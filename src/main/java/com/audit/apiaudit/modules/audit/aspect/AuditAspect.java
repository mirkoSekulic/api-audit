package com.audit.apiaudit.modules.audit.aspect;

import com.audit.apiaudit.modules.audit.annotation.ApiAudited;
import com.audit.apiaudit.modules.audit.domain.Audit;
import com.audit.apiaudit.modules.audit.service.AuditService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Aspect
@Slf4j
@SuppressWarnings("squid:CommentedOutCodeLine")
public class AuditAspect {

    private HttpServletRequest request;
    private AuditService auditService;

    public AuditAspect(HttpServletRequest httpServletRequest, AuditService auditService) {
        this.request = httpServletRequest;
        this.auditService = auditService;
    }

    /**
     * Log audit data
     *
     * @param joinPoint  Join point
     * @param apiAudited ApiAudited interface
     * @param response   ResponseEntity response
     */
    @AfterReturning(pointcut = "@annotation(apiAudited)", returning = "response")
    public void logAudit(JoinPoint joinPoint, ApiAudited apiAudited, ResponseEntity response) {
        try {
            // Method
            String method = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName();

            // Timestamp
            Instant timespamp = Instant.now();

            // Authentication context
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Login
            String login = authentication != null ? authentication.getName() : null;

            // Authorities
            List<String> authoritiesList = authentication != null ?
                    authentication.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList())
                    : null;

            // IP address
            String ip = request.getRemoteAddr();

            // Arguments
            CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
            String[] argumentNames = codeSignature.getParameterNames();
            Object[] argumentValues = joinPoint.getArgs();

            // Skip non-audible arguments
            List<String> skipArguments = Arrays.asList("request", "response", "pageable");

            Map<String, Object> argumentsMap = IntStream.range(0, argumentNames.length)
                    .filter(i -> !skipArguments.contains(argumentNames[i]))
                    .filter(i -> argumentValues[i] != null)
                    .filter(i -> !(argumentValues[i] instanceof MultipartFile))
                    .boxed()
                    .collect(Collectors.toMap(i -> argumentNames[i], i -> argumentValues[i]));

            // Http status
            Integer httpStatus = response.getStatusCodeValue();

            // Info
            String info = null;

            // Path
            String path = request.getContextPath() + request.getServletPath();

            // Request type
            String requestType = request.getMethod();

            // Create audit log
            Audit audit = new Audit();
            audit.setMethod(method);
            audit.setArguments(argumentsMap);
            audit.setLogin(login);
            audit.setAuthorities(authoritiesList);
            audit.setHttpStatus(httpStatus);
            audit.setIp(ip);
            audit.setInfo(info);
            audit.setPath(path);
            audit.setRequestType(requestType);
            audit.setDatetime(timespamp);

            // Create audit log
            auditService.create(audit);
        } catch (Exception ex) {
            log.error("Exit: ApiAudited exception: {}", ex);
        }
    }

}
