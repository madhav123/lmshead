package org.mifosplatform.portfolio.insurance.domain;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.joda.time.LocalDate;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.springframework.data.jpa.domain.AbstractPersistable;
@Entity
@Table(name="m_insurance")
public class Insurance extends AbstractPersistable<Long>{
	@Column(name="loan_id" ,nullable=false)
private Long loanAccountNo;
	@Column(name="insurer" ,nullable=false)
private String insurer;
	@Column(name="insuredAsset" ,nullable=false)
private String insuredAsset;
	@Column(name="valueOfAsset" ,nullable=false)
private BigDecimal valueOfAsset;
	@Column(name="insuredTerm" ,nullable=false)
private int insuredTerm;
	@Column(name="premiumAmount" ,nullable=false)
private BigDecimal premiumAmount;
	@Column(name="insuredDate" ,nullable=false)
private Date insuredDate;
	@Column(name="expiryDate" ,nullable=false)
private Date expiryDate;
	@Column(name="vechicalRegNo" ,nullable=false)
private String vechicalRegNo;
	@Column(name="vehicalModel" ,nullable=false)
private String vehicalModel;
	
	public Insurance( ){
	}
	
	
	public Insurance(String insurer,String insuredAsset,BigDecimal valueOfAsset,int insuredTerm
			,BigDecimal premiumAmount, LocalDate insuredDate,String vechicalRegNo,String vehicalModel){
		
		this.insurer=insurer;
		this.insuredAsset=insuredAsset;
		this.valueOfAsset=valueOfAsset;
		this.insuredTerm=insuredTerm;
		this.premiumAmount=premiumAmount;
		this.insuredDate=insuredDate.toDate();
		this.expiryDate=insuredDate.plusMonths(insuredTerm).toDate();
		this.vechicalRegNo=vechicalRegNo;
		this.vehicalModel=vehicalModel;
		
	
	
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
	
	public BigDecimal getValueOfAsset() {
		return valueOfAsset;
	}


	public void setValueOfAsset(BigDecimal valueOfAsset) {
		this.valueOfAsset = valueOfAsset;
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


	public void setInsurer(String insurer) {
		this.insurer = insurer;
	}
	public String getInsuredAsset() {
		return insuredAsset;
	}
	public void setInsuredAsset(String insuredAsset) {
		this.insuredAsset = insuredAsset;
	}
	public BigDecimal getvalueOfAsset() {
		return valueOfAsset;
	}
	public void setvalueOfAsset(BigDecimal valueOfAsset) {
		this.valueOfAsset = valueOfAsset;
	}
	public int getInsuredTerm() {
		return insuredTerm;
	}
	public void setInsuredTerm(int insuredTerm) {
		this.insuredTerm = insuredTerm;
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

	public static Insurance fromJson(final JsonCommand command){

		    final String insurer=command.stringValueOfParameterNamed("insurer");
		    final String insuredAsset=command.stringValueOfParameterNamed("insuredAsset");
		    final BigDecimal vlaueOfAsset=command.bigDecimalValueOfParameterNamed("valueOfAsset");
		    final int insuredTerm=command.integerValueOfParameterNamed("insuredTerm");
		    final BigDecimal premiumAmount=command.bigDecimalValueOfParameterNamed("premiumAmount");
		    final LocalDate insuredDate = command.localDateValueOfParameterNamed("insuredDate");
		    final String vechicalRegNo=command.stringValueOfParameterNamed("vechicalRegNo");
		    final String vehicalModel=command.stringValueOfParameterNamed("vehicalModel");
		    return new Insurance(insurer, insuredAsset, vlaueOfAsset, insuredTerm, premiumAmount,
		    		insuredDate,vechicalRegNo,vehicalModel);
	}
	
}

/*

Loan Account Number: 
Insurer: (String) - Dropdown
Insured Asset: (String) - Dropdown    ---- FK
Value of the Asset: (N)
Insured Term:  (N)
Premium Amount: (N) 
Insured Date: (N)
Expiry Date:  Insured Date + Insured Term

*/