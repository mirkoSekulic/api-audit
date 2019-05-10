package com.audit.apiaudit.modules.users.controller;

import com.audit.apiaudit.modules.users.dto.UserDTO;
import com.audit.apiaudit.modules.users.filter.UserFilter;
import com.audit.apiaudit.modules.users.mapper.UserMapper;
import com.audit.apiaudit.modules.users.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "users", tags = {"users"})
@RequestMapping("users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    @ApiOperation(value = "Get a page of user accounts..", nickname = "findAll")
    public ResponseEntity<Page<UserDTO>> list(Pageable pageable, UserFilter userFilter)
    {
        Page<UserDTO> userDTOPage = userService.findAll(userFilter, pageable)
                .map(userMapper::userToUserDTOWithoutAuthorities);

        return new ResponseEntity<>(userDTOPage, HttpStatus.OK);
    }
}
