package org.mifosplatform.portfolio.equity.data;
public class EquityType {
private String  equityType;
private Long id;
public EquityType(String equityType,Long id){
	this.equityType=equityType;
	this.id=id;
}
public String getEquityType() {
	return equityType;
}
public void setEquityType(String equityType) {
	this.equityType = equityType;
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}


}
