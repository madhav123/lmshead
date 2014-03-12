package org.mifosplatform.portfolio.message.service;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jettison.json.JSONObject;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.domain.MifosPlatformTenant;
import org.mifosplatform.infrastructure.core.serialization.FromJsonHelper;
import org.mifosplatform.infrastructure.core.service.ThreadLocalContextUtil;
import org.mifosplatform.infrastructure.dataqueries.service.ReadReportingService;
import org.mifosplatform.infrastructure.jobs.annotation.CronTarget;
import org.mifosplatform.infrastructure.jobs.data.JobParameterData;
import org.mifosplatform.infrastructure.jobs.data.ScheduleJobData;
import org.mifosplatform.infrastructure.jobs.service.JobName;
import org.mifosplatform.portfolio.message.data.BillingMessageData;
import org.mifosplatform.portfolio.message.data.BillingMessageDataForProcessing;
import org.springframework.beans.factory.annotation.Autowired;
@Service

public class LeaseMesssageReadPlatformServiceImpl implements LeaseMesssageReadPlatformService{
	private final ScheduleJobReadPlatformService scheduleJobReadPlatformService;
	private final BillingMesssageReadPlatformService billingMesssageReadPlatformService;
	private final BillingMessageDataWritePlatformService billingMessageDataWritePlatformService;
	private final MessagePlatformEmailService messagePlatformEmailService;
	@Autowired
	public LeaseMesssageReadPlatformServiceImpl(final ScheduleJobReadPlatformService scheduleJobReadPlatformService,
			final BillingMesssageReadPlatformService billingMesssageReadPlatformService,BillingMessageDataWritePlatformService billingMessageDataWritePlatformService
			, final MessagePlatformEmailService messagePlatformEmailService){
		this.scheduleJobReadPlatformService=scheduleJobReadPlatformService;
		this.billingMesssageReadPlatformService=billingMesssageReadPlatformService;
		this.billingMessageDataWritePlatformService=billingMessageDataWritePlatformService;
		this.messagePlatformEmailService=messagePlatformEmailService;
	}
	@Transactional
	@CronTarget(jobName = JobName.MESSAGE_MERGE)
	@Override
	public void processingMessages() {
		 JobParameterData data=this.scheduleJobReadPlatformService.retrieveJobParameters(JobName.MESSAGE_MERGE.toString());
		 
		 List<ScheduleJobData> sheduleDatas = this.scheduleJobReadPlatformService.retrieveSheduleJobDetails(data.getBatchName());
		 for (ScheduleJobData scheduleJobData : sheduleDatas) {
			   Long messageId = this.scheduleJobReadPlatformService.getMessageId(data.getMessageTemplate());
			   this.billingMessageDataWritePlatformService.createMessageData(messageId,scheduleJobData.getQuery());
		 }
		
	}
	@Transactional
	@Override
	@CronTarget(jobName = JobName.PUSH_NOTIFICATION)
	public void processNotify() {
		try {
			System.out.println("Processing Notify Details.......");
			
			List<BillingMessageDataForProcessing> billingMessageDataForProcessings=this.billingMesssageReadPlatformService.retrieveMessageDataForProcessing();
    	    
			if(!billingMessageDataForProcessings.isEmpty()){
				
				  MifosPlatformTenant tenant = ThreadLocalContextUtil.getTenant();				
					final DateTimeZone zone = DateTimeZone.forID(tenant.getTimezoneId());
					LocalTime date=new LocalTime(zone);
					String dateTime=date.getHourOfDay()+"_"+date.getMinuteOfHour()+"_"+date.getSecondOfMinute();
    	    //	String path=FileUtils.generateLogFileDirectory()+JobName.PUSH_NOTIFICATION.toString() + File.separator +"PushNotification_"+new LocalDate().toString().replace("-","")+"_"+dateTime+".log";
    	   // 	File fileHandler = new File(path.trim());
    			// fileHandler.createNewFile();
    		//	 FileWriter fw = new FileWriter(fileHandler);
    		 //    FileUtils.BILLING_JOB_PATH=fileHandler.getAbsolutePath();
			  //  fw.append("Processing Notify Details....... \r\n");  	    	
				for(BillingMessageDataForProcessing emailDetail : billingMessageDataForProcessings){
				//	fw.append("BillingMessageData id="+emailDetail.getId()+" ,MessageTo="+emailDetail.getMessageTo()+" ,MessageType="
						//	+emailDetail.getMessageType()+" ,MessageFrom="+emailDetail.getMessageFrom()+" ,Message="
						//	+emailDetail.getBody()+"\r\n");
	    	    	if(emailDetail.getMessageType()=='E'){
	    	    		 String Result=this.messagePlatformEmailService.sendToUserEmail(emailDetail);
	    	    		// fw.append("b_message_data processing id="+emailDetail.getId()+"-- and Result :"+Result+" ... \r\n");
	    	    	}
	    	    	else if(emailDetail.getMessageType()=='M'){
	    	    		String message = this.scheduleJobReadPlatformService.retrieveMessageData(emailDetail.getId());
	    	    		String Result=this.messagePlatformEmailService.sendToUserMobile(message,emailDetail.getId());	    	    		
	    	    		//fw.append("b_message_data processing id="+emailDetail.getId()+"-- and Result:"+Result+" ... \r\n");
	    	    	}
	    	    	else{
	    	    		// fw.append("Message Type Unknown ..\r\n");
	    	    	}		                        
	           }
			//	fw.append("Notify Job is Completed.... \r\n");
			//	fw.flush();
			//	fw.close();
				
    	    }else{
            	System.out.println("push Notification data is empty...");
            }
			System.out.println("Notify Job is Completed..."+ ThreadLocalContextUtil.getTenant().getTenantIdentifier());
		} catch (DataIntegrityViolationException exception) {

		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
	}

}