package com.logitics.erp.employeeoauth.controller;

import com.logitics.erp.employee.service.EmployeeService;
import com.logitics.erp.employeeoauth.service.EmployeeOauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth")
public class EmployeeOauthController {

    private final EmployeeOauthService employeeOauthService;

}
