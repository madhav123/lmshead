package org.mifosplatform.portfolio.equity.service;

import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;

public interface EquityWritePlatformService {
	  public CommandProcessingResult createEquity(final Long loanId,final JsonCommand command);
	  public CommandProcessingResult updateEquity(final Long equityId, final JsonCommand command);
	  public CommandProcessingResult deleteEquity( final Long equityId);
}
