package org.mifosplatform.portfolio.message.service;

import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;

public interface BillingMessageDataWritePlatformService {

	CommandProcessingResult createMessageData(Long command, String string);
	
	
	
	

}
