package com.audit.apiaudit.modules.audit.specification;

import com.audit.apiaudit.modules.audit.domain.Audit;
import com.audit.apiaudit.modules.audit.domain.Audit_;
import com.audit.apiaudit.modules.audit.filter.AuditFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class AuditSpecification implements Specification<Audit> {
    private static final long serialVersionUID = -1243222771522885693L;

    private final AuditFilter auditFilter;

    private AuditSpecification(AuditFilter auditFilter) {
        this.auditFilter = auditFilter;
    }

    public static AuditSpecification of(AuditFilter auditFilter) {
        return new AuditSpecification(auditFilter);
    }

    @Override
    public Predicate toPredicate(Root<Audit> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        String likeFormat = "%%%s%%";

        List<Predicate> predicates = new ArrayList<>();

        if (auditFilter.getLogin() != null && !auditFilter.getLogin().isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get(Audit_.login), String.format(likeFormat, auditFilter.getLogin())));
        }

        if (auditFilter.getMethod() != null && !auditFilter.getMethod().isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get(Audit_.method), String.format(likeFormat, auditFilter.getMethod())));
        }

        if (auditFilter.getIp() != null && !auditFilter.getIp().isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get(Audit_.ip), auditFilter.getIp()));
        }

        if (auditFilter.getHttpStatus() != null) {
            predicates.add(criteriaBuilder.equal(root.get(Audit_.httpStatus), auditFilter.getHttpStatus()));
        }

        if (auditFilter.getRequestType() != null && !auditFilter.getRequestType().isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get(Audit_.requestType), auditFilter.getRequestType()));
        }

        if (auditFilter.getPath() != null && !auditFilter.getPath().isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get(Audit_.path), auditFilter.getPath()));
        }

        if (auditFilter.getFromDate() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Audit_.datetime), auditFilter.getFromDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        }

        if (auditFilter.getToDate() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Audit_.datetime), auditFilter.getToDate().atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant()));
        }

        Predicate[] predicateArray = predicates.toArray(new Predicate[0]);

        return criteriaBuilder.and(predicateArray);
    }
}
