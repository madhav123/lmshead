package org.mifosplatform.portfolio.message.handler;

import org.mifosplatform.commands.handler.NewCommandSourceHandler;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.portfolio.message.service.BillingMessageTemplateWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteBillingMessageTemplateCommandHandler implements NewCommandSourceHandler {

	private final BillingMessageTemplateWritePlatformService billingMessageTemplateWritePlatformService;
	
	@Autowired
	public DeleteBillingMessageTemplateCommandHandler(final BillingMessageTemplateWritePlatformService billingMessageTemplateWritePlatformService)
	{
	this.billingMessageTemplateWritePlatformService =billingMessageTemplateWritePlatformService;
	}
	
	 @Transactional
	public CommandProcessingResult processCommand(JsonCommand command) {
		return this.billingMessageTemplateWritePlatformService.deleteMessageTemplate(command);
	}

}
