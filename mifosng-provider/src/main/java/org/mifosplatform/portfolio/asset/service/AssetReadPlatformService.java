package org.mifosplatform.portfolio.asset.service;

import java.util.List;

import org.mifosplatform.portfolio.asset.data.AssetData;

public interface AssetReadPlatformService {
	  public AssetData reteriveDropdownOptions();
	  public  List<AssetData> retrieveAssetData();
	  public AssetData retrieveAssetData(final Long assetId);
}
