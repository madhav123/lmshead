package org.mifosplatform.portfolio.message.service;

import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;

public interface LeaseMesssageWritePlatformService {
	public CommandProcessingResult createMessageData(Long id,String json) ;
}
