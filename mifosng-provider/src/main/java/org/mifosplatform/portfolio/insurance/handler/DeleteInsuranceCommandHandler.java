package org.mifosplatform.portfolio.insurance.handler;

import org.mifosplatform.commands.handler.NewCommandSourceHandler;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.portfolio.insurance.service.InsuranceWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class DeleteInsuranceCommandHandler implements NewCommandSourceHandler {
	private InsuranceWritePlatformService insuranceWritePlatformService;
	@Autowired
		public DeleteInsuranceCommandHandler(InsuranceWritePlatformService insuranceWritePlatformService){
			this.insuranceWritePlatformService=insuranceWritePlatformService;
		}
		@Override
		public CommandProcessingResult processCommand(JsonCommand command) {
			return this.insuranceWritePlatformService.deleteInsurance(command.entityId());
		}

	}
