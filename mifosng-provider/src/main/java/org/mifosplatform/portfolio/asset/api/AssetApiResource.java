package org.mifosplatform.portfolio.asset.api;
import java.util.Arrays;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import org.mifosplatform.commands.domain.CommandWrapper;
import org.mifosplatform.commands.service.CommandWrapperBuilder;
import org.mifosplatform.commands.service.PortfolioCommandSourceWritePlatformService;
import org.mifosplatform.infrastructure.core.api.ApiRequestParameterHelper;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.serialization.ApiRequestJsonSerializationSettings;
import org.mifosplatform.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.mifosplatform.portfolio.asset.service.AssetReadPlatformService;
import org.mifosplatform.portfolio.asset.service.AssetWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.mifosplatform.portfolio.asset.data.AssetData;;
@Path("/assets")
@Component
@Scope("singleton")
public class AssetApiResource {
	  private final Set<String> RESPONSE_DATA_PARAMETERS = new HashSet<String>(Arrays.asList("assetCode","assetDescription","assetType","model","make","purchaseValue","saleValue","locale"));

	    private final String resourceNameForPermissions = "ASSET";

	    private final PlatformSecurityContext context;
	    private final ApiRequestParameterHelper apiRequestParameterHelper;
	    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;
	    private final DefaultToApiJsonSerializer<AssetData> toApiJsonSerializer;
	    private final AssetReadPlatformService assetReadPlatformService;
	    @Autowired
	    public AssetApiResource(final PlatformSecurityContext context,final AssetReadPlatformService readPlatformService,final  ApiRequestParameterHelper apiRequestParameterHelper,
	    		final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService,final DefaultToApiJsonSerializer<AssetData> toApiJsonSerializer,
	    		final  AssetReadPlatformService assetReadPlatformService)  {
	    	this.context=context;
	    	this.apiRequestParameterHelper=apiRequestParameterHelper;
	    	this.commandsSourceWritePlatformService=commandsSourceWritePlatformService;
	    	this.toApiJsonSerializer=toApiJsonSerializer;
	    	this.assetReadPlatformService=assetReadPlatformService;
	    }
	    
	  @POST
	    @Consumes({ MediaType.APPLICATION_JSON })
	    @Produces({ MediaType.APPLICATION_JSON })
	    public String createAsset(final String apiRequestBodyAsJson) {

	        final CommandWrapper commandRequest = new CommandWrapperBuilder().createAsset().withJson(apiRequestBodyAsJson).build();

	        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);

	        return this.toApiJsonSerializer.serialize(result);
	    }
	    
	  @GET
	    @Path("{assetId}")
	    @Consumes({ MediaType.APPLICATION_JSON })
	    @Produces({ MediaType.APPLICATION_JSON })
	    public String retrieveCreditPolicy(@PathParam("assetId") Long assetId,@Context final UriInfo uriInfo) {

	        context.authenticatedUser().validateHasReadPermission(resourceNameForPermissions);
	        final ApiRequestJsonSerializationSettings settings = apiRequestParameterHelper.process(uriInfo.getQueryParameters());  
	        AssetData assetData=assetReadPlatformService.retrieveAssetData(assetId);
	      
	        	 AssetData assetData1=assetReadPlatformService.reteriveDropdownOptions();
	        	 assetData.setAssetTypeDropdown(assetData1.getAssetTypeDropdown());
	        	 assetData.setModelDropdown(assetData1.getModelDropdown());
	      	

	        return this.toApiJsonSerializer.serialize(settings, assetData, RESPONSE_DATA_PARAMETERS);
	    }
	  
	  @GET
	   
	    @Consumes({ MediaType.APPLICATION_JSON })
	    @Produces({ MediaType.APPLICATION_JSON })
	    public String retrieveCreditPolicy(@Context final UriInfo uriInfo) {
	        context.authenticatedUser().validateHasReadPermission(resourceNameForPermissions);
	        List<AssetData> assetData=assetReadPlatformService.retrieveAssetData();
	        final ApiRequestJsonSerializationSettings settings = apiRequestParameterHelper.process(uriInfo.getQueryParameters());
	        return this.toApiJsonSerializer.serialize(settings, assetData, RESPONSE_DATA_PARAMETERS);
	    }
	  
	  
	    @GET
	    @Path("template")
	    @Consumes({ MediaType.APPLICATION_JSON })
	    @Produces({ MediaType.APPLICATION_JSON })
	    public String retrieveTemplate(@Context final UriInfo uriInfo) {

	        context.authenticatedUser().validateHasReadPermission(resourceNameForPermissions);
	        AssetData assetData=assetReadPlatformService.reteriveDropdownOptions();
	        final ApiRequestJsonSerializationSettings settings = apiRequestParameterHelper.process(uriInfo.getQueryParameters());
	        return this.toApiJsonSerializer.serialize(settings, assetData, RESPONSE_DATA_PARAMETERS);
	    }
	    
	    @DELETE
	    @Path("{assetId}")
	    @Consumes({ MediaType.APPLICATION_JSON })
	    @Produces({ MediaType.APPLICATION_JSON })
	    public String deleteAsset(@PathParam("assetId") final Long assetId) {

	        final CommandWrapper commandRequest = new CommandWrapperBuilder().deleteAsset(assetId).build();

	        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);

	        return this.toApiJsonSerializer.serialize(result);
	    }
	    
	    @PUT
	    @Path("{assetId}")
	    @Consumes({ MediaType.APPLICATION_JSON })
	    @Produces({ MediaType.APPLICATION_JSON })
	    public String updateFund(@PathParam("assetId") final Long assetId, final String apiRequestBodyAsJson) {

	        final CommandWrapper commandRequest = new CommandWrapperBuilder().updateAsset(assetId).withJson(apiRequestBodyAsJson).build();

	        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);

	        return this.toApiJsonSerializer.serialize(result);
	    }
	    
	    
}
