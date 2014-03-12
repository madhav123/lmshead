package org.mifosplatform.portfolio.insurance.service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.mifosplatform.infrastructure.core.service.RoutingDataSource;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.mifosplatform.portfolio.asset.data.AssetData;
import org.mifosplatform.portfolio.asset.data.Model;


import org.mifosplatform.portfolio.insurance.data.InsuranceData;
import org.mifosplatform.portfolio.insurance.data.Insurar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
@Service
public class InsuranceReadPlatformServiceImpl implements InsuranceReadPlatformService{
	 private final JdbcTemplate jdbcTemplate;
     private final PlatformSecurityContext context;

    @Autowired
    public InsuranceReadPlatformServiceImpl(final PlatformSecurityContext context, final RoutingDataSource dataSource) {
        this.context = context;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	@Override
	public InsuranceData reteriveDropdownOptions() {
		AssetMappet assetMapper=new AssetMappet();
		ModelMapper modelMapper=new ModelMapper();
		List<AssetData> assetdropdowns=jdbcTemplate.query("select "+assetMapper.schema(), assetMapper);
		InsurarMapper insurarMapper=new InsurarMapper();
		//List<AssetData> assetdropdowns=jdbcTemplate.query("select"+ assetMappet.schema(), assetMappet);
		List<Insurar> Insurardropdowns=jdbcTemplate.query(InsurarMapper.schema, insurarMapper);
	    List<Model> models=jdbcTemplate.query(modelMapper.schema2, modelMapper);
		return  new InsuranceData(assetdropdowns,Insurardropdowns,models);
	}	

    private static final class InsurarMapper implements RowMapper<Insurar>{

    	static String schema="select mv.code_value, mv.id from m_code m,m_code_value mv where m.id=mv.code_id and m.id=18";

		@Override
		public Insurar mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			String values=rs.getString("code_value");
			Long id=rs.getLong("id");

			return new Insurar(values,id);
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

	  
	  private static final class InsuranceMapper implements RowMapper<InsuranceData>
	  {

		  public String schema(){
			  return "i.id as id,i.loan_id as loan_id,i.insurer as insurer,i.insuredAsset as insuredAsset,i.valueOfAsset as valueOfAsset," +
			  		"i.insuredTerm as insuredTerm,i.premiumAmount as premiumAmount,i.insuredDate as insuredDate,i.expiryDate as expiryDate,i.vechicalRegNo as vechicalRegNo,i.vehicalModel as vehicalModel from m_insurance i ";
		  }
			  @Override
		public InsuranceData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
				    final Long id = rs.getLong("id");
				    final Long loanAccountNo = rs.getLong("loan_id");
				    final String insurer=rs.getString("insurer");
				   final String insuredAsset=rs.getString("insuredAsset");
				    final BigDecimal valueOfAsset=rs.getBigDecimal("valueOfAsset");
				    final int insuredTerm=rs.getInt("insuredTerm");
				    final BigDecimal premiumAmount=rs.getBigDecimal("premiumAmount");
			        final Date insuredDate =rs.getDate("insuredDate");
			        final Date expiryDate =rs.getDate("expiryDate");
			        final String  vechicalRegNo=rs.getString("vechicalRegNo");
			        final String vehicalModel=rs.getString("vehicalModel"); 
			return new InsuranceData(id,loanAccountNo,insurer,insuredAsset,insuredTerm,valueOfAsset,premiumAmount,insuredDate,expiryDate,vechicalRegNo,vehicalModel);
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
	@Override
	public List<InsuranceData> retrieveInsuracetData() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<InsuranceData> retrieveInsuracetData(Long loanId) {
	
		InsuranceMapper insuranceMapper=new InsuranceMapper();
		
		return jdbcTemplate.query("select "+ insuranceMapper.schema() +"where i.loan_id=?",insuranceMapper,new Object [] {loanId});
	}
	@Override
	public InsuranceData retrieveInsuracetDataSingle(Long loanId) {
	
		InsuranceMapper insuranceMapper=new InsuranceMapper();
		
		return jdbcTemplate.queryForObject("select "+ insuranceMapper.schema() +"where i.id=?",insuranceMapper,new Object [] {loanId});
	}
}
