package app.yourdevbro.backend.user.controller;

import app.yourdevbro.backend.user.dto.CreateUserRequestDto;
import app.yourdevbro.backend.user.dto.UpdateUserRequestDto;
import app.yourdevbro.backend.user.dto.UserDto;
import app.yourdevbro.backend.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        Optional<UserDto> userDto = userService.getUserById(id);
        return userDto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserRequestDto createUserRequestDto) {
        UserDto createdUserDto = userService.createUser(createUserRequestDto);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequestDto updateUserRequestDto) {
        Optional<UserDto> updatedUserDto = userService.updateUser(id, updateUserRequestDto);
        return updatedUserDto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.deleteUser(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}