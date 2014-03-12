package org.mifosplatform.portfolio.message.service;

import java.util.List;

import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.portfolio.message.data.BillingMessageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class LeaseMesssageWritePlatformServiceJpaRepositoryImpl implements LeaseMesssageWritePlatformService{
	private final ScheduleJobReadPlatformService scheduleJobReadPlatformService;
	private final BillingMesssageReadPlatformService billingMesssageReadPlatformService;
	@Autowired
	public LeaseMesssageWritePlatformServiceJpaRepositoryImpl(final ScheduleJobReadPlatformService scheduleJobReadPlatformService,
			final BillingMesssageReadPlatformService billingMesssageReadPlatformService){
		this.scheduleJobReadPlatformService=scheduleJobReadPlatformService;
		this.billingMesssageReadPlatformService=billingMesssageReadPlatformService;
	}
	@Override
	public CommandProcessingResult createMessageData(Long id, String json) {
		BillingMessageData templateData=this.scheduleJobReadPlatformService.retrieveMessageTemplate(id);
		List<BillingMessageData> messageparam=this.scheduleJobReadPlatformService.retrieveMessageParams(id);
		List<BillingMessageData> clientData=this.billingMesssageReadPlatformService.retrieveData(id,json,templateData,messageparam,billingMesssageReadPlatformService);
	  return null;
	}

}
