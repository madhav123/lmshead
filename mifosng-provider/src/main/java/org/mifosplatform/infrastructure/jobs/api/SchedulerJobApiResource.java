package org.mifosplatform.infrastructure.jobs.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.StringUtils;
import org.mifosplatform.commands.domain.CommandWrapper;
import org.mifosplatform.commands.service.CommandWrapperBuilder;
import org.mifosplatform.commands.service.PortfolioCommandSourceWritePlatformService;
import org.mifosplatform.infrastructure.core.api.ApiRequestParameterHelper;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.exception.UnrecognizedQueryParamException;
import org.mifosplatform.infrastructure.core.serialization.ApiRequestJsonSerializationSettings;
import org.mifosplatform.infrastructure.core.serialization.ToApiJsonSerializer;
import org.mifosplatform.infrastructure.core.service.Page;
import org.mifosplatform.infrastructure.jobs.data.JobDetailData;
import org.mifosplatform.infrastructure.jobs.data.JobDetailHistoryData;
import org.mifosplatform.infrastructure.jobs.data.JobParameterData;
import org.mifosplatform.infrastructure.jobs.data.MessageTemplateData;
import org.mifosplatform.infrastructure.jobs.data.ScheduleJobData;
import org.mifosplatform.infrastructure.jobs.service.JobRegisterService;
import org.mifosplatform.infrastructure.jobs.service.SchedulerJobRunnerReadService;
import org.mifosplatform.portfolio.group.service.SearchParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Path("/jobs")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@Component
public class SchedulerJobApiResource {

    private final SchedulerJobRunnerReadService schedulerJobRunnerReadService;
    private final JobRegisterService jobRegisterService;
    private final ApiRequestParameterHelper apiRequestParameterHelper;
    private final ToApiJsonSerializer<JobDetailData> toApiJsonSerializer;
    private final ToApiJsonSerializer<JobDetailHistoryData> jobHistoryToApiJsonSerializer;
    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;

    @Autowired
    public SchedulerJobApiResource(final SchedulerJobRunnerReadService schedulerJobRunnerReadService,
            final JobRegisterService jobRegisterService, final ToApiJsonSerializer<JobDetailData> toApiJsonSerializer,
            final ApiRequestParameterHelper apiRequestParameterHelper,
            final ToApiJsonSerializer<JobDetailHistoryData> jobHistoryToApiJsonSerializer,
            final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService) {
        this.schedulerJobRunnerReadService = schedulerJobRunnerReadService;
        this.jobRegisterService = jobRegisterService;
        this.toApiJsonSerializer = toApiJsonSerializer;
        this.jobHistoryToApiJsonSerializer = jobHistoryToApiJsonSerializer;
        this.apiRequestParameterHelper = apiRequestParameterHelper;
        this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
    }

    @GET
    public String retrieveAll(@Context final UriInfo uriInfo) {
        final List<JobDetailData> jobDetailDatas = this.schedulerJobRunnerReadService.findAllJobDeatils();
        final ApiRequestJsonSerializationSettings settings = this.apiRequestParameterHelper.process(uriInfo.getQueryParameters());
        return this.toApiJsonSerializer.serialize(settings, jobDetailDatas, SchedulerJobApiConstants.JOB_DETAIL_RESPONSE_DATA_PARAMETERS);
    }

    @GET
    @Path("{" + SchedulerJobApiConstants.JOB_ID + "}")
    public String retrieveOne(@PathParam(SchedulerJobApiConstants.JOB_ID) final Long jobId, @Context final UriInfo uriInfo) {
        final JobDetailData jobDetailData = this.schedulerJobRunnerReadService.retrieveOne(jobId);
        final ApiRequestJsonSerializationSettings settings = this.apiRequestParameterHelper.process(uriInfo.getQueryParameters());
        return this.toApiJsonSerializer.serialize(settings, jobDetailData, SchedulerJobApiConstants.JOB_DETAIL_RESPONSE_DATA_PARAMETERS);
    }

