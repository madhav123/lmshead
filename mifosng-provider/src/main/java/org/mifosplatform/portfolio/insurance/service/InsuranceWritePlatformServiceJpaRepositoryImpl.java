package org.mifosplatform.portfolio.insurance.service;

import java.util.Calendar;
import java.util.Date;

import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResultBuilder;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.mifosplatform.portfolio.asset.domain.Asset;
import org.mifosplatform.portfolio.asset.domain.AssetRepository;
import org.mifosplatform.portfolio.asset.service.AssetWritePlatformServiceJpaRepositoryImpl;
import org.mifosplatform.portfolio.client.exception.ClientIdentifierNotFoundException;
import org.mifosplatform.portfolio.insurance.data.InsuranceData;
import org.mifosplatform.portfolio.insurance.domain.Insurance;
import org.mifosplatform.portfolio.insurance.domain.InsuranceRepository;
import org.mifosplatform.portfolio.loanaccount.domain.Loan;
import org.mifosplatform.portfolio.loanaccount.domain.LoanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
@Service
public class InsuranceWritePlatformServiceJpaRepositoryImpl implements InsuranceWritePlatformService{
	private final static Logger logger = LoggerFactory.getLogger(InsuranceWritePlatformServiceJpaRepositoryImpl.class);

    private final PlatformSecurityContext context;
    private final LoanRepository loanRepository;
    private final AssetRepository assetRepository;
    private final InsuranceRepository insuranceRepository;
    private final InsuranceReadPlatformService insuranceReadPlatformService;
    @Autowired
    public InsuranceWritePlatformServiceJpaRepositoryImpl(final PlatformSecurityContext context,final LoanRepository loanRepository,
    		final AssetRepository assetRepository,InsuranceRepository insuranceRepository,final InsuranceReadPlatformService insuranceReadPlatformService){
    	this.context=context;
    	this.loanRepository=loanRepository;
    	this.assetRepository=assetRepository;
    	this.insuranceRepository=insuranceRepository;
    	this.insuranceReadPlatformService=insuranceReadPlatformService;
    	
    }

	@Override
	public CommandProcessingResult createInsurance(long loanId,
		
			JsonCommand command) {

		try{
		context.authenticatedUser();
        
		Loan loan=loanRepository.findOne(loanId);
		Insurance insurance=Insurance.fromJson(command);
		insurance.setLoanAccountNo(loan.getId());
		insuranceRepository.saveAndFlush(insurance);
		
		return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withEntityId(insurance.getId()).build();
    } catch (DataIntegrityViolationException dve) {
     
        return CommandProcessingResult.empty();
    } 
	}

	@Override
	public CommandProcessingResult updateInsurance(Long assetId,
			JsonCommand command) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommandProcessingResult deleteInsurance(Long loanId) {
		InsuranceData insuranceData=insuranceReadPlatformService.retrieveInsuracetDataSingle(loanId);
		Insurance insurance=insuranceRepository.findOne(insuranceData.getId());
	   if (insurance == null) { throw new ClientIdentifierNotFoundException(loanId); }
        this.insuranceRepository.delete(insurance);
  /*      return new CommandProcessingResultBuilder() //
                .withCommandId(insurance.getLoanAccountNo()) //
                .build();*/

        return new CommandProcessingResultBuilder().withEntityId(insurance.getId())
        		.withLoanId(insurance.getLoanAccountNo())
        		.build();
	}

}
