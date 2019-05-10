package com.audit.apiaudit.modules.example.service;

import com.audit.apiaudit.modules.example.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExampleService {

    Page<Example> findAll(Pageable pageable);

    Example create(Example example);

    Example update(Example example);

    void delete(Long id);

    Example findById(Long id);
}
