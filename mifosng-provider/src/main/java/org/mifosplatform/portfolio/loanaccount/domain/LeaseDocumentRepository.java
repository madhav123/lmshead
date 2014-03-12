package org.mifosplatform.portfolio.loanaccount.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface LeaseDocumentRepository extends  JpaRepository<LeaseDocument, Long>,
JpaSpecificationExecutor<LeaseDocument>{
}

