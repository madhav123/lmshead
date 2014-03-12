package org.mifosplatform.portfolio.asset.data;

import java.util.List;

public class AssetType {
public String assetType;
public Long id;

public AssetType(String assetType,Long id)
{
	this.assetType=assetType;
	this.id=id;
}
public String getAssetType() {
	return assetType;
}

public void setAssetType(String assetType) {
	this.assetType = assetType;
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}



}
