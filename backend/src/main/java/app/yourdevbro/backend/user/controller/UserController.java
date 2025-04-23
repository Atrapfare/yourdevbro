package app.yourdevbro.backend.user.controller;

import app.yourdevbro.backend.user.dto.RequestCreateUserDto;
import app.yourdevbro.backend.user.dto.UserDto;
import app.yourdevbro.backend.user.dto.RequestUpdateUserDto;
import app.yourdevbro.backend.user.dto.ChangePasswordRequestDto; // DTO for changing password
import app.yourdevbro.backend.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
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
        return userDto.map(ResponseEntity::ok) // Return 200 OK with user if found
                .orElse(ResponseEntity.notFound().build()); // Return 404 Not Found if not found
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid RequestCreateUserDto requestCreateUserDto) {
        UserDto userDto = userService.createUser(requestCreateUserDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED); // Return 201 Created with the new user
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody @Valid RequestUpdateUserDto updateUserDto) {
        Optional<UserDto> updatedUserDto = userService.updateUser(id, updateUserDto);
        return updatedUserDto.map(ResponseEntity::ok) // Return 200 OK with updated user if found
                .orElse(ResponseEntity.notFound().build()); // Return 404 Not Found if not found
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.deleteUser(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Return 204 No Content if deletion was successful
        } else {
            return ResponseEntity.notFound().build(); // Return 404 Not Found if user to delete was not found
        }
    }

    @PostMapping("/{id}/password")
    public ResponseEntity<Void> updateUserPassword(
            @PathVariable Long id,
            @RequestBody @Valid ChangePasswordRequestDto changePasswordRequestDto) {
        try {
            userService.updateUserPassword(id, changePasswordRequestDto);
            return ResponseEntity.noContent().build(); // Return 204 No Content if password update was successful
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // Return 400 Bad Request if old password was invalid
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build(); // Return 404 Not Found if user to update password for was not found
        }
    }
}