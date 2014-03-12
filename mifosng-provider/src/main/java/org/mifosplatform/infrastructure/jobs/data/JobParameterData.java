package org.mifosplatform.infrastructure.jobs.data;

import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.mifosplatform.infrastructure.jobs.domain.JobParameters;



public class JobParameterData {
	
	private String batchName;
	private String isDynamic;
	private LocalDate dueDate;
	private LocalDate processDate;
	private LocalDate exipiryDate;
	private String defaultValue;
	private String url;
	private String username;
	private String password;
	private String provSystem;
	private String isAutoRenewal;
	private String promotionalMessage;
	private String messageTemplate;
	private String emailId;
	
	public JobParameterData(List<JobParameters> jobParameters) {
              
		for(JobParameters parameter:jobParameters){
			
		if(parameter.getParamName().equalsIgnoreCase(JobParametersConstants.PARAM_MESSAGETEMPLATE)){
			     this.messageTemplate=parameter.getParamValue();

		    }
			
			else if(parameter.getParamName().equalsIgnoreCase(JobParametersConstants.PARAM_PROMTIONALMESSAGE)){
			          this.promotionalMessage=parameter.getParamValue();	
			
			}else if(parameter.getParamName().equalsIgnoreCase(JobParametersConstants.PARAM_PROCESSDATE) && parameter.getParamValue()!=null){
				    this.processDate= DateTimeFormat.forPattern("dd MMMM yyyy").parseLocalDate(parameter.getParamValue());
				    this.isDynamic=parameter.isDynamic();
			
			}else if(parameter.getParamName().equalsIgnoreCase(JobParametersConstants.PARAM_DUEDATE) && parameter.getParamValue()!=null){
			    this.dueDate= DateTimeFormat.forPattern("dd MMMM yyyy")
		                 .parseLocalDate(parameter.getParamValue());
			    this.isDynamic=parameter.isDynamic();

			}else if(parameter.getParamName().equalsIgnoreCase(JobParametersConstants.PARAM_EXIPIRYDATE) && parameter.getParamValue()!=null){
			    this.exipiryDate= DateTimeFormat.forPattern("dd MMMM yyyy")
		                 .parseLocalDate(parameter.getParamValue());

			    this.isDynamic=parameter.isDynamic();
			}else if(parameter.getParamName().equalsIgnoreCase(JobParametersConstants.PARAM_URL)){
			     this.url=parameter.getParamValue();
					
		    }else if(parameter.getParamName().equalsIgnoreCase(JobParametersConstants.PARAM_USERNAME)){
		         this.username=parameter.getParamValue();
				
	        }else if(parameter.getParamName().equalsIgnoreCase(JobParametersConstants.PARAM_PASSWORD)){
	             this.password=parameter.getParamValue();
			
            }else if(parameter.getParamName().equalsIgnoreCase(JobParametersConstants.PARAM_Prov_System)){
                 this.provSystem=parameter.getParamValue();
	

            }else if(parameter.getParamName().equalsIgnoreCase(JobParametersConstants.PARAM_IS_RENEWAL)){
                
            	this.isAutoRenewal=parameter.isDynamic();
            	
           }else if(parameter.getParamName().equalsIgnoreCase(JobParametersConstants.PARAM_EMAIL)){
		          this.emailId=parameter.getParamValue();	
					
		   }else{
				 this.batchName=parameter.getParamValue();
				 this.defaultValue=parameter.getParamDefaultValue();
			}
			/*if(parameter.isDynamic() == "Y" && parameter.getParamValue() == null){
				
	    		  if(parameter.getParamValue().equalsIgnoreCase("+1")){
	    			  
	    			  this.dueDate=new LocalDate().plusDays(1);
	    			  this.processDate=new LocalDate().plusDays(1);
	    		  }else{
	    			  dueDate=new LocalDate().minusDays(1);
	    			  this.processDate=new LocalDate().minusDays(1);
	    		  }
	    	          	  
	    	  }*/
		}
		
		
	}

	public String getBatchName() {
		return batchName;
	}

	public String isDynamic() {
		return isDynamic;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public LocalDate getProcessDate() {
		return processDate;
	}



	public String getIsAutoRenewal() {
		return isAutoRenewal;
	}

	public String getIsDynamic() {

		return isDynamic;
	}

	public LocalDate getExipiryDate() {
		return exipiryDate;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getProvSystem() {
		return provSystem;
	}

	public String getPromotionalMessage() {
		return promotionalMessage;
	}


	public String getMessageTemplate() {
		return messageTemplate;

	}

	public String getEmailId() {
		return emailId;
	}
	
	
	
	
	
	

	

	
	
	
	

	

}
