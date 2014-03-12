package org.mifosplatform.portfolio.equity.service;

import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResultBuilder;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.mifosplatform.portfolio.asset.domain.Asset;
import org.mifosplatform.portfolio.asset.service.AssetWritePlatformServiceJpaRepositoryImpl;
import org.mifosplatform.portfolio.equity.domain.Equity;
import org.mifosplatform.portfolio.equity.domain.EquityRepository;
import org.mifosplatform.portfolio.equity.serializatin.EquityCommandFromApiJsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
@Service
public class EquityWritePlatformServiceJpaRepositoryImpl implements EquityWritePlatformService{
	private final static Logger logger = LoggerFactory.getLogger(AssetWritePlatformServiceJpaRepositoryImpl.class);

    private final PlatformSecurityContext context;
    private final EquityRepository equityRepository;
    private final EquityCommandFromApiJsonDeserializer equityCommandFromApiJsonDeserializer;
    @Autowired
    public EquityWritePlatformServiceJpaRepositoryImpl( final PlatformSecurityContext context,final EquityRepository equityRepository,final EquityCommandFromApiJsonDeserializer equityCommandFromApiJsonDeserializer ){
    	this.context=context;
    	this.equityRepository=equityRepository;
    	this.equityCommandFromApiJsonDeserializer=equityCommandFromApiJsonDeserializer;
    }
	@Override
	public CommandProcessingResult createEquity(Long loanId,JsonCommand command) {	
		 try {
	            context.authenticatedUser();
               this.equityCommandFromApiJsonDeserializer.validateForCreate(command.json());   
	            final Equity equity = Equity.fromJson(loanId,command);
	           this.equityRepository.save(equity);
	            return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withEntityId(equity.getId()).build();
	        } catch (DataIntegrityViolationException dve) {
	         
	            return CommandProcessingResult.empty();
	        } 
	
	}

	@Override
	public CommandProcessingResult updateEquity(Long equityId,
			JsonCommand command) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommandProcessingResult deleteEquity(Long equityId) {
		// TODO Auto-generated method stub
		return null;
	}

}
