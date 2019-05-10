package com.audit.apiaudit.modules.example.service.implementation;

import com.audit.apiaudit.modules.example.domain.Example;
import com.audit.apiaudit.modules.example.error.exception.ExampleNotFoundException;
import com.audit.apiaudit.modules.example.repository.ExampleRepository;
import com.audit.apiaudit.modules.example.service.ExampleService;
import com.audit.apiaudit.modules.shared.error.exception.entity.EntityMustHaveIDException;
import com.audit.apiaudit.modules.shared.error.exception.entity.NewEntityCannotHaveIDException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ExapmleServiceImpl implements ExampleService {

    private final ExampleRepository exampleRepository;

    public ExapmleServiceImpl(ExampleRepository exampleRepository) {
        this.exampleRepository = exampleRepository;
    }

    @Override
    public Page<Example> findAll(Pageable pageable) {
        return exampleRepository.findAll(pageable);
    }

    @Override
    public Example findById(Long id) {
        return exampleRepository.findById(id).orElseThrow(() ->
                new ExampleNotFoundException(id));
    }

    @Override
    public Example create(Example example) {
        if(example.getId()!= null) {
            throw new NewEntityCannotHaveIDException("Example");
        }
        Example forInsert = new Example();
        BeanUtils.copyProperties(example, forInsert);
        forInsert.setCreatedDate(Instant.now());

        return exampleRepository.save(forInsert);
    }

    @Override
    public Example update(Example example) {
        if(example.getId() == null) {
            throw new EntityMustHaveIDException("Example");
        }

        Example fromDb = findById(example.getId());
        Example forUpdate = new Example();
        BeanUtils.copyProperties(example, forUpdate);
        forUpdate.setCreatedDate(fromDb.getCreatedDate());

        return exampleRepository.save(forUpdate);
    }

    @Override
    public void delete(Long id) {
        Example example = findById(id);
        exampleRepository.delete(example);
    }
}
