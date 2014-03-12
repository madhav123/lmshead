package org.mifosplatform.portfolio.asset.handler;

import org.mifosplatform.commands.handler.NewCommandSourceHandler;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.portfolio.asset.service.AssetWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class DeleteAssetCommandHandler implements NewCommandSourceHandler{
	private AssetWritePlatformService assetWritePlatformService;
	@Autowired
	public DeleteAssetCommandHandler( AssetWritePlatformService assetWritePlatformService)
	{
		this.assetWritePlatformService=assetWritePlatformService;
	}
	@Transactional
	@Override
	public CommandProcessingResult processCommand(final JsonCommand command) {

	    return this.assetWritePlatformService.deleteAsset(command.entityId());
	}
}