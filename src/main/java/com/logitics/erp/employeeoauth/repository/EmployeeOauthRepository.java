package com.logitics.erp.employeeoauth.repository;

import com.logitics.erp.employeeoauth.entity.EmployeeOauth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeOauthRepository extends JpaRepository<EmployeeOauth, Long> {
	Optional<EmployeeOauth> findByProviderId(String providerId);

	void deleteAllByProviderId(String providerId);
}
