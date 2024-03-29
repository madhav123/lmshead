/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.portfolio.loanaccount.service;

import java.math.BigDecimal;
import java.util.Collection;

import javax.ws.rs.core.Response.ResponseBuilder;

import org.mifosplatform.infrastructure.core.service.Page;
import org.mifosplatform.organisation.staff.data.StaffData;
import org.mifosplatform.portfolio.calendar.data.CalendarData;
import org.mifosplatform.portfolio.group.service.SearchParameters;
import org.mifosplatform.portfolio.loanaccount.data.LoanAccountData;
import org.mifosplatform.portfolio.loanaccount.data.LoanTransactionData;
import org.mifosplatform.portfolio.loanaccount.data.RepaymentScheduleRelatedLoanData;
import org.mifosplatform.portfolio.loanaccount.domain.Loan;
import org.mifosplatform.portfolio.loanaccount.loanschedule.data.LoanScheduleData;
import org.mifosplatform.portfolio.loanaccount.loanschedule.data.OverdueLoanScheduleData;

public interface LoanReadPlatformService {

    LoanAccountData retrieveOne(Long loanId);

    LoanScheduleData retrieveRepaymentSchedule(Long loanId, RepaymentScheduleRelatedLoanData repaymentScheduleRelatedData);

    Collection<LoanTransactionData> retrieveLoanTransactions(Long loanId);

    LoanAccountData retrieveTemplateWithClientAndProductDetails(Long clientId, Long productId);

    LoanAccountData retrieveTemplateWithGroupAndProductDetails(Long groupId, Long productId);

    LoanTransactionData retrieveLoanTransactionTemplate(Long loanId);

    LoanTransactionData retrieveWaiveInterestDetails(Long loanId);

    LoanTransactionData retrieveLoanTransaction(Long loanId, Long transactionId);

    LoanTransactionData retrieveNewClosureDetails();

    LoanTransactionData retrieveDisbursalTemplate(Long loanId);

    LoanAccountData retrieveTemplateWithCompleteGroupAndProductDetails(Long groupId, Long productId);

    LoanAccountData retrieveLoanProductDetailsTemplate(Long productId, Long clientId, Long groupId);

    LoanAccountData retrieveClientDetailsTemplate(Long clientId);

    LoanAccountData retrieveGroupDetailsTemplate(Long groupId);

    LoanAccountData retrieveGroupAndMembersDetailsTemplate(Long groupId);

    Collection<CalendarData> retrieveCalendars(Long groupId);

    Page<LoanAccountData> retrieveAll(SearchParameters searchParameters);

    Collection<StaffData> retrieveAllowedLoanOfficers(Long selectedOfficeId, boolean staffInSelectedOfficeOnly);

    /*
     * musoni-specific at present - will find overdue scheduled installments that have a special 'overdue charge' associated with the loan product.
     *
     * The 'overdue-charge' is only ever applied once to an installment and as a result overdue installments with this charge already applied are not returned.
     */
    Collection<OverdueLoanScheduleData> retrieveAllLoansWithOverdueInstallmentsNotAlreadyPenalized(final Long penaltyWaitPeriod);

    Integer retriveLoanCounter(Long groupId, Integer loanType);

    Integer retriveLoanCounter(Long clientId);

	String getEmailForClient(Long clientId);

	String printLeaseDetails(Long loanId);

	BigDecimal retrivePrincipalForReSchedule(Loan loanId);

	BigDecimal retrivePrincipalForReSchedule(Long loanId);
}