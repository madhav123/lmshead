package org.mifosplatform.portfolio.equity.serializatin;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.mifosplatform.infrastructure.codes.CodeConstants.CODEVALUE_JSON_INPUT_PARAMS;
import org.mifosplatform.infrastructure.core.data.ApiParameterError;
import org.mifosplatform.infrastructure.core.data.DataValidatorBuilder;
import org.mifosplatform.infrastructure.core.exception.InvalidJsonException;
import org.mifosplatform.infrastructure.core.exception.PlatformApiDataValidationException;
import org.mifosplatform.infrastructure.core.serialization.FromJsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
@Service
public class EquityCommandFromApiJsonDeserializer {
	 private final Set<String> supportedParameters = new HashSet<String>(Arrays.asList("equityType","model","marketPrice","actualPrice","locale"));
	    private final FromJsonHelper fromApiJsonHelper;
	    @Autowired
	    public EquityCommandFromApiJsonDeserializer(final FromJsonHelper fromApiJsonHelper) {
	        this.fromApiJsonHelper = fromApiJsonHelper;
	    }
	    public void validateForCreate(final String json) {
	        if (StringUtils.isBlank(json)) { throw new InvalidJsonException(); }

	        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
	        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, this.supportedParameters);

	        final List<ApiParameterError> dataValidationErrors = new ArrayList<ApiParameterError>();
	        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("code.value");
	        final JsonElement element = this.fromApiJsonHelper.parse(json);
	        
	        final String equityType = fromApiJsonHelper.extractStringNamed("equityType", element);
	        baseDataValidator.reset().parameter(CODEVALUE_JSON_INPUT_PARAMS.NAME.getValue()).value(equityType).notBlank().notExceedingLengthOf(100);
	        
	        final String model = fromApiJsonHelper.extractStringNamed("model", element);
	        if(!equityType.equalsIgnoreCase("cash")){
	        baseDataValidator.reset().parameter(CODEVALUE_JSON_INPUT_PARAMS.NAME.getValue()).value(model).notBlank().notExceedingLengthOf(100);
	        }
	       
	        final Integer marketPrice=fromApiJsonHelper.extractIntegerWithLocaleNamed("marketPrice", element);
	        if(!equityType.equalsIgnoreCase("cash")){
	        baseDataValidator.reset().parameter("marketPrice").value(marketPrice).integerGreaterThanZero();}
	      
	        final Integer actualPrice=fromApiJsonHelper.extractIntegerWithLocaleNamed("actualPrice", element);
	        baseDataValidator.reset().parameter("actualPrice").value(actualPrice).integerGreaterThanZero();
	        
	  
	        throwExceptionIfValidationWarningsExist(dataValidationErrors);
	    }

	 

	    private void throwExceptionIfValidationWarningsExist(final List<ApiParameterError> dataValidationErrors) {
	        if (!dataValidationErrors.isEmpty()) { throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist",
	                "Validation errors exist.", dataValidationErrors); }
	    }
	    
}
