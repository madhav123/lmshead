package org.mifosplatform.infrastructure.jobs.data;

public class MessageTemplateData {
private Long id;
private String templateDescription;

public MessageTemplateData(Long id,String templateDescription){
	this.id=id;
	this.templateDescription=templateDescription;
}

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getTemplateDescription() {
	return templateDescription;
}

public void setTemplateDescription(String templateDescription) {
	this.templateDescription = templateDescription;
}

}
