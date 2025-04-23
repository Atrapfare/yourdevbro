package app.yourdevbro.backend.user.service;

import app.yourdevbro.backend.user.dto.ChangePasswordRequestDto;
import app.yourdevbro.backend.user.dto.RequestCreateUserDto;
import app.yourdevbro.backend.user.dto.RequestUpdateUserDto;
import app.yourdevbro.backend.user.dto.UserDto;
import app.yourdevbro.backend.user.model.User;
import app.yourdevbro.backend.user.repository.UserRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing user data.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * Constructs a new UserService with the provided UserRepository.
     *
     * @param userRepository The repository for accessing user data.
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all users from the database.
     *
     * @return A list of all users as UserDto objects.
     */
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return An Optional containing the UserDto if found, or an empty Optional otherwise.
     */
    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id).map(this::convertToDto);
    }

    /**
     * Creates a new user. Ensures that the provided email and phone number are unique.
     *
     * @param requestCreateUserDto The data for the new user.
     * @return The created user as a UserDto object.
     * @throws ResponseStatusException If the email or phone number is already registered (HTTP 400 Bad Request).
     */
    public UserDto createUser(RequestCreateUserDto requestCreateUserDto) {
        if (userRepository.existsByEmail(requestCreateUserDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This email address is already registered.");
        }
        if (userRepository.existsByPhone(requestCreateUserDto.getPhone())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This phone number is already registered.");
        }
        User user = convertToEntity(requestCreateUserDto);
        // Password hashing would typically go here
        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    /**
     * Updates an existing user. Ensures that a new email or phone number is unique if changed.
     *
     * @param id            The ID of the user to update.
     * @param updateUserDto The updated user data.
     * @return An Optional containing the updated UserDto if found, or an empty Optional otherwise.
     * @throws ResponseStatusException If a new email or phone number is already registered (HTTP 400 Bad Request).
     */
    public Optional<UserDto> updateUser(Long id, RequestUpdateUserDto updateUserDto) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    if (updateUserDto.getPreName() != null) existingUser.setPreName(updateUserDto.getPreName());
                    if (updateUserDto.getLastName() != null) existingUser.setLastName(updateUserDto.getLastName());
                    if (updateUserDto.getEmail() != null && !existingUser.getEmail().equals(updateUserDto.getEmail())) {
                        if (userRepository.existsByEmail(updateUserDto.getEmail())) {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This email address is already registered.");
                        }
                        existingUser.setEmail(updateUserDto.getEmail());
                    }
                    if (updateUserDto.getPhone() != null && !existingUser.getPhone().equals(updateUserDto.getPhone())) {
                        if (userRepository.existsByPhone(updateUserDto.getPhone())) {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This phone number is already registered.");
                        }
                        existingUser.setPhone(updateUserDto.getPhone());
                    }
                    if (updateUserDto.getDateOfBirth() != null)
                        existingUser.setDateOfBirth(updateUserDto.getDateOfBirth());
                    User updatedUser = userRepository.save(existingUser);
                    return convertToDto(updatedUser);
                });
    }


    public void updateUserPassword(Long id, ChangePasswordRequestDto changePasswordRequestDto)
            throws IllegalArgumentException, ChangeSetPersister.NotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        // Verify the old password
        if (changePasswordRequestDto.getOldPassword().equals(user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid old password");
        }

        // Encode and set the new password
        String newPasswordHash = changePasswordRequestDto.getNewPassword();
        user.setPasswordHash(newPasswordHash);
        userRepository.save(user);
    }



    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to delete.
     * @return {@code true} if the user was successfully deleted, {@code false} otherwise.
     */
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Converts a User entity to a UserDto object.
     *
     * @param user The User entity to convert.
     * @return The corresponding UserDto.
     */
    private UserDto convertToDto(User user) {
        return new UserDto(user.getId(), user.getPreName(), user.getLastName(), user.getEmail(), user.getPhone(), user.getDateOfBirth());
    }

    /**
     * Converts a RequestCreateUserDto object to a User entity.
     *
     * @param requestCreateUserDto The DTO to convert.
     * @return The corresponding User entity.
     */
    private User convertToEntity(RequestCreateUserDto requestCreateUserDto) {
        User user = new User();
        user.setPreName(requestCreateUserDto.getPreName());
        user.setLastName(requestCreateUserDto.getLastName());
        user.setEmail(requestCreateUserDto.getEmail());
        user.setPhone(requestCreateUserDto.getPhone());
        user.setDateOfBirth(requestCreateUserDto.getDateOfBirth());
        return user;
    }
}