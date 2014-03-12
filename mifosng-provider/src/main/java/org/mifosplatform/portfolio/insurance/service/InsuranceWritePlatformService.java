package org.mifosplatform.portfolio.insurance.service;

import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;

public interface InsuranceWritePlatformService {
	  public CommandProcessingResult createInsurance(final long loanId,final JsonCommand command);
	  public CommandProcessingResult updateInsurance(final Long assetId, final JsonCommand command);
	  public CommandProcessingResult deleteInsurance( final Long assetId);
}
