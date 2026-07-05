package com.logitics.erp.employeeoauth.entity;

import com.logitics.erp.common.entity.BaseEntity;
import com.logitics.erp.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(
				name = "employee_oauth",
				uniqueConstraints = {
								@UniqueConstraint(
												name = "uk_employee_oauth_provider_provider_id",
												columnNames = {"provider", "provider_id"}
								)
				}
)
public class EmployeeOauth extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeOauthId;
    
    @JoinColumn(name = "employee_id")
    @ManyToOne
    private Employee employee;

    // 로그인 제공자 (KAKAO, GOOGLE)
    private String provider;

    // 제공자에서 사용하는 고유 사용자 ID
    private String providerId;

    // 제공자에서 사용하는 닉네임
    private String providerNickname;

    // 제공자에서 사용하는 이메일
    private String providerEmail;
    
}
