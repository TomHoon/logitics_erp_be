package com.logitics.erp.payrolldetail.repository;

import com.logitics.erp.payrolldetail.entity.PayrollDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PayrollDetailRepository extends JpaRepository<PayrollDetail, Long> {
    PayrollDetail findByAmount(int amount);

    Optional<PayrollDetail> findByPayroll_PayrollIdAndItemNameSnapshot(
            Long payrollId,
            String itemNameSnapshot
    );
}