    @GET
    @Path("{" + SchedulerJobApiConstants.JOB_ID + "}/" + SchedulerJobApiConstants.JOB_RUN_HISTORY)
    public String retrieveHistory(@Context final UriInfo uriInfo, @PathParam(SchedulerJobApiConstants.JOB_ID) final Long jobId,
            @QueryParam("offset") final Integer offset, @QueryParam("limit") final Integer limit,
            @QueryParam("orderBy") final String orderBy, @QueryParam("sortOrder") final String sortOrder) {
        final SearchParameters searchParameters = SearchParameters.forPagination(offset, limit, orderBy, sortOrder);
        final Page<JobDetailHistoryData> jobhistoryDetailData = this.schedulerJobRunnerReadService.retrieveJobHistory(jobId,
                searchParameters);
        final ApiRequestJsonSerializationSettings settings = this.apiRequestParameterHelper.process(uriInfo.getQueryParameters());
        return this.jobHistoryToApiJsonSerializer.serialize(settings, jobhistoryDetailData,
                SchedulerJobApiConstants.JOB_HISTORY_RESPONSE_DATA_PARAMETERS);
    }
    @GET
    @Path("{" + SchedulerJobApiConstants.JOB_ID + "}/" + SchedulerJobApiConstants.JOB_PARAMETERS)
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String retrieveJobParameters(@Context final UriInfo uriInfo, @PathParam(SchedulerJobApiConstants.JOB_ID) final Long jobId) {
    	JobDetailData jobDetailData = this.schedulerJobRunnerReadService.retrieveOne(jobId);
        JobParameterData data=this.schedulerJobRunnerReadService.getJobParameters(jobDetailData.getDisplayName());
        jobDetailData.setJobparameters(data);	
        List<MessageTemplateData> messageTemplateData=schedulerJobRunnerReadService.getMessageTemplate();
        jobDetailData.setMessageTemplateData(messageTemplateData);
        List<ScheduleJobData> queryData=this.schedulerJobRunnerReadService.getJobQeryData();
	      jobDetailData.setQueryData(queryData);
        //need to play some logic 
        
        /*   if(jobDetailData.getName().equalsIgnoreCase(SchedulerJobApiConstants.JOB_MESSANGER)){
	    	  final Collection<BillingMessageData> templateData = this.billingMesssageReadPlatformService.retrieveAllMessageTemplates();
	    	  jobDetailData.setMessageData(templateData);
	       }*/
        final ApiRequestJsonSerializationSettings settings = this.apiRequestParameterHelper.process(uriInfo.getQueryParameters());
        return this.toApiJsonSerializer.serialize(settings, jobDetailData, SchedulerJobApiConstants.JOB_DETAIL_RESPONSE_DATA_PARAMETERS);
    }
 /*   private JobDetailData handleTemplateData(JobDetailData jobDetailData) {
    	
    	
	      List<ScheduleJobData> queryData=this.sheduleJobReadPlatformService.getJobQeryData();
	      jobDetailData.setQueryData(queryData);
	      if(jobDetailData.getName().equalsIgnoreCase(SchedulerJobApiConstants.JOB_MESSANGER)){
	    	  
	    	  final Collection<BillingMessageData> templateData = this.billingMesssageReadPlatformService.retrieveAllMessageTemplates();
	    	  jobDetailData.setMessageData(templateData);
	       }
	
	return jobDetailData;
}*/


    @POST
    @Path("{" + SchedulerJobApiConstants.JOB_ID + "}")
    public Response executeJob(@PathParam(SchedulerJobApiConstants.JOB_ID) final Long jobId,
            @QueryParam(SchedulerJobApiConstants.COMMAND) final String commandParam) {
        Response response = Response.status(400).build();
        if (is(commandParam, SchedulerJobApiConstants.COMMAND_EXECUTE_JOB)) {
            this.jobRegisterService.executeJob(jobId);
            response = Response.status(202).build();
        } else {
            throw new UnrecognizedQueryParamException(SchedulerJobApiConstants.COMMAND, commandParam);
        }
        return response;
    }
	@PUT
    @Path("{" + SchedulerJobApiConstants.JOB_ID + "}/"+SchedulerJobApiConstants.JOB_PARAMETERS)
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String updateJobParametersl(@PathParam(SchedulerJobApiConstants.JOB_ID) final Long jobId, final String jsonRequestBody) {

        final CommandWrapper commandRequest = new CommandWrapperBuilder() //
                .updateJobParametersDetail(jobId) //
                .withJson(jsonRequestBody) //
                .build(); //
        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);
        
        return this.toApiJsonSerializer.serialize(result);
    }
    
    @PUT
    @Path("{" + SchedulerJobApiConstants.JOB_ID + "}")
    public String updateJobDetail(@PathParam(SchedulerJobApiConstants.JOB_ID) final Long jobId, final String jsonRequestBody) {

        final CommandWrapper commandRequest = new CommandWrapperBuilder() //
                .updateJobDetail(jobId) //
                .withJson(jsonRequestBody) //
                .build(); //
        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);
        if (result.getChanges() != null
                && (result.getChanges().containsKey(SchedulerJobApiConstants.jobActiveStatusParamName) || result.getChanges().containsKey(
                        SchedulerJobApiConstants.cronExpressionParamName))) {
            this.jobRegisterService.rescheduleJob(jobId);
        }
        return this.toApiJsonSerializer.serialize(result);
    }

    private boolean is(final String commandParam, final String commandValue) {
        return StringUtils.isNotBlank(commandParam) && commandParam.trim().equalsIgnoreCase(commandValue);
    }
}