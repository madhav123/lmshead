package org.mifosplatform.portfolio.insurance.domain;

import org.mifosplatform.portfolio.asset.domain.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InsuranceRepository extends JpaRepository<Insurance, Long>, JpaSpecificationExecutor<Insurance> {

}

