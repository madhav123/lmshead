package org.mifosplatform.portfolio.asset.data;

import java.math.BigDecimal;
import java.util.*;
public class AssetData {
	private Long id;
	private String assetCode;
	private String assetDescription;  
	private String assetType;
	private String make;
	private String model;
	private BigDecimal purchaseValue;
	private BigDecimal saleValue;
	
	private  List<AssetType> assetTypeDropdown;
	private  List<Model> modelDropdown;
	
	public AssetData(List<AssetType> assetTypeDropdown,List<Model> modelDropdown)
	{
		this.assetTypeDropdown=assetTypeDropdown;
		this.modelDropdown=modelDropdown;
	}
	
	
	public AssetData(Long id,String assetCode,String assetDescription,String assetType, String make,String model,BigDecimal purchaseValue,BigDecimal saleValue)
	{
		this.id=id;
		this.assetCode=assetCode;
		this.assetDescription=assetDescription;
		this.assetType=assetType;
		this.make=make;
		this.model=model;
		this.purchaseValue=purchaseValue;
		this.saleValue=saleValue;
		
	}
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}











	public List<AssetType> getAssetTypeDropdown() {
		return assetTypeDropdown;
	}


	public void setAssetTypeDropdown(List<AssetType> assetTypeDropdown) {
		this.assetTypeDropdown = assetTypeDropdown;
	}


	public List<Model> getModelDropdown() {
		return modelDropdown;
	}


	public void setModelDropdown(List<Model> modelDropdown) {
		this.modelDropdown = modelDropdown;
	}


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
	
}
