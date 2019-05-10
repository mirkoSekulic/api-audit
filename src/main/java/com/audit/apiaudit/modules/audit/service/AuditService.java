package com.audit.apiaudit.modules.audit.service;

import com.audit.apiaudit.modules.audit.domain.Audit;
import com.audit.apiaudit.modules.audit.filter.AuditFilter;
import com.audit.apiaudit.modules.audit.repository.AuditRepository;
import com.audit.apiaudit.modules.audit.specification.AuditSpecification;
import com.audit.apiaudit.modules.shared.error.exception.entity.NewEntityCannotHaveIDException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


public interface AuditService {
    Page<Audit> getAllPageable(Pageable pageable);

    Page<Audit> getAllPageable(Pageable pageable, AuditFilter auditFilter);

    Audit create(Audit audit);

}
