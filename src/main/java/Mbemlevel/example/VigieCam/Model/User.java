package Mbemlevel.example.VigieCam.Model;

import Mbemlevel.example.VigieCam.Enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(  name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_user_email",
                        columnNames = "email")
        })
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "User",
        description = "Represents a VigieCam user authenticated through Google. " +
                "The user's role is automatically determined from their Google email address."
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Unique identifier of the user. Automatically generated.",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(
            description = "Full name retrieved from the user's Google account.",
            example = "John Doe",
            maxLength = 100
    )
    @Column(nullable = false,length = 100)
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @Schema(
            description = "Email address retrieved from Google. " +
                    "The email is also used to determine whether the user is a USER or MODERATOR.",
            example = "john.doe@gmail.com",
            maxLength = 150
    )
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 150, message = "Email must not exceed 150 characters")
    @Column(nullable = false, unique = true, length = 150)
    private  String email;


    @Size(max = 500, message = "Photo URL must not exceed 500 characters")
    @Column(length = 500)
    private String photoUrl;

    @Schema(
            description = "Role automatically assigned by the application based on the user's Google email.",
            example = "USER",
            allowableValues = {"USER", "MODERATOR"},
            accessMode = Schema.AccessMode.READ_ONLY
    )
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    @Schema(
            description = "Date and time when the user account was created. Automatically generated.",
            example = "2026-08-31T21:30:00",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
    }
}
