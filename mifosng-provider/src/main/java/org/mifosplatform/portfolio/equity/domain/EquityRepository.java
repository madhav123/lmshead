package org.mifosplatform.portfolio.equity.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EquityRepository extends JpaRepository<Equity, Long>, JpaSpecificationExecutor<Equity>{

}
