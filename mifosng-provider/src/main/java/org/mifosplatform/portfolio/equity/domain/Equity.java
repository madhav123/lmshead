package org.mifosplatform.portfolio.equity.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.portfolio.asset.domain.Asset;
import org.springframework.data.jpa.domain.AbstractPersistable;

import com.sun.swing.internal.plaf.basic.resources.basic;
@Entity
@Table(name="m_equity")
public class Equity extends AbstractPersistable<Long>{
	@Column(name="equityType" ,nullable=false)
	private String equityType;
	@Column(name="model" ,nullable=false)
	private String model;
	@Column(name="marketPrice" ,nullable=false)
	private BigDecimal marketPrice;
	@Column(name="actualPrice" ,nullable=false)
	private BigDecimal actualPrice;
	@Column(name="loanId",nullable=false)
	private Long loanId;
	public Equity(){
	}
	public static Equity fromJson(final Long loanId,final JsonCommand command)
	 {
		  String model;
		  BigDecimal marketPrice;
	    final String equityType=command.stringValueOfParameterNamed("equityType");
	    if(!equityType.equalsIgnoreCase("cash")){
	     model=command.stringValueOfParameterNamed("model");  
	     marketPrice=command.bigDecimalValueOfParameterNamed("marketPrice");}
	    else{
	    	marketPrice=null;
	    	model=null;
	    }
	    final BigDecimal actualPrice=command.bigDecimalValueOfParameterNamed("actualPrice");

	  return new Equity(equityType,model,marketPrice,actualPrice,loanId);
	 }
	
	public Equity(final String equityType,final String model,final BigDecimal marketPrice, final BigDecimal actualPrice ,Long loanId)
	{
		this.equityType=equityType;
		this.model=model;
		this.marketPrice=marketPrice;
		this.actualPrice=actualPrice;
		this.loanId=loanId;
	}
	
	public Equity(final String equityType,final BigDecimal marketPrice, final BigDecimal actualPrice )
	{
		this.equityType=equityType;
		this.marketPrice=marketPrice;
		this.actualPrice=actualPrice;
	}
	
	public String getEquityType() {
		return equityType;
	}
	public void setEquityType(String equityType) {
		this.equityType = equityType;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	public BigDecimal getActualPrice() {
		return actualPrice;
	}
	public void setActualPrice(BigDecimal actualPrice) {
		this.actualPrice = actualPrice;
	}
	
	

}
