package com.audit.apiaudit.modules.example.controller;

import com.audit.apiaudit.modules.audit.annotation.ApiAudited;
import com.audit.apiaudit.modules.example.domain.Example;
import com.audit.apiaudit.modules.example.dto.ExampleDTO;
import com.audit.apiaudit.modules.example.mapper.ExampleMapper;
import com.audit.apiaudit.modules.example.service.ExampleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(value = "examples", tags = {"examples"})
@RequestMapping("examples")
public class ExampleController {
    private final ExampleService exampleService;
    private final ExampleMapper exampleMapper;

    public ExampleController(ExampleService exampleService, ExampleMapper exampleMapper) {
        this.exampleService = exampleService;
        this.exampleMapper = exampleMapper;
    }

    @GetMapping
    @ApiAudited
    @ApiOperation(value = "Get a page of examples..", nickname = "findAll")
    public ResponseEntity<Page<ExampleDTO>> list(Pageable pageable) {
        Page<ExampleDTO> exampleDTOPage = exampleService.findAll(pageable)
                .map(exampleMapper::exampleToExampleDTO);

        return new ResponseEntity<>(exampleDTOPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiAudited
    @ApiOperation(value = "Get an example", nickname = "findById")
    public ResponseEntity<ExampleDTO> findById(@PathVariable("id") Long id) {
        Example createdExample = exampleService.findById(id);

        return new ResponseEntity<>(exampleMapper.exampleToExampleDTO(createdExample), HttpStatus.OK);
    }

    @PostMapping
    @ApiAudited
    @ApiOperation(value = "Create an example", nickname = "create")
    public ResponseEntity<ExampleDTO> create(@RequestBody @Valid ExampleDTO exampleDTO) {
        Example createdExample = exampleService.create(
                exampleMapper.exampleDTOToExample(exampleDTO)
        );
        return new ResponseEntity<>(exampleMapper.exampleToExampleDTO(createdExample), HttpStatus.CREATED);
    }

    @PutMapping
    @ApiAudited
    @ApiOperation(value = "Update an example", nickname = "update")
    public ResponseEntity<ExampleDTO> update(@RequestBody @Valid ExampleDTO exampleDTO) {
        Example createdExample = exampleService.update(
                exampleMapper.exampleDTOToExample(exampleDTO)
        );
        return new ResponseEntity<>(exampleMapper.exampleToExampleDTO(createdExample), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiAudited
    @ApiOperation(value = "Delete an example", nickname = "delete")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        exampleService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
