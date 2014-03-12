package org.mifosplatform.portfolio.asset.data;

import java.util.List;

public class Model {
String models;
Long id;

public Model(String models,Long id)
{
	this.models=models;
	this.id=id;

}
public String getModels() {
	return models;
}

public void setModels(String models) {
	this.models = models;
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}


}
