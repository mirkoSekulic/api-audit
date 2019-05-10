package com.audit.apiaudit.modules.users.service;

import com.audit.apiaudit.modules.users.domain.User;
import com.audit.apiaudit.modules.users.filter.UserFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<User> findAll(UserFilter userFilter, Pageable pageable);
}
