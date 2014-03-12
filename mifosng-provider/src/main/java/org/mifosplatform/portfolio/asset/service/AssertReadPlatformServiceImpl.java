package org.mifosplatform.portfolio.asset.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.mifosplatform.infrastructure.core.service.RoutingDataSource;

import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.mifosplatform.portfolio.asset.data.AssetData;
import org.mifosplatform.portfolio.asset.data.AssetType;
import org.mifosplatform.portfolio.asset.data.Model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
@Service
public class AssertReadPlatformServiceImpl implements AssetReadPlatformService{
    	 private final JdbcTemplate jdbcTemplate;
	     private final PlatformSecurityContext context;

	    @Autowired
	    public AssertReadPlatformServiceImpl(final PlatformSecurityContext context, final RoutingDataSource dataSource) {
	        this.context = context;
	        this.jdbcTemplate = new JdbcTemplate(dataSource);
	    }
	     
	     private static final class AssetMapper implements RowMapper<AssetType>{

	    	static String schema1="select mv.code_value,mv.id from m_code m,m_code_value mv where m.id=mv.code_id and m.id=16";
	    			@Override
			public AssetType mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String values=rs.getString("code_value");
				Long id=rs.getLong("id");

				return new AssetType(values,id);
			}	
	    }
	     private static final class ModelMapper implements RowMapper<Model>{

	     	static String schema2="select mv.code_value,mv.id from m_code m,m_code_value mv where m.id=mv.code_id and m.id=17";
			@Override
			public Model mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String values=rs.getString("code_value");
				Long id=rs.getLong("id");

				return new Model(values,id);
			}	
	    }
	     
	     
	    private static final class AssetMappet implements RowMapper<AssetData>
	    {
      public String schema(){
	  return "a.id as id,a.assetCode as assetCode,a.assetDescription as assetDescription,a.assetType as assetType ,a.make as make,a.model as model,a.purchaseValue as purchaseValue,a.saleValue as saleValue from m_asset a ";
  }
			@Override
			public AssetData mapRow(ResultSet rs, int rowNum)
					throws SQLException {
			            final Long id = rs.getLong("id");
			    	     final String assetCode=rs.getString("assetCode");
			             final String assetDescription=rs.getString("assetDescription");
			             final String AssetType=rs.getString("assetType");
			             final String make=rs.getString("make");
			             final String model=rs.getString("model");
	    		         final BigDecimal purchaseValue=rs.getBigDecimal("purchaseValue");
	    		         final BigDecimal saleValue=rs.getBigDecimal("saleValue");
				return new AssetData(id,assetCode,assetDescription,AssetType,make,model,purchaseValue,saleValue);
			}
	    	
	    }
	    
	@Override
	public AssetData reteriveDropdownOptions() {
		AssetMapper assetMapper=new AssetMapper();
		ModelMapper modelMapper=new ModelMapper();
		
        List<AssetType> assettpes=jdbcTemplate.query(assetMapper.schema1, assetMapper);
        List<Model> models=jdbcTemplate.query(modelMapper.schema2, modelMapper);
		return new AssetData(assettpes,models);
	}

	
	
	@Override
	public List<AssetData> retrieveAssetData() {
		AssetMappet assetMapper=new AssetMappet();
		List<AssetData> assetlist=jdbcTemplate.query("select "+assetMapper.schema(), assetMapper);
		return assetlist;
	}

	@Override
	public AssetData retrieveAssetData(Long assetId) {
		AssetMappet assetMapper=new AssetMappet();
		AssetData asset=jdbcTemplate.queryForObject("select "+ assetMapper.schema() +"where a.id=?",assetMapper,new Object [] {assetId});
		return asset;
	}

}
