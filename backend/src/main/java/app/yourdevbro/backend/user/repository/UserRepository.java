package app.yourdevbro.backend.user.repository;

import app.yourdevbro.backend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their email address.
     * @param email The email address to search for.
     * @return An Optional containing the User if found, otherwise empty.
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by their phone number.
     * @param phone The phone number to search for.
     * @return An Optional containing the User if found, otherwise empty.
     */
    Optional<User> findByPhone(String phone);

    /**
     * Finds a user by their prename and lastname.
     * @param preName The prename to search for.
     * @param lastName The lastname to search for.
     * @return An Optional containing the User if found, otherwise empty.
     */
    Optional<User> findByPreNameAndLastName(String preName, String lastName);

    /**
     * Checks if a user with the given email address exists.
     * @param email The email address to check.
     * @return True if a user with the email exists, false otherwise.
     */
    boolean existsByEmail(String email);

    /**
     * Checks if a user with the given phone number exists.
     * @param phone The phone number to check.
     * @return True if a user with the phone number exists, false otherwise.
     */
    boolean existsByPhone(String phone);

}