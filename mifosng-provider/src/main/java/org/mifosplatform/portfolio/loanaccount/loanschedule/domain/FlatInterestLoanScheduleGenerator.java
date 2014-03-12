/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.portfolio.loanaccount.loanschedule.domain;

import java.math.BigDecimal;
import java.math.MathContext;

import org.mifosplatform.organisation.monetary.domain.Money;

import sun.nio.cs.ext.Big5;

public class FlatInterestLoanScheduleGenerator extends AbstractLoanScheduleGenerator {

    @Override
    public PrincipalInterest calculatePrincipalInterestComponentsForPeriod(final PaymentPeriodsInOneYearCalculator calculator,
            final double interestCalculationGraceOnRepaymentPeriodFraction, final Money totalCumulativePrincipal,
            final Money totalCumulativeInterest, final Money totalInterestDueForLoan, final Money cumulatingInterestPaymentDueToGrace,
            final int daysInPeriodApplicableForInterest, final Money outstandingBalance, final LoanApplicationTerms loanApplicationTerms,
            final int periodNumber, final MathContext mc) {

        Money principalForThisInstallment = loanApplicationTerms.calculateTotalPrincipalForPeriod(calculator,
                daysInPeriodApplicableForInterest, outstandingBalance, periodNumber, mc);

        final PrincipalInterest result = loanApplicationTerms.calculateTotalInterestForPeriod(calculator,
                interestCalculationGraceOnRepaymentPeriodFraction, periodNumber, mc, cumulatingInterestPaymentDueToGrace,
                daysInPeriodApplicableForInterest, outstandingBalance);
        Money interestForThisInstallment = result.interest();
        
   
        

        // update cumulative fields for principal & interest
        final Money interestBroughtForwardDueToGrace = result.interestPaymentDueToGrace();
        final Money totalCumulativePrincipalToDate = totalCumulativePrincipal.plus(principalForThisInstallment);
        final Money totalCumulativeInterestToDate = totalCumulativeInterest.plus(interestForThisInstallment);

        // adjust if needed
        principalForThisInstallment = loanApplicationTerms.adjustPrincipalIfLastRepaymentPeriod(principalForThisInstallment,
                totalCumulativePrincipalToDate, periodNumber);

        
        interestForThisInstallment = loanApplicationTerms.adjustInterestIfLastRepaymentPeriod(interestForThisInstallment,
                totalCumulativeInterestToDate, totalInterestDueForLoan, periodNumber);
        
      //madhav -need to play logic for change the interest
/*        BigDecimal residualAmt=loanApplicationTerms.getResidualAmount();
 	    Money interestAmtMoney=Money.of(null, residualAmt);
 	    
        residualAmt=residualAmt.divide(new BigDecimal(loanApplicationTerms.getNumberOfRepayments()));
        BigDecimal interest=loanApplicationTerms.calculateInterestMonthleyCompond(loanApplicationTerms.getInterestRatePerPeriod().divide(new BigDecimal(12)), new Long(365), balanceForFindCompondInterest,balance.getAmountDefaultedToNullIfZero());	
      
        */

        return new PrincipalInterest(principalForThisInstallment, interestForThisInstallment, interestBroughtForwardDueToGrace);
    }
}