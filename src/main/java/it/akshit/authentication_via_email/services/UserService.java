package it.akshit.authentication_via_email.services;

import it.akshit.authentication_via_email.dtos.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDto createUser(UserDto userDto);
    UserDto getUser(UUID uuid);
    List<UserDto> getUsers();
}
