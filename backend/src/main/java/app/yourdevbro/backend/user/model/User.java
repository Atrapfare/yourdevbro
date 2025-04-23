package app.yourdevbro.backend.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pre_name")
    private String preName;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true) // unique email address
    private String email;

    @Column(unique = true) // unique phone
    private String phone;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "created_at", updatable = false) // Records when the user was created, cannot be updated
    private LocalDateTime createdAt;

    @Column(name = "updated_at") // Records the last time the user was updated
    private LocalDateTime updatedAt;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @PrePersist // Method called before the entity is persisted to the database
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate // Method called before the entity is updated in the database
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public User(Long id, String preName, String lastName, String email, String phone, LocalDate dateOfBirth) {
        this.id = id;
        this.preName = preName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
    }
}