package org.mifosplatform.portfolio.asset.serialization;

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
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

@Component
public class AssetCommandFromApiJsonDeserializer {
	  private final Set<String> supportedParameters = new HashSet<String>(Arrays.asList("assetCode","assetDescription","assetType","model","make","purchaseValue","saleValue","locale"));
	    private final FromJsonHelper fromApiJsonHelper;
	    @Autowired
	    public AssetCommandFromApiJsonDeserializer(final FromJsonHelper fromApiJsonHelper) {
	        this.fromApiJsonHelper = fromApiJsonHelper;
	    }
	    public void validateForCreate(final String json) {
	        if (StringUtils.isBlank(json)) { throw new InvalidJsonException(); }

	        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
	        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, this.supportedParameters);

	        final List<ApiParameterError> dataValidationErrors = new ArrayList<ApiParameterError>();
	        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("code.value");
	        
	        
	        final JsonElement element = this.fromApiJsonHelper.parse(json);

	        
	        final String assetCode = fromApiJsonHelper.extractStringNamed("assetCode", element);
	        baseDataValidator.reset().parameter(CODEVALUE_JSON_INPUT_PARAMS.NAME.getValue()).value(assetCode).notBlank().notExceedingLengthOf(100);
	        
	        final Integer purchaseValue=fromApiJsonHelper.extractIntegerWithLocaleNamed("purchaseValue", element);
	        baseDataValidator.reset().parameter("purchaseValue").value(purchaseValue).integerGreaterThanZero();
	        
	        final Integer saleValue=fromApiJsonHelper.extractIntegerWithLocaleNamed("saleValue", element);
	        baseDataValidator.reset().parameter("saleValue").value(saleValue).integerGreaterThanZero();
	        
