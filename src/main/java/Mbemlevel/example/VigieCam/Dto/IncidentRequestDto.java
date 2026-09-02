package Mbemlevel.example.VigieCam.Dto;

import Mbemlevel.example.VigieCam.Enums.IncidentKind;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data required to report an incident.")
public class IncidentRequestDto {

    @NotBlank(message = "Title is required")
    @Size(
            min = 5,
            max = 150,
            message = "Title must contain between 5 and 150 characters"
    )
    @Schema(
            description = "Title of the incident.",
            example = "Road accident between two vehicles",
            minLength = 5,
            maxLength = 150,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String title;

    @NotBlank(message = "Description is required")
    @Size(
            min = 20,
            max = 1000,
            message = "Description must contain between 20 and 1000 characters"
    )
    @Schema(
            description = "Detailed description of the incident.",
            example = "Two vehicles collided at the intersection after one driver ignored the traffic light.",
            minLength = 20,
            maxLength = 1000,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String description;

    @NotNull(message = "Kind is required")
    @Schema(
            description = "Kind of incident.",
            example = "INTER_URBAN",
            allowableValues = {"INTER_URBAN", "DOMESTIC"},
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private IncidentKind kind;

    @NotNull(message = "Category is required")
    @Schema(
            description = "Identifier of an existing incident category.",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long categoryId;

    @NotBlank(message = "Location is required")
    @Size(
            min = 2,
            max = 150,
            message = "Location must contain between 2 and 150 characters"
    )
    @Schema(
            description = "Location where the incident occurred.",
            example = "Douala, Bonanjo",
            minLength = 2,
            maxLength = 150,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String location;
}