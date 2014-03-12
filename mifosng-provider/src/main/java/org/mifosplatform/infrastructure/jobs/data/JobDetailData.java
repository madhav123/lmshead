package org.mifosplatform.infrastructure.jobs.data;

import java.util.Date;
import java.util.List;

public class JobDetailData {

    @SuppressWarnings("unused")
    private final Long jobId;

    @SuppressWarnings("unused")
    private String displayName;
    
    
    @SuppressWarnings("unused")
    private final String name;


    @SuppressWarnings("unused")
    private final Date nextRunTime;

    @SuppressWarnings("unused")
    private final String initializingError;

    @SuppressWarnings("unused")
    private final String cronExpression;

    @SuppressWarnings("unused")
    private final boolean active;

    @SuppressWarnings("unused")
    private final boolean currentlyRunning;

    @SuppressWarnings("unused")
    private final JobDetailHistoryData lastRunHistory;
    
    @SuppressWarnings("unused")
	private JobParameterData jobparameters;
    
    @SuppressWarnings("unused")
    private List<MessageTemplateData> messageTemplateData;

    
    @SuppressWarnings("unused")
    private  List<ScheduleJobData> queryData;
    

	public JobDetailData(final Long jobId, final String displayName, final Date nextRunTime, final String initializingError,
            final String cronExpression, final boolean active, final boolean currentlyRunning, final JobDetailHistoryData lastRunHistory ,    
            @SuppressWarnings("unused")
            final String name) {
        this.jobId = jobId;
        this.displayName = displayName;
        this.nextRunTime = nextRunTime;
        this.initializingError = initializingError;
        this.cronExpression = cronExpression;
        this.active = active;
        this.lastRunHistory = lastRunHistory;
        this.currentlyRunning = currentlyRunning;
        this.name=name;
    }

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
    
	
	  public JobParameterData getJobparameters() {
			return jobparameters;
		}

		public void setJobparameters(JobParameterData jobparameters) {
			this.jobparameters = jobparameters;
		}

		public List<MessageTemplateData> getMessageTemplateData() {
			return messageTemplateData;
		}

		public void setMessageTemplateData(List<MessageTemplateData> messageTemplateData) {
			this.messageTemplateData = messageTemplateData;
		}

		public List<ScheduleJobData> getQueryData() {
			return queryData;
		}

		public void setQueryData(List<ScheduleJobData> queryData) {
			this.queryData = queryData;
		}
		
		
		
		
}
