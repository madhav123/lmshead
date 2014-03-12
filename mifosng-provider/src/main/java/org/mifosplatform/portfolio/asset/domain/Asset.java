package org.mifosplatform.portfolio.asset.domain;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.springframework.data.jpa.domain.AbstractPersistable;

import org.apache.commons.lang.StringUtils;
@Entity
@Table(name="m_asset")
public class Asset extends  AbstractPersistable<Long> {
	 
	@Column(name="assetCode" ,nullable=false)
	private String assetCode;
	@Column(name="assetDescription" ,nullable=false)
	private String assetDescription;  
	@Column(name="assetType" ,nullable=false)
	
	private String assetType;
	@Column(name="make" ,nullable=false)
	private String make;
	@Column(name="model" ,nullable=false)
	private String model;
	@Column(name="purchaseValue" ,nullable=false)
	private BigDecimal purchaseValue;
	@Column(name="saleValue" ,nullable=false)
	private BigDecimal saleValue;
	
	public String getAssetCode() {
		return assetCode;
	}
	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}
	public String getAssetDescription() {
		return assetDescription;
	}
	public void setAssetDescription(String assetDescription) {
		this.assetDescription = assetDescription;
	}
	public String getAssetType() {
		return assetType;
	}
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public BigDecimal getPurchaseValue() {
		return purchaseValue;
	}
	public void setPurchaseValue(BigDecimal purchaseValue) {
		this.purchaseValue = purchaseValue;
	}
	public BigDecimal getSaleValue() {
		return saleValue;
	}
	public void setSaleValue(BigDecimal saleValue) {
		this.saleValue = saleValue;
	}
	
	public Asset(){
	}
	
	public static Asset fromJson(final JsonCommand command)
	 {
	       
	    final String assetCode=command.stringValueOfParameterNamed("assetCode");
	    final String assetDescription=command.stringValueOfParameterNamed("assetDescription");  
	    final String assetType=command.stringValueOfParameterNamed("assetType");
	    final String make=command.stringValueOfParameterNamed("make");
	    final String model=command.stringValueOfParameterNamed("model");
	    final BigDecimal purchaseValue=command.bigDecimalValueOfParameterNamed("purchaseValue");
	    final BigDecimal saleValue=command.bigDecimalValueOfParameterNamed("saleValue");
	  return new Asset(assetCode,assetDescription,assetType,make,model,purchaseValue,saleValue);
	 }
	
	public Asset(String assetCode,String assetDescription,String assetType,String make,String model,BigDecimal purchaseValue,BigDecimal saleValue)
	{
		this.assetCode=assetCode;
		this.assetDescription=assetDescription;
		this.assetType=assetType;
		this.make=make;
		this.model=model;
		this.purchaseValue=purchaseValue;
		this.saleValue=saleValue;
	}
	
	public Map<String, Object> update(final JsonCommand command) {

        final Map<String, Object> actualChanges = new LinkedHashMap<String, Object>(7);

   
        
         final String  assetCodeParamName="assetCode";
        if(command.isChangeInStringParameterNamed(assetCodeParamName, this.assetCode)){
        	final String newValue=command.stringValueOfParameterNamed(assetCodeParamName);
        	 actualChanges.put(assetCodeParamName, newValue);
        	this.assetCode=StringUtils.defaultIfEmpty(newValue, null);
        	
        	}
        
        final String  assetDescriptionParamName="assetDescription";
        if(command.isChangeInStringParameterNamed(assetDescriptionParamName, this. assetDescription)){
        	final String newValue=command.stringValueOfParameterNamed(assetDescriptionParamName);
        	actualChanges.put(assetDescriptionParamName, newValue);
        	this.assetDescription=StringUtils.defaultIfEmpty(newValue, null);
     	   
        
        	}
        
        final String  assetTypeParamName="assetType";
        if(command.isChangeInStringParameterNamed(assetTypeParamName, this. assetType)){
        	final String newValue=command.stringValueOfParameterNamed(assetTypeParamName);
        	 actualChanges.put(assetTypeParamName, newValue);
        	this.assetType=StringUtils.defaultIfEmpty(newValue, null);
        	
        	}
        
        final String  makeParamName="make";
        if(command.isChangeInStringParameterNamed(makeParamName, this. make)){
        	final String newValue=command.stringValueOfParameterNamed(makeParamName);
       	 actualChanges.put(makeParamName, newValue);
        	this.make=StringUtils.defaultIfEmpty(newValue, null);
       
       
        	}
        
        final String  modelParamName="model";
        if(command.isChangeInStringParameterNamed(modelParamName, this. model)){
        	final String newValue=command.stringValueOfParameterNamed(modelParamName);
        	 actualChanges.put(modelParamName, newValue);
        	this.model=StringUtils.defaultIfEmpty(newValue, null);
     
        	}
        
     


        
        final String purchaseValueParamName = "purchaseValue";
        if (command.isChangeInBigDecimalParameterNamed(purchaseValueParamName, this.purchaseValue )) {
            final BigDecimal newValue = command.bigDecimalValueOfParameterNamed(purchaseValueParamName);
            actualChanges.put(purchaseValueParamName, newValue);
            this.purchaseValue=newValue;
        }
            
            final String saleValueParamName = "saleValue";
            if (command.isChangeInBigDecimalParameterNamed(saleValueParamName, this.saleValue )) {
                final BigDecimal newValue = command.bigDecimalValueOfParameterNamed(saleValueParamName);
                actualChanges.put(saleValueParamName, newValue);
                this.saleValue=newValue;
              
          
        } 
            return actualChanges;
	}
}

