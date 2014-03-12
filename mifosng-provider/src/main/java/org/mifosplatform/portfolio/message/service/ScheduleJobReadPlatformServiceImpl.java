package org.mifosplatform.portfolio.message.service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.mifosplatform.infrastructure.core.domain.MifosPlatformTenant;
import org.mifosplatform.infrastructure.core.service.RoutingDataSource;
import org.mifosplatform.infrastructure.core.service.ThreadLocalContextUtil;
import org.mifosplatform.infrastructure.jobs.data.JobParameterData;
import org.mifosplatform.infrastructure.jobs.data.ScheduleJobData;
import org.mifosplatform.infrastructure.jobs.domain.JobParameters;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.mifosplatform.infrastructure.security.service.TenantDetailsService;
import org.mifosplatform.portfolio.message.data.BillingMessageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
@Service
public class ScheduleJobReadPlatformServiceImpl implements ScheduleJobReadPlatformService{
	 private final JdbcTemplate jdbcTemplate;
     private final PlatformSecurityContext context;
     private final TenantDetailsService tenantDetailsService;

    @Autowired
    public ScheduleJobReadPlatformServiceImpl(final PlatformSecurityContext context, final RoutingDataSource dataSource,final TenantDetailsService tenantDetailsService) {
        this.context = context;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.tenantDetailsService=tenantDetailsService;
    }
	@Override
	public 	JobParameterData retrieveJobParameters(String jobName) {
		try {
			final SheduleJobParametersMapper mapper = new SheduleJobParametersMapper();
			final String sql = "select " + mapper.sheduleLookupSchema();
			List<JobParameters> jobparameters= jdbcTemplate.query(sql, mapper, new Object[] { jobName });
			   return new JobParameterData(jobparameters); 
			} catch (EmptyResultDataAccessException e) {
			return null;
			}
	}
	private static final class SheduleJobParametersMapper implements RowMapper<JobParameters> {
		public String sheduleLookupSchema() {
		return "p.id as id,p.job_id as jobId,p.param_name as paramName,p.param_type as paramType,p.param_default_value as defaultValue," +
				"p.param_value as paramValue,p.is_dynamic as isDynamic,p.query_values as queryValue from job_parameters p, job j " +
				"where j.id=p.job_id  and j.name=?";
		}
		@Override
		public JobParameters mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {
		final Long id = rs.getLong("id");
		final Long jobId = rs.getLong("jobId");
		final String paramType = rs.getString("paramType");
		final String paramName = rs.getString("paramName");
		final String defaultValue = rs.getString("defaultValue");
		final String paramValue = rs.getString("paramValue");
		final String isDynamic = rs.getString("isDynamic");
		final String queryValue = rs.getString("queryValue");
		return new JobParameters(id,jobId,paramName,paramType,defaultValue,paramValue,isDynamic,queryValue);
		}
		}
	@Override
	public List<ScheduleJobData> retrieveSheduleJobDetails(String paramValue) {
		try {

			final MifosPlatformTenant tenant = this.tenantDetailsService.loadTenantById("default");
	        ThreadLocalContextUtil.setTenant(tenant);
	      
			final SheduleJobMapper1 mapper = new SheduleJobMapper1();

			final String sql = "select " + mapper.sheduleLookupSchema();

			return jdbcTemplate.query(sql, mapper, new Object[] { paramValue });
			} catch (EmptyResultDataAccessException e) {
			return null;
			}
	}
	private static final class SheduleJobMapper1 implements RowMapper<ScheduleJobData> {

		public String sheduleLookupSchema() {
		return "  b.id as id,b.report_name as batchName,b.report_sql as query from stretchy_report b where b.report_name=?";

		}

		@Override
		public ScheduleJobData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {
         
		final Long id = rs.getLong("id");
		final String batchName = rs.getString("batchName");
		final String query = rs.getString("query");
		
		
		return new ScheduleJobData(id, batchName,query);
		}
	 }
	@Override
	public Long getMessageId(String messageTemplateName) {
		
		try {  
	      
			final MessageIdMapper mapper = new MessageIdMapper();
			final String sql = "select " + mapper.getmessageId();
			return jdbcTemplate.queryForObject(sql, mapper, new Object[]{ messageTemplateName });
			} catch (EmptyResultDataAccessException e) {
			return null;
			}
			}
			private static final class MessageIdMapper implements RowMapper<Long> {

			public String getmessageId() {
			return "mt.id as id from b_message_template mt where mt.template_description=?";
			}
			@Override
			public Long mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {
			final Long id = rs.getLong("id");
			return id ;
			}
		}
			
			@Override
			public String retrieveMessageData(Long id) {
				// TODO Auto-generated method stub
				try {
		
					final MessageMapper mapperdata = new MessageMapper();
					String query = mapperdata.retrieveId(id);
					return jdbcTemplate.queryForObject(query, mapperdata,
							new Object[] {});
				} catch (EmptyResultDataAccessException e) {
					return null;
				}
			}
			

			private static final class MessageMapper implements RowMapper<String> {
				private Long id;

				public String retrieveId(Long val) {
					this.id = val;
					return "SELECT sms_conf(" + val + ")";
				}

				@Override
				public String mapRow(final ResultSet rs,
						@SuppressWarnings("unused") final int rowNum)
						throws SQLException {
					String prepare = "sms_conf(" + id + ")";
					String message = rs.getString(prepare);
					return message;

				}
			}
			
			//message data related mappers
			@Override
			public BillingMessageData retrieveMessageTemplate(Long command) {
				// context.authenticatedUser();
				BillingMessageTemplateMapper mapper = new BillingMessageTemplateMapper();

				String sql = "select " + mapper.schema() + command;

				return this.jdbcTemplate.queryForObject(sql, mapper, new Object[] {});
			}

			private static final class BillingMessageTemplateMapper implements
					RowMapper<BillingMessageData> {

				public String schema() {

					return "mt.id ,mt.template_description, mt.subject ,mt.header,mt.body , mt.footer,mt.message_type as messageType from  b_message_template mt where mt.id=";
				}

				@Override
				public BillingMessageData mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					int id1 = rs.getInt("id");
					String templateDescription = rs.getString("template_description");
					String subject = rs.getString("subject");
					String header = rs.getString("header");
					String body = rs.getString("body");
					String footer = rs.getString("footer");
					String messageTypeInDB = rs.getString("messageType");
					char c = messageTypeInDB.charAt(0);
					Long id = new Long(id1);

					return new BillingMessageData(id, templateDescription, subject,
							header, body, footer, null, c);
				}
			}
			@Override
			public List<BillingMessageData> retrieveMessageParams(Long entityId) {
				// TODO Auto-generated method stub
				// context.authenticatedUser();
				BillingMessageParamMapper mapper = new BillingMessageParamMapper();
				String sql = "select " + mapper.schema() + entityId;

				return this.jdbcTemplate.query(sql, mapper, new Object[] {});
			}

			private static final class BillingMessageParamMapper implements
					RowMapper<BillingMessageData> {

				public String schema() {

					return "mp.id as msgTemplateId,mp.parameter_name as parameterName,mp.sequence_no as sequenceNo "
							+ "from b_message_params mp where mp.msgtemplate_id=";
				}

				@Override
				public BillingMessageData mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					Long messageTemplateId = rs.getLong("msgTemplateId");
					String parameterName = rs.getString("parameterName");
					return new BillingMessageData(messageTemplateId, parameterName);
				}
			}


}
