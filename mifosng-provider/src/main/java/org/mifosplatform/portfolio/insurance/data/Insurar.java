package org.mifosplatform.portfolio.insurance.data;

public class Insurar {
	private String insurer;
	private Long id;
	
	public Insurar(String insurer,Long id)
	{
		this.insurer=insurer;
		this.id=id;
	}

	public String getInsurer() {
		return insurer;
	}

	public void setInsurer(String insurer) {
		this.insurer = insurer;
	}


}
