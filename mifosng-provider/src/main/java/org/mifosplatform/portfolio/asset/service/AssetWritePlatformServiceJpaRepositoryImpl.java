package org.mifosplatform.portfolio.asset.service;

import java.util.Map;

import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResultBuilder;
import org.mifosplatform.infrastructure.core.exception.PlatformDataIntegrityException;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.mifosplatform.portfolio.asset.domain.Asset;
import org.mifosplatform.portfolio.asset.domain.AssetRepository;
import org.mifosplatform.portfolio.asset.serialization.AssetCommandFromApiJsonDeserializer;
import org.mifosplatform.portfolio.client.exception.ClientIdentifierNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class AssetWritePlatformServiceJpaRepositoryImpl implements AssetWritePlatformService{
	private final static Logger logger = LoggerFactory.getLogger(AssetWritePlatformServiceJpaRepositoryImpl.class);

    private final PlatformSecurityContext context;
    
    private final AssetRepository assetRepository;

    private final AssetReadPlatformService assetReadPlatformService;
    private final AssetCommandFromApiJsonDeserializer assetCommandFromApiJsonDeserializer;
    //private final CreditPolicyCommandFromApiJsonDeserializer fromApiJsonDeserializer;
    @Autowired
	public AssetWritePlatformServiceJpaRepositoryImpl(final PlatformSecurityContext context,final AssetRepository assetRepository,final AssetReadPlatformService assetReadPlatformService,final AssetCommandFromApiJsonDeserializer assetCommandFromApiJsonDeserializer)
	{
		this.context=context;
		this.assetRepository=assetRepository;
		this.assetReadPlatformService=assetReadPlatformService;
		this.assetCommandFromApiJsonDeserializer=assetCommandFromApiJsonDeserializer;
		
	}

	@Override
	public CommandProcessingResult createAsset(JsonCommand command) {
		  try {
	            context.authenticatedUser();

	        this.assetCommandFromApiJsonDeserializer.validateForCreate(command.json());
	           
	            final Asset asset = Asset.fromJson(command);
	           this.assetRepository.save(asset);
	            return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withEntityId(asset.getId()).build();
	        } catch (DataIntegrityViolationException dve) {
	         
	            return CommandProcessingResult.empty();
	        } 
	}

	
	@Override
	public CommandProcessingResult deleteAsset(Long assetId) {
	
		Asset asset=assetRepository.findOne(assetId);
	        if (asset == null) { throw new ClientIdentifierNotFoundException(assetId); }
	        this.assetRepository.delete(asset);
	        return new CommandProcessingResultBuilder() //
	                .withCommandId(assetId) //
	                .build();
	}
	
	@Override
	public CommandProcessingResult updateAsset(Long assetId, JsonCommand command) {

			
                     Asset asset;
	    	try {
	    		 
	            context.authenticatedUser();

	         // this.fromApiJsonDeserializer.validateForUpdate(command.json());
	         
                           asset=assetRepository.findOne(assetId);
	             //   if (creditPolicy == null) { throw new ClientIdentifierNotFoundException(creditPolicyData.getId()); }
	          

	            final Map<String, Object> changes = asset.update(command);
	            if (!changes.isEmpty()) {
	                this.assetRepository.saveAndFlush(asset);
	            }

	            return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withEntityId(asset.getId()).with(changes).build();
	        } catch (DataIntegrityViolationException dve) {
	        	handleGuarantorDataIntegrityIssues( dve);
	            return CommandProcessingResult.empty();
	        }
	    }
	   private void handleGuarantorDataIntegrityIssues(final DataIntegrityViolationException dve) {
	        Throwable realCause = dve.getMostSpecificCause();
	        logger.error(dve.getMessage(), dve);
	        throw new PlatformDataIntegrityException("error.msg.creditpolicy.unknown.data.integrity.issue",
	                "Unknown data integrity issue with resource creditpolicy: " + realCause.getMessage());
	    }
	

}
