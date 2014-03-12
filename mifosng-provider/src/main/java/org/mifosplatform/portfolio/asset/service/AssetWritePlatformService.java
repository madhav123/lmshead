package org.mifosplatform.portfolio.asset.service;

import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;

public interface AssetWritePlatformService {
	  public CommandProcessingResult createAsset(final JsonCommand command);
	  public CommandProcessingResult updateAsset(final Long assetId, final JsonCommand command);
	  public CommandProcessingResult deleteAsset( final Long assetId);
}
