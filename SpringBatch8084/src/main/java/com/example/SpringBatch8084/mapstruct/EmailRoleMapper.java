package com.example.SpringBatch8084.mapstruct;

import com.example.SpringBatch8084.DTO.EmailRoleDTO;
import com.example.SpringBatch8084.DTO.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmailRoleMapper {
    @Mapping(target = "email", source = "email")
    @Mapping(target = "nameRole", source = "nameRole")
    EmailRoleDTO userDTOToEmailRole(UserDTO userDTO);

}
