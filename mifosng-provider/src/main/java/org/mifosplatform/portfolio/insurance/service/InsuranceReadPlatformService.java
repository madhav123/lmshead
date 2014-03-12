package org.mifosplatform.portfolio.insurance.service;

import java.util.List;

import org.mifosplatform.portfolio.asset.data.AssetData;
import org.mifosplatform.portfolio.insurance.data.InsuranceData;

public interface InsuranceReadPlatformService {
	 public InsuranceData reteriveDropdownOptions();
	  public  List<InsuranceData> retrieveInsuracetData();
	  public List<InsuranceData> retrieveInsuracetData(final Long assetId);
	  public InsuranceData retrieveInsuracetDataSingle(Long loanId);
}
