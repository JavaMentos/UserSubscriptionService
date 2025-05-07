package ru.home.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.home.dto.UpdateUserDto;
import ru.home.dto.UserDto;
import ru.home.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user with the provided email and name.")
    @ApiResponse(responseCode = "201", description = "User created successfully",
            content = @Content(schema = @Schema(implementation = UserDto.class)))
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {

        UserDto createdUser = userService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{email}")
    @Operation(summary = "Get user by email", description = "get a user by email.")
    @ApiResponse(responseCode = "200", description = "User found",
            content = @Content(schema = @Schema(implementation = UserDto.class)))
    public ResponseEntity<UserDto> getUser(@PathVariable String email) {

        UserDto user = userService.getUserByEmail(email);

        return ResponseEntity.ok(user);
    }

    @PutMapping("/{email}")
    @Operation(summary = "Update user by email", description = "Updates the user's name by email.")
    @ApiResponse(responseCode = "200", description = "User updated successfully",
            content = @Content(schema = @Schema(implementation = UserDto.class)))
    public ResponseEntity<UserDto> updateUser(@PathVariable String email, @RequestBody UpdateUserDto updateUserDto) {

        UserDto updatedUser = userService.updateUser(email, updateUserDto);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{email}")
    @Operation(summary = "Delete user by email", description = "Deletes a user by their email.")
    @ApiResponse(responseCode = "204", description = "User deleted successfully")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {

        userService.deleteUser(email);

        return ResponseEntity.noContent().build();
    }
}