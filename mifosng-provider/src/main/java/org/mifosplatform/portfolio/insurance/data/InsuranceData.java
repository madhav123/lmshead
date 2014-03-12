package org.mifosplatform.portfolio.insurance.data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import org.mifosplatform.portfolio.asset.data.AssetData;
import org.mifosplatform.portfolio.asset.data.Model;
import org.mifosplatform.portfolio.asset.domain.Asset;

public class InsuranceData {
private Long id;
	
private Long loanAccountNo;

private String insurer;

private String insuredAsset;

private BigDecimal valueOfAsset;

private int insuredTerm;

private BigDecimal premiumAmount;

private Date insuredDate;

private Date expiryDate;

private String vechicalRegNo;

private String vehicalModel;

private  List<AssetData> assetDropdown;
private List<Insurar> insurarDropdown;
private List<Model> modelDropdown;

public InsuranceData(List<AssetData> assetDropdown,List<Insurar> insurarDropdown,  List<Model> modelDropdown){
	this.assetDropdown=assetDropdown;
	this.insurarDropdown=insurarDropdown;
	this.modelDropdown=modelDropdown;
}

public InsuranceData(Long id,Long loanAccountNo,String insurer,String insuredAsset,int insuredTerm,BigDecimal valueOfAsset, 
		BigDecimal premiumAmount,Date insuredDate,Date expiryDate,String vechicalRegNo,String vehicalModel){
	this.id=id;
	this.loanAccountNo=loanAccountNo;
	this.insurer=insurer;
	this.insuredAsset=insuredAsset;
	this.valueOfAsset=valueOfAsset;
	this.insuredTerm=insuredTerm;
	this.premiumAmount=premiumAmount;
	this.insuredDate=insuredDate;
	this.expiryDate=expiryDate;
	this.vechicalRegNo=vechicalRegNo;
	this.vehicalModel=vehicalModel;
}


public String getVechicalRegNo() {
	return vechicalRegNo;
}

public void setVechicalRegNo(String vechicalRegNo) {
	this.vechicalRegNo = vechicalRegNo;
}

public String getVehicalModel() {
	return vehicalModel;
}

public void setVehicalModel(String vehicalModel) {
	this.vehicalModel = vehicalModel;
}

public List<Insurar> getInsurarDropdown() {
	return insurarDropdown;
}

public void setInsurarDropdown(List<Insurar> insurarDropdown) {
	this.insurarDropdown = insurarDropdown;
}

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public Long getLoanAccountNo() {
	return loanAccountNo;
}

public void setLoanAccountNo(Long loanAccountNo) {
	this.loanAccountNo = loanAccountNo;
}

public String getInsurer() {
	return insurer;
}

public void setInsurer(String insurer) {
	this.insurer = insurer;
}

public String getInsuredAsset() {
	return insuredAsset;
}

public void setInsuredAsset(String insuredAsset) {
	this.insuredAsset = insuredAsset;
}

public BigDecimal getVlaueOfAsset() {
	return valueOfAsset;
}

public void setVlaueOfAsset(BigDecimal vlaueOfAsset) {
	this.valueOfAsset = vlaueOfAsset;
}

public int getInsuredTerm() {
	return insuredTerm;
}

public void setInsuredTerm(int insuredTerm) {
	this.insuredTerm = insuredTerm;
}



public BigDecimal getValueOfAsset() {
	return valueOfAsset;
}

public void setValueOfAsset(BigDecimal valueOfAsset) {
	this.valueOfAsset = valueOfAsset;
}

public BigDecimal getPremiumAmount() {
	return premiumAmount;
}

public void setPremiumAmount(BigDecimal premiumAmount) {
	this.premiumAmount = premiumAmount;
}

public Date getInsuredDate() {
	return insuredDate;
}

public void setInsuredDate(Date insuredDate) {
	this.insuredDate = insuredDate;
}

public Date getExpiryDate() {
	return expiryDate;
}

public void setExpiryDate(Date expiryDate) {
	this.expiryDate = expiryDate;
}

public List<AssetData> getAssetDropdown() {
	return assetDropdown;
}

public void setAssetDropdown(List<AssetData> assetDropdown) {
	this.assetDropdown = assetDropdown;
}


}
