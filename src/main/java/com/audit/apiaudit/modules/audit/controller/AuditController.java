package com.audit.apiaudit.modules.audit.controller;

import com.audit.apiaudit.modules.audit.domain.Audit;
import com.audit.apiaudit.modules.audit.filter.AuditFilter;
import com.audit.apiaudit.modules.audit.service.AuditService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api(value = "audits", tags = {"audits"})
@RequestMapping("audits")
public class AuditController {
    private final AuditService auditService;


    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @ApiOperation(value = "Get page of audit.", nickname = "getAllPageable")
    public ResponseEntity<Page<Audit>> getAllPageable(Pageable pageable, AuditFilter auditFilter) {

        Page<Audit> auditPage = auditService.getAllPageable(pageable, auditFilter);

        return new ResponseEntity<>(auditPage, HttpStatus.OK);
    }

}
