package org.mifosplatform.portfolio.equity.api;

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


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.mifosplatform.portfolio.asset.data.AssetData;
import org.mifosplatform.portfolio.equity.data.EquityData;
import org.mifosplatform.portfolio.equity.service.EquityReadPlatformService;

@Path("/equitys")
@Component
@Scope("singleton")
public class EquityApiResource {
	  private final Set<String> RESPONSE_DATA_PARAMETERS = new HashSet<String>(Arrays.asList("equityType","model","marketPrice","actualPrice","locale"));
	    private final String resourceNameForPermissions = "EQUITY";
	    private final PlatformSecurityContext context;
	    private final ApiRequestParameterHelper apiRequestParameterHelper;
	    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;
	    private final DefaultToApiJsonSerializer<EquityData> toApiJsonSerializer;
	    private final EquityReadPlatformService equityReadPlatformService;
	    
	    @Autowired
	    public EquityApiResource(final PlatformSecurityContext context,final  ApiRequestParameterHelper apiRequestParameterHelper,
	    		final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService,
	    		final DefaultToApiJsonSerializer<EquityData> toApiJsonSerializer,final EquityReadPlatformService equityReadPlatformService)  {
	    	this.context=context;
	    	this.apiRequestParameterHelper=apiRequestParameterHelper;
	    	this.commandsSourceWritePlatformService=commandsSourceWritePlatformService;
	    	this.toApiJsonSerializer=toApiJsonSerializer;
	    	this.equityReadPlatformService=equityReadPlatformService;
	    	
	    }
		    @POST
		    @Consumes({ MediaType.APPLICATION_JSON })
		    @Produces({ MediaType.APPLICATION_JSON })
		    @Path("{loanId}")
		    public String createEquity(@PathParam("loanId") Long loanId,final String apiRequestBodyAsJson) {

		        final CommandWrapper commandRequest = new CommandWrapperBuilder().createEquity(loanId).withJson(apiRequestBodyAsJson).build();

		        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);

		        return this.toApiJsonSerializer.serialize(result);
		    }
		  
		  
		    @GET
		    @Path("template")
		    @Consumes({ MediaType.APPLICATION_JSON })
		    @Produces({ MediaType.APPLICATION_JSON })
		    public String retrieveTemplate(@Context final UriInfo uriInfo) {
		        context.authenticatedUser().validateHasReadPermission(resourceNameForPermissions);
		        EquityData equityData=equityReadPlatformService.reteriveDropdownOptions();
		        final ApiRequestJsonSerializationSettings settings = apiRequestParameterHelper.process(uriInfo.getQueryParameters());
		        return this.toApiJsonSerializer.serialize(settings, equityData, RESPONSE_DATA_PARAMETERS);
		    }
		    
	/*	    @GET
		    @Consumes({ MediaType.APPLICATION_JSON })
		    @Produces({ MediaType.APPLICATION_JSON })
		    @Path("{loanId}")
		    public String retriveAllEquites(@PathParam("loanId") Long loanId, @Context final UriInfo uriInfo){
		        context.authenticatedUser().validateHasReadPermission(resourceNameForPermissions);
		    	List<EquityData> Equites=equityReadPlatformService.retrieveEquityDataAll(loanId);
		    	  final ApiRequestJsonSerializationSettings settings = apiRequestParameterHelper.process(uriInfo.getQueryParameters());
			        return this.toApiJsonSerializer.serialize(settings, Equites, RESPONSE_DATA_PARAMETERS);
		    }*/
		    
		    @GET
		    @Consumes({ MediaType.APPLICATION_JSON })
		    @Produces({ MediaType.APPLICATION_JSON })
		    @Path("{equityId}")
		    public String retriveAllEquites(@PathParam("equityId") Long equityId, @Context final UriInfo uriInfo){
		        context.authenticatedUser().validateHasReadPermission(resourceNameForPermissions);
		    EquityData Equites=equityReadPlatformService.retrieveEquityData(equityId);
		    	  final ApiRequestJsonSerializationSettings settings = apiRequestParameterHelper.process(uriInfo.getQueryParameters());
			        return this.toApiJsonSerializer.serialize(settings, Equites, RESPONSE_DATA_PARAMETERS);
		    }
	    
}
