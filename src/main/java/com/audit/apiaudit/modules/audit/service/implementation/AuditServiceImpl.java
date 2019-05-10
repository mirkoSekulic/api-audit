package com.audit.apiaudit.modules.audit.service.implementation;

import com.audit.apiaudit.modules.audit.domain.Audit;
import com.audit.apiaudit.modules.audit.filter.AuditFilter;
import com.audit.apiaudit.modules.audit.repository.AuditRepository;
import com.audit.apiaudit.modules.audit.service.AuditService;
import com.audit.apiaudit.modules.audit.specification.AuditSpecification;
import com.audit.apiaudit.modules.shared.error.exception.entity.NewEntityCannotHaveIDException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@Transactional(readOnly = true)
public class AuditServiceImpl implements AuditService {
    private final AuditRepository auditRepository;
    private static final String ENTITY_NAME = "Audit";

    public AuditServiceImpl(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Override
    public Page<Audit> getAllPageable(Pageable pageable) {
        return getAllPageable(pageable, new AuditFilter());
    }

    @Override
    public Page<Audit> getAllPageable(Pageable pageable, AuditFilter auditFilter) {
        return auditRepository.findAll(AuditSpecification.of(auditFilter), pageable);
    }

    /***
     * Create audit
     * @param audit - audit for create
     * @return - Created audit
     */
    @Override
    @Transactional
    public Audit create(Audit audit) {
        if (audit.getId() != null) {
            throw new NewEntityCannotHaveIDException(ENTITY_NAME);
        }
        Audit forInsert = new Audit();
        BeanUtils.copyProperties(audit, forInsert);

        return auditRepository.save(forInsert);
    }

}
