package com.logitics.erp.payrolldetail.entity;

import com.logitics.erp.payroll.entity.Payroll;
import com.logitics.erp.payrollitem.entity.PayrollItemMaster;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PayrollDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long payrollDetailId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payroll_id")
	private Payroll payroll;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payroll_item_master_id")
    private PayrollItemMaster payrollItemMaster;

	private String itemNameSnapshot;
	private String itemTypeCodeSnapshot;
    private int amount;

}
