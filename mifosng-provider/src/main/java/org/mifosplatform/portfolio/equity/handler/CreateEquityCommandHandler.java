package org.mifosplatform.portfolio.equity.handler;

import org.mifosplatform.commands.handler.NewCommandSourceHandler;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.portfolio.equity.service.EquityWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CreateEquityCommandHandler implements NewCommandSourceHandler{
	private EquityWritePlatformService equityWritePlatformService;
	@Autowired
	public CreateEquityCommandHandler( EquityWritePlatformService equityWritePlatformService)
	{
		this.equityWritePlatformService=equityWritePlatformService;
	}
	@Override
	public CommandProcessingResult processCommand(JsonCommand command) {
	return this.equityWritePlatformService.createEquity(command.entityId(),command);
	}

}
