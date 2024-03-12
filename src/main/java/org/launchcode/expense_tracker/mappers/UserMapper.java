package org.launchcode.expense_tracker.mappers;

import org.launchcode.expense_tracker.models.SignUpDto;
import org.launchcode.expense_tracker.models.User;
import org.launchcode.expense_tracker.models.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);
}