	        /*final Integer liveStok=fromApiJsonHelper.extractIntegerWithLocaleNamed("liveStok", element);
	        baseDataValidator.reset().parameter("liveStok").value(vehicle).integerGreaterThanZero();
	        
	        final Integer m_other1=fromApiJsonHelper.extractIntegerWithLocaleNamed("m_other1", element);
	        baseDataValidator.reset().parameter("m_other1").value(vehicle).integerGreaterThanZero();
	        
	        final Integer m_other2=fromApiJsonHelper.extractIntegerWithLocaleNamed("m_other2", element);
	        baseDataValidator.reset().parameter("m_other2").value(vehicle).integerGreaterThanZero();
	        
	        final Integer selfIncome=fromApiJsonHelper.extractIntegerWithLocaleNamed("selfIncome", element);
	        baseDataValidator.reset().parameter("selfIncome").value(selfIncome).integerGreaterThanZero();
	        
	        final Integer pouseContributeToFamily=fromApiJsonHelper.extractIntegerWithLocaleNamed("pouseContributeToFamily", element);
	        baseDataValidator.reset().parameter("pouseContributeToFamily").value(pouseContributeToFamily).integerGreaterThanZero();
	        
	        final Integer familyMemberContributionToFamily=fromApiJsonHelper.extractIntegerWithLocaleNamed("familyMemberContributionToFamily", element);
	        baseDataValidator.reset().parameter("familyMemberContributionToFamily").value(familyMemberContributionToFamily).integerGreaterThanZero();
	        
	        final Integer familyMember2ContributionToFamily=fromApiJsonHelper.extractIntegerWithLocaleNamed("familyMember2ContributionToFamily", element);
	        baseDataValidator.reset().parameter("familyMember2ContributionToFamily").value(familyMember2ContributionToFamily).integerGreaterThanZero();
	        
	        final Integer IncomeContributionFromOtherSource1=fromApiJsonHelper.extractIntegerWithLocaleNamed("IncomeContributionFromOtherSource1", element);
	        baseDataValidator.reset().parameter("IncomeContributionFromOtherSource1").value(IncomeContributionFromOtherSource1).integerGreaterThanZero();
	       
	        final Integer IncomeContributionFromOtherSource2=fromApiJsonHelper.extractIntegerWithLocaleNamed("IncomeContributionFromOtherSource2", element);
	        baseDataValidator.reset().parameter("IncomeContributionFromOtherSource2").value(IncomeContributionFromOtherSource2).integerGreaterThanZero();
	       
	        final Integer i_other1=fromApiJsonHelper.extractIntegerWithLocaleNamed("i_other1", element);
	        baseDataValidator.reset().parameter("i_other1").value(i_other1).integerGreaterThanZero();
	        
	        final Integer i_other2=fromApiJsonHelper.extractIntegerWithLocaleNamed("i_other2", element);
	        baseDataValidator.reset().parameter("i_other2").value(i_other1).integerGreaterThanZero();
	       
	        final Integer consumption=fromApiJsonHelper.extractIntegerWithLocaleNamed("consumption", element);
	        baseDataValidator.reset().parameter("consumption").value(consumption).integerGreaterThanZero();
	        
	        final Integer monthlyMedicalExpenditure=fromApiJsonHelper.extractIntegerWithLocaleNamed("monthlyMedicalExpenditure", element);
	        baseDataValidator.reset().parameter("monthlyMedicalExpenditure").value(monthlyMedicalExpenditure).integerGreaterThanZero();
	       
	        
	        final Integer educationExpenditure=fromApiJsonHelper.extractIntegerWithLocaleNamed("educationExpenditure", element);
	        baseDataValidator.reset().parameter("educationExpenditure").value(educationExpenditure).integerGreaterThanZero();
	        
	        
	        final Integer monthlyRegularSaving=fromApiJsonHelper.extractIntegerWithLocaleNamed("monthlyRegularSaving", element);
	        baseDataValidator.reset().parameter("monthlyRegularSaving").value(monthlyRegularSaving).integerGreaterThanZero();
	        
	        
	        final Integer MonthlyInsurancePremium=fromApiJsonHelper.extractIntegerWithLocaleNamed("MonthlyInsurancePremium", element);
	        baseDataValidator.reset().parameter("MonthlyInsurancePremium").value(MonthlyInsurancePremium).integerGreaterThanZero();
	        
	        
	        final Integer monthlyLoanRepayment=fromApiJsonHelper.extractIntegerWithLocaleNamed("monthlyLoanRepayment", element);
	        baseDataValidator.reset().parameter("monthlyLoanRepayment").value(monthlyLoanRepayment).integerGreaterThanZero();
	        
	        final Integer o_other1=fromApiJsonHelper.extractIntegerWithLocaleNamed("o_other1", element);
	        baseDataValidator.reset().parameter("o_other1").value(o_other1).integerGreaterThanZero();
	        
	        final Integer o_other2=fromApiJsonHelper.extractIntegerWithLocaleNamed("o_other2", element);
	        baseDataValidator.reset().parameter("o_other2").value(o_other2).integerGreaterThanZero();
	        
	        final LocalDate startDate = fromApiJsonHelper.extractLocalDateNamed("startDate", element);
	        baseDataValidator.reset().parameter("startDate").value(startDate).notBlank();
	        final String[] services = fromApiJsonHelper.extractArrayNamed("services", element);
	        baseDataValidator.reset().parameter("services").value(services).arrayNotEmpty();
	        final String provisioingSystem = fromApiJsonHelper.extractStringNamed("provisioingSystem", element);
	        baseDataValidator.reset().parameter("provisioingSystem").value(provisioingSystem).notBlank();
	      */
	        throwExceptionIfValidationWarningsExist(dataValidationErrors);
	    }

	 

	    private void throwExceptionIfValidationWarningsExist(final List<ApiParameterError> dataValidationErrors) {
	        if (!dataValidationErrors.isEmpty()) { throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist",
	                "Validation errors exist.", dataValidationErrors); }
	    }
}
