package org.mifosplatform.infrastructure.jobs.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.mifosplatform.infrastructure.core.service.Page;
import org.mifosplatform.infrastructure.core.service.PaginationHelper;
import org.mifosplatform.infrastructure.core.service.RoutingDataSource;
import org.mifosplatform.infrastructure.jobs.data.JobDetailData;
import org.mifosplatform.infrastructure.jobs.data.JobDetailHistoryData;
import org.mifosplatform.infrastructure.jobs.data.JobParameterData;
import org.mifosplatform.infrastructure.jobs.data.MessageTemplateData;
import org.mifosplatform.infrastructure.jobs.data.ScheduleJobData;
import org.mifosplatform.infrastructure.jobs.domain.JobParameters;
import org.mifosplatform.infrastructure.jobs.exception.JobNotFoundException;
import org.mifosplatform.infrastructure.jobs.exception.OperationNotAllowedException;
import org.mifosplatform.portfolio.group.service.SearchParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class SchedulerJobRunnerReadServiceImpl implements SchedulerJobRunnerReadService {

    private final JdbcTemplate jdbcTemplate;

    private final PaginationHelper<JobDetailHistoryData> paginationHelper = new PaginationHelper<JobDetailHistoryData>();

    @Autowired
    public SchedulerJobRunnerReadServiceImpl(final RoutingDataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<JobDetailData> findAllJobDeatils() {
        final JobDetailMapper detailMapper = new JobDetailMapper();
        final String sql = detailMapper.schema();
        final List<JobDetailData> JobDeatils = this.jdbcTemplate.query(sql, detailMapper, new Object[] {});
        return JobDeatils;

    }

    @Override
    public JobDetailData retrieveOne(final Long jobId) {
        try {
            final JobDetailMapper detailMapper = new JobDetailMapper();
            final String sql = detailMapper.schema() + " where job.id=?";
            return this.jdbcTemplate.queryForObject(sql, detailMapper, new Object[] { jobId });
        } catch (final EmptyResultDataAccessException e) {
            throw new JobNotFoundException(String.valueOf(jobId));
        }
    }
	@Override
	public JobParameterData getJobParameters(String jobName) {
		try
		{
		   List<JobParameters> jobParameters=this.retrieveJobParameters(jobName);//scheduledJobDetail.getDetails();
		   return new JobParameterData(jobParameters); 
		   
		}catch(EmptyResultDataAccessException exception){
			return null;
		}
	}
	
	private List<JobParameters> retrieveJobParameters(String jobName) {
		try {
	
			final SheduleJobParametersMapper mapper = new SheduleJobParametersMapper();
			final String sql = "select " + mapper.sheduleLookupSchema();
			return this.jdbcTemplate.query(sql, mapper, new Object[] { jobName });
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
    public Page<JobDetailHistoryData> retrieveJobHistory(final Long jobId, final SearchParameters searchParameters) {
        if (!isJobExist(jobId)) { throw new JobNotFoundException(String.valueOf(jobId)); }
        final JobHistoryMapper jobHistoryMapper = new JobHistoryMapper();
        final StringBuilder sqlBuilder = new StringBuilder(200);
        sqlBuilder.append("select SQL_CALC_FOUND_ROWS ");
        sqlBuilder.append(jobHistoryMapper.schema());
        sqlBuilder.append(" where job.id=?");
        if (searchParameters.isOrderByRequested()) {
            sqlBuilder.append(" order by ").append(searchParameters.getOrderBy());

            if (searchParameters.isSortOrderProvided()) {
                sqlBuilder.append(' ').append(searchParameters.getSortOrder());
            }
        }

        if (searchParameters.isLimited()) {
            sqlBuilder.append(" limit ").append(searchParameters.getLimit());
            if (searchParameters.isOffset()) {
                sqlBuilder.append(" offset ").append(searchParameters.getOffset());
            }
        }

        final String sqlCountRows = "SELECT FOUND_ROWS()";
        return this.paginationHelper.fetchPage(this.jdbcTemplate, sqlCountRows, sqlBuilder.toString(), new Object[] { jobId },
                jobHistoryMapper);
    }

    @Override
    public boolean isUpdatesAllowed() {
        final String sql = "select job.display_name from job job where job.currently_running=true and job.updates_allowed=false";
        final List<String> names = this.jdbcTemplate.queryForList(sql, String.class);
        if (names != null && names.size() > 0) {
            final String listVals = names.toString();
            final String jobNames = listVals.substring(listVals.indexOf("[") + 1, listVals.indexOf("]"));
            throw new OperationNotAllowedException(jobNames);
        }
        return true;
    }

    private boolean isJobExist(final Long jobId) {
        boolean isJobPresent = false;
        final String sql = "select count(*) from job job where job.id=" + jobId;
        final int count = this.jdbcTemplate.queryForInt(sql);
        if (count == 1) {
            isJobPresent = true;
        }
        return isJobPresent;
    }

    private static final class JobDetailMapper implements RowMapper<JobDetailData> {

        private final StringBuilder sqlBuilder = new StringBuilder("select")
                .append(" job.id,job.display_name as displayName,job.name as name,job.next_run_time as nextRunTime,job.initializing_errorlog as initializingError,job.cron_expression as cronExpression,job.is_active as active,job.currently_running as currentlyRunning,")
                .append(" runHistory.version,runHistory.start_time as lastRunStartTime,runHistory.end_time as lastRunEndTime,runHistory.`status`,runHistory.error_message as jobRunErrorMessage,runHistory.trigger_type as triggerType,runHistory.error_log as jobRunErrorLog ")
                .append(" from job job  left join job_run_history runHistory ON job.id=runHistory.job_id and job.previous_run_start_time=runHistory.start_time ");

        public String schema() {
            return this.sqlBuilder.toString();
        }

        @Override
        public JobDetailData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {
            final Long id = rs.getLong("id");
            final String displayName = rs.getString("displayName");
            final Date nextRunTime = rs.getTimestamp("nextRunTime");
            final String initializingError = rs.getString("initializingError");
            final String cronExpression = rs.getString("cronExpression");
            final boolean active = rs.getBoolean("active");
            final boolean currentlyRunning = rs.getBoolean("currentlyRunning");

            final Long version = rs.getLong("version");
            final Date jobRunStartTime = rs.getTimestamp("lastRunStartTime");
            final Date jobRunEndTime = rs.getTimestamp("lastRunEndTime");
            final String status = rs.getString("status");
            final String jobRunErrorMessage = rs.getString("jobRunErrorMessage");
            final String triggerType = rs.getString("triggerType");
            final String jobRunErrorLog = rs.getString("jobRunErrorLog");
            final String name=rs.getString("name");

            JobDetailHistoryData lastRunHistory = null;
            if (version > 0) {
                lastRunHistory = new JobDetailHistoryData(version, jobRunStartTime, jobRunEndTime, status, jobRunErrorMessage, triggerType,
                        jobRunErrorLog);
            }
            final JobDetailData jobDetail = new JobDetailData(id, displayName, nextRunTime, initializingError, cronExpression, active,
                    currentlyRunning, lastRunHistory,name);
            return jobDetail;
        }

    }

    private static final class JobHistoryMapper implements RowMapper<JobDetailHistoryData> {

        private final StringBuilder sqlBuilder = new StringBuilder(200)
                .append(" runHistory.version,runHistory.start_time as runStartTime,runHistory.end_time as runEndTime,runHistory.`status`,runHistory.error_message as jobRunErrorMessage,runHistory.trigger_type as triggerType,runHistory.error_log as jobRunErrorLog ")
                .append(" from job job join job_run_history runHistory ON job.id=runHistory.job_id");

        public String schema() {
            return this.sqlBuilder.toString();
        }

        @Override
        public JobDetailHistoryData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {
            final Long version = rs.getLong("version");
            final Date jobRunStartTime = rs.getTimestamp("runStartTime");
            final Date jobRunEndTime = rs.getTimestamp("runEndTime");
            final String status = rs.getString("status");
            final String jobRunErrorMessage = rs.getString("jobRunErrorMessage");
            final String triggerType = rs.getString("triggerType");
            final String jobRunErrorLog = rs.getString("jobRunErrorLog");
            final JobDetailHistoryData jobDetailHistory = new JobDetailHistoryData(version, jobRunStartTime, jobRunEndTime, status,
                    jobRunErrorMessage, triggerType, jobRunErrorLog);
            return jobDetailHistory;
        }
        //reterive template data for job template
     
 

    }
    @Override
	public List<ScheduleJobData> getJobQeryData() {
		try {
			 
					final SheduleJobMapper mapper = new SheduleJobMapper();
					final String sql = "select " + mapper.sheduleLookupSchema()+"where sr.report_category='Scheduling Job'";
					return jdbcTemplate.query(sql, mapper, new Object[] {  });
					} catch (EmptyResultDataAccessException e) {
					return null;
					}

					
			}

	private static final class SheduleJobMapper implements
			RowMapper<ScheduleJobData> {

		public String sheduleLookupSchema() {
			return " sr.id as id,sr.report_name as reportName,sr.report_sql as query  from stretchy_report sr ";
		}

		@Override
		public ScheduleJobData mapRow(final ResultSet rs,
				@SuppressWarnings("unused") final int rowNum)
				throws SQLException {
			final Long id = rs.getLong("id");
			final String batchName = rs.getString("reportName");
			final String query = rs.getString("query");

			return new ScheduleJobData(id, batchName,query);
			}
			}
	@Override
	public List<MessageTemplateData> getMessageTemplate() {
		MessageTemplateMapper mapper =new MessageTemplateMapper();
		return jdbcTemplate.query(mapper.schema(), mapper);
	}
	
	private static final class MessageTemplateMapper implements RowMapper<MessageTemplateData>{

		public String schema(){
		return "SELECT mt.id as id,mt.template_description as templatedescription  FROM b_message_template mt";
		}
		@Override
		public MessageTemplateData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			Long id=rs.getLong("id");
			String templateDescription=rs.getNString("templateDescription");
			return new MessageTemplateData(id, templateDescription);
		}
		
	}

}
