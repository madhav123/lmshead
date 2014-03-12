package org.mifosplatform.portfolio.message.service;

import java.util.List;

import org.mifosplatform.infrastructure.jobs.data.JobParameterData;
import org.mifosplatform.infrastructure.jobs.data.ScheduleJobData;
import org.mifosplatform.infrastructure.jobs.domain.JobParameters;
import org.mifosplatform.portfolio.message.data.BillingMessageData;

public interface ScheduleJobReadPlatformService {
	JobParameterData retrieveJobParameters(String jobName) ;
	public List<ScheduleJobData> retrieveSheduleJobDetails(String paramValue) ;
	Long getMessageId(String messageTemplateName);
	BillingMessageData retrieveMessageTemplate(Long command);
	List<BillingMessageData> retrieveMessageParams(Long entityId);
	String retrieveMessageData(Long id);
}
