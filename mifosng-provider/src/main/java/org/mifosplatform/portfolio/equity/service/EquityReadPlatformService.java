package org.mifosplatform.portfolio.equity.service;

import java.util.List;

import org.mifosplatform.portfolio.asset.data.AssetData;
import org.mifosplatform.portfolio.equity.data.EquityData;
import org.mifosplatform.portfolio.equity.data.EquityType;

public interface EquityReadPlatformService {
	  public EquityData reteriveDropdownOptions();
	  public  List<EquityData> retrieveEquityDataAll(Long loanId);
	  public EquityData retrieveEquityData(final Long assetId);
}
