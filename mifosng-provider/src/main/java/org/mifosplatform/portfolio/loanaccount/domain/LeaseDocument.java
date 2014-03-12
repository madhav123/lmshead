package org.mifosplatform.portfolio.loanaccount.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "m_lease_document")
public class LeaseDocument {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	@Column(name="loan_id")
	private Long loanId;

	@Column(name ="filename")
	private String fileName;
	public LeaseDocument(){

	}
	public LeaseDocument(Long loanId,String filename){
		
		this.loanId=loanId;
		this.fileName=filename;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getloanId() {
		return loanId;
	}
	public void setloanId(Long loanId) {
		this.loanId = loanId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
