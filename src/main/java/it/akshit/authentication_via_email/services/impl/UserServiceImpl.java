package it.akshit.authentication_via_email.services.impl;

import it.akshit.authentication_via_email.dtos.UserDto;
import it.akshit.authentication_via_email.exceptions.DuplicateResourceException;
import it.akshit.authentication_via_email.exceptions.ResourceNotFoundException;
import it.akshit.authentication_via_email.models.User;
import it.akshit.authentication_via_email.repository.UserRepository;
import it.akshit.authentication_via_email.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new DuplicateResourceException("User already exists!");
        }
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setRoles(List.of("USER"));
        user.setIsActive(true);

        userRepository.save(user);

        return userDto;
    }

    @Override
    public UserDto getUser(UUID uuid) {
        User user = userRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        return new UserDto(user.getFirstName(), user.getLastName(), user.getEmail());
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> new UserDto(user.getFirstName(), user.getLastName(), user.getEmail())).toList();
    }
}
