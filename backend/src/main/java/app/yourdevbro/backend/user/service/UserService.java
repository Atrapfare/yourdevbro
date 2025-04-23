package app.yourdevbro.backend.user.service;

import app.yourdevbro.backend.user.dto.CreateUserRequestDto;
import app.yourdevbro.backend.user.dto.UpdateUserRequestDto;
import app.yourdevbro.backend.user.dto.UserDto;
import app.yourdevbro.backend.user.model.User;
import app.yourdevbro.backend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDto);
    }

    public UserDto createUser(CreateUserRequestDto createUserRequestDto) {
        User user = convertToEntity(createUserRequestDto);
        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    public Optional<UserDto> updateUser(Long id, UpdateUserRequestDto updateUserRequestDto) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User userToUpdate = existingUser.get();
            userToUpdate.setPreName(updateUserRequestDto.getPreName());
            userToUpdate.setLastName(updateUserRequestDto.getLastName());
            userToUpdate.setEmail(updateUserRequestDto.getEmail());
            userToUpdate.setPhone(updateUserRequestDto.getPhone());

            User updatedUser = userRepository.save(userToUpdate);
            return Optional.of(convertToDto(updatedUser));
        }
        return Optional.empty();
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private UserDto convertToDto(User user) {
        return new UserDto(user.getId(), user.getPreName(), user.getLastName(), user.getEmail(), user.getPhone());
    }

    private User convertToEntity(CreateUserRequestDto dto) {
        return new User(null, dto.getPreName(), dto.getLastName(), dto.getEmail(), dto.getPhone());
    }
}