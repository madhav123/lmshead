package org.mifosplatform.portfolio.equity.data;
import java.math.BigDecimal;
import java.util.List;
public class EquityData {
    private Long id;
	private String equityType;
	private String model;
	private BigDecimal marketPrice;
	private BigDecimal actualPrice;
	private List<EquityType> equityTypes; 
	public EquityData(final Long id,final String equityType,final String model,final BigDecimal marketPrice, final BigDecimal actualPrice )
	{
		this.id=id;
		this.equityType=equityType;
		this.model=model;
		this.marketPrice=marketPrice;
		this.actualPrice=actualPrice;
	}
	
	public EquityData(final List<EquityType> equityTypes){
		this.equityTypes=equityTypes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<EquityType> getEquityTypes() {
		return equityTypes;
	}

	public void setEquityTypes(List<EquityType> equityTypes) {
		this.equityTypes = equityTypes;
	}
	

}
