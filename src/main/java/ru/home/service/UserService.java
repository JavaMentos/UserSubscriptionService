package ru.home.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.home.dto.UpdateUserDto;
import ru.home.dto.UserDto;
import ru.home.entity.User;
import ru.home.exception.UserAlreadyExistsException;
import ru.home.exception.UserNotFoundException;
import ru.home.mapper.UserMapper;
import ru.home.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto createUser(UserDto userDto) {
        if (userRepository.findUserByEmail(userDto.email()).isPresent()) {
            log.warn("Try to create user with existing email: {}", userDto.email());
            throw new UserAlreadyExistsException("User with email " + userDto.email() + " already exists");
        }
        User user = userMapper.toEntity(userDto);
        User savedUser = userRepository.save(user);
        log.info("Created user with email: {}", savedUser.getEmail());
        return userMapper.toDto(savedUser);
    }

    public UserDto getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> {
                    log.error("User with email {} not found", email);
                    return new UserNotFoundException("User with email " + email + " not found");
                });
        log.info("get user with email: {}", email);
        return userMapper.toDto(user);
    }

    public UserDto updateUser(String email, UpdateUserDto updateUserDto) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> {
                    log.error("User with email {} not found for update", email);
                    return new UserNotFoundException("User with email " + email + " not found");
                });
        user.setName(updateUserDto.name());
        User updatedUser = userRepository.save(user);
        log.info("Updated user with email: {}", updatedUser.getEmail());
        return userMapper.toDto(updatedUser);
    }

    public void deleteUser(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> {
                    log.error("User with email {} not found for deletion", email);
                    return new UserNotFoundException("User with email " + email + " not found");
                });
        userRepository.delete(user);
        log.info("Deleted user with email: {}", email);
    }
}