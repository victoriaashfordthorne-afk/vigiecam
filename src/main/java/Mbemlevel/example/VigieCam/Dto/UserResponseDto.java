package Mbemlevel.example.VigieCam.Dto;


import Mbemlevel.example.VigieCam.Enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Information about a VigieCam user.")

public class UserResponseDto {
    @Schema(
            description = "Unique identifier of the user.",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "User's name retrieved from Google.",
            example = "John Doe"
    )
    private String name;

    @Schema(
            description = "User's email retrieved from Google.",
            example = "john.doe@gmail.com"
    )
    private String email;

    @Schema(
            description = "URL of the user's Google profile photo.",
            example = "https://lh3.googleusercontent.com/example"
    )
    private String photoUrl;

    @Schema(
            description = "Role automatically assigned by the application.",
            example = "USER",
            allowableValues = {"USER", "MODERATOR"}
    )
    private Role role;

    @Schema(
            description = "Date and time when the account was created.",
            example = "2026-08-31T22:30:00"
    )
    private LocalDateTime dateCreation;
}
