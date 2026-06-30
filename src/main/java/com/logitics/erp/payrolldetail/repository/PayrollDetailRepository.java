package com.logitics.erp.payrolldetail.repository;

import com.logitics.erp.payrolldetail.entity.PayrollDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayrollDetailRepository extends JpaRepository<PayrollDetail, Long> {
    PayrollDetail findByAmount(int amount);
}
