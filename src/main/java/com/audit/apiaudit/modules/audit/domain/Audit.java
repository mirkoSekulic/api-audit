package com.audit.apiaudit.modules.audit.domain;


import com.audit.apiaudit.modules.shared.converter.JsonToListConverer;
import com.audit.apiaudit.modules.shared.converter.JsonToMapConverer;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * A Audit.
 */
@Entity
@Table(name = "audit")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@SuppressWarnings("squid:S1948")
public class Audit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login")
    private String login;

    @NotNull
    @Column(name = "datetime", nullable = false)
    private Instant datetime;

    @Column(name = "ip")
    private String ip;

    @Column(name = "arguments", columnDefinition = "json")
    @Convert(converter = JsonToMapConverer.class)
    private Map<String, Object> arguments;

    @Column(name = "http_status")
    private Integer httpStatus;

    @Column(name = "info")
    private String info;

    @Column(name = "method")
    private String method;

    @Column(name = "authorities", columnDefinition = "json")
    @Convert(converter = JsonToListConverer.class)
    private List<String> authorities;

    @Column(name = "path")
    private String path;

    @Column(name = "request_type")
    private String requestType;
}
