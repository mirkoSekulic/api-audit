package com.audit.apiaudit.modules.users.mapper;

import com.audit.apiaudit.modules.users.dto.UserDTO;
import com.audit.apiaudit.modules.users.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {

    @Mapping(target = "authorities", ignore = true)
    UserDTO userToUserDTOWithoutAuthorities(User user);
}
