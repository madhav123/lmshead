package org.mifosplatform.portfolio.equity.service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.mifosplatform.infrastructure.core.service.RoutingDataSource;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.mifosplatform.portfolio.equity.data.EquityData;
import org.mifosplatform.portfolio.equity.data.EquityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
@Service
public class EquityReadPlatformServiceImpl implements EquityReadPlatformService{
	 private final JdbcTemplate jdbcTemplate;
    private final PlatformSecurityContext context;
   @Autowired
   public EquityReadPlatformServiceImpl(final PlatformSecurityContext context, final RoutingDataSource dataSource) {
       this.context = context;
       this.jdbcTemplate = new JdbcTemplate(dataSource);
   }
   private static final class EquityMapper implements RowMapper<EquityType>{

   	static String schema1="select mv.code_value,mv.id from m_code m,m_code_value mv where m.id=mv.code_id and m.id=19";
   			@Override
		public EquityType mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			String values=rs.getString("code_value");
			Long id=rs.getLong("id");
			return new EquityType(values,id);
		}	
   }
	@Override
	public EquityData reteriveDropdownOptions() {
		EquityMapper mapper=new EquityMapper();
		List<EquityType> equityTypes=jdbcTemplate.query(EquityMapper.schema1, mapper);
		return new EquityData(equityTypes);
	}
	@Override
	public List<EquityData> retrieveEquityDataAll(Long loanId) {
	String sql="SELECT e.id as id,e.equityType as equityType,e.model as model,e.marketPrice as marketPrice,e.actualPrice as actualPrice from m_equity e where e.loanId="+loanId;
	EqityMapper mapper=new EqityMapper();
    return jdbcTemplate.query(sql, mapper);
	

	}		
		final class EqityMapper implements RowMapper<EquityData>{

			@Override
			public EquityData mapRow(ResultSet rs, int assetId)
					throws SQLException {
				 Long id=rs.getLong("id");
				 String eqityType=rs.getString("equityType");
				 String model=rs.getString("model");
				 BigDecimal marketPrice=rs.getBigDecimal("marketPrice");
	             BigDecimal price=rs.getBigDecimal("actualPrice");
				return new EquityData(id,eqityType,model,marketPrice,price);
			}
			
		}
	

	@Override
	public EquityData retrieveEquityData(Long assetId) {
		String sql="SELECT e.id as id,e.equityType as equityType,e.model as model,e.marketPrice as marketPrice,e.actualPrice as actualPrice from m_equity e where e.id=?";
		EqityMapper mapper=new EqityMapper();
	    return jdbcTemplate.queryForObject(sql,mapper,new Object[] { assetId });
	}

}
