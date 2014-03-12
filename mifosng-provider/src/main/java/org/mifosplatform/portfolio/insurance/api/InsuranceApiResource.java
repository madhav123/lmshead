package org.mifosplatform.portfolio.insurance.api;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.joda.time.LocalDate;
import org.mifosplatform.commands.domain.CommandWrapper;
import org.mifosplatform.commands.service.CommandWrapperBuilder;
import org.mifosplatform.commands.service.PortfolioCommandSourceWritePlatformService;
import org.mifosplatform.infrastructure.core.api.ApiRequestParameterHelper;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.serialization.ApiRequestJsonSerializationSettings;
import org.mifosplatform.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.mifosplatform.infrastructure.core.serialization.FromJsonHelper;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.mifosplatform.portfolio.asset.data.AssetData;
import org.mifosplatform.portfolio.asset.service.AssetReadPlatformService;
import org.mifosplatform.portfolio.client.api.ClientApiConstants;
import org.mifosplatform.portfolio.insurance.data.InsuranceData;
import org.mifosplatform.portfolio.insurance.service.InsuranceReadPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
@Path("/insurances")
@Component
@Scope("singleton")
public class InsuranceApiResource {
	  private final Set<String> RESPONSE_DATA_PARAMETERS = new HashSet<String>(Arrays.asList("insurer","insuredAsset","valueOfAsset","insuredTerm","premiumAmount","insuredDate","vechicalRegNo","vehicalModel","locale"));

	    private final String resourceNameForPermissions = "INSURANCE";

	    private final PlatformSecurityContext context;
	    private final ApiRequestParameterHelper apiRequestParameterHelper;
	    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;
	    private final DefaultToApiJsonSerializer<InsuranceData> toApiJsonSerializer;
	    private final InsuranceReadPlatformService insuranceReadPlatformService;
	    private final AssetReadPlatformService assetReadPlatformService;
	    private final FromJsonHelper fromApiJsonHelper;
	    @Autowired
	    public InsuranceApiResource(final PlatformSecurityContext context,final ApiRequestParameterHelper apiRequestParameterHelper,final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService,
	    		final DefaultToApiJsonSerializer<InsuranceData> toApiJsonSerializer,final InsuranceReadPlatformService insuranceReadPlatformService, final AssetReadPlatformService assetReadPlatformService,final FromJsonHelper fromApiJsonHelper){
	    	this.context=context;
	    	this.apiRequestParameterHelper=apiRequestParameterHelper;
	    	this.commandsSourceWritePlatformService=commandsSourceWritePlatformService;
	    	this.toApiJsonSerializer=toApiJsonSerializer;
	    	this.insuranceReadPlatformService=insuranceReadPlatformService;
	    	this.assetReadPlatformService=assetReadPlatformService;
	    	this.fromApiJsonHelper=fromApiJsonHelper;
	    	
	    }
	    
	    @GET
	    @Path("template")
	    @Consumes({ MediaType.APPLICATION_JSON })
	    @Produces({ MediaType.APPLICATION_JSON })
	    public String retrieveTemplate(@Context final UriInfo uriInfo) {
	        context.authenticatedUser().validateHasReadPermission(resourceNameForPermissions);
	       // List<AssetData> assetDropdown=assetReadPlatformService.retrieveAssetData();
	      //  List<AssetData> assetDropdown=insuranceReadPlatformService.reteriveDropdownOptions();
	        InsuranceData insuranceData=insuranceReadPlatformService.reteriveDropdownOptions();
	 
	 //=InsuranceReadPlatformService.reteriveDropdownOptions();
	        final ApiRequestJsonSerializationSettings settings = apiRequestParameterHelper.process(uriInfo.getQueryParameters());
	        return this.toApiJsonSerializer.serialize(settings, insuranceData, RESPONSE_DATA_PARAMETERS);
	    }
	    
	    @POST
	    @Path("{loanId}")
	    @Consumes({ MediaType.APPLICATION_JSON })
	    @Produces({ MediaType.APPLICATION_JSON })
	    public String createInsuracne(@PathParam("loanId") Long loanId,final String apiRequestBodyAsJson) {

	        final CommandWrapper commandRequest = new CommandWrapperBuilder().createInsurance(loanId).withJson(apiRequestBodyAsJson).build();

	        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);

	        return this.toApiJsonSerializer.serialize(result);
	    }
	    
	    @POST
	    @Consumes({ MediaType.APPLICATION_JSON })
	    @Produces({ MediaType.APPLICATION_JSON })
	    public String findExpDate(final String apiRequestBodyAsJson) throws JSONException {
	    	 final JsonElement commandEl = this.fromApiJsonHelper.parse(apiRequestBodyAsJson);
	    	   final Long insuredt= this.fromApiJsonHelper.extractLongNamed("insuredTerm", commandEl);
	    	   //final LocalDate insureddate=this.fromApiJsonHelper.extractLocalDateNamed("insuredDate", commandEl);
	    	   LocalDate expInsuredDate= new LocalDate();
	    	   expInsuredDate= expInsuredDate.plusMonths(insuredt.intValue());
	    	   JSONObject jsonObj = new JSONObject("{\"expInsuredDate\":\""+expInsuredDate+"\"}");
	    	return jsonObj.toString();
	    }
	    
	    
	    @GET
	    @Path("{loanId}")
	    @Consumes({ MediaType.APPLICATION_JSON })
	    @Produces({ MediaType.APPLICATION_JSON })
	    public String retrieveInsurance(@PathParam("loanId") Long loanId,@Context final UriInfo uriInfo) {

	        context.authenticatedUser().validateHasReadPermission(resourceNameForPermissions);
	     
	 InsuranceData insurences=insuranceReadPlatformService.retrieveInsuracetDataSingle(loanId);
	        final ApiRequestJsonSerializationSettings settings = apiRequestParameterHelper.process(uriInfo.getQueryParameters());
	        return this.toApiJsonSerializer.serialize(settings, insurences, RESPONSE_DATA_PARAMETERS);
	    }
	    
	    
	    @DELETE
	    @Path("{loanId}")
	    @Consumes({ MediaType.APPLICATION_JSON })
	    @Produces({ MediaType.APPLICATION_JSON })
	    public String deleteInsurance(@PathParam("loanId") final Long loanId) {

	        final CommandWrapper commandRequest = new CommandWrapperBuilder().deleteInsurance(loanId).build();

	        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);

	        return this.toApiJsonSerializer.serialize(result);
	    }
	    
	    
}
