package Mbemlevel.example.VigieCam.Dto;

import Mbemlevel.example.VigieCam.Enums.IncidentKind;
import Mbemlevel.example.VigieCam.Enums.IncidentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Information about a reported incident.")
public class IncidentResponseDto {

    @Schema(example = "1")
    private Long id;

    @Schema(
            description = "Title of the incident.",
            example = "Road accident between two vehicles"
    )
    private String title;

    @Schema(
            description = "Detailed description of the incident.",
            example = "Two vehicles collided at the intersection."
    )
    private String description;

    @Schema(
            description = "Kind of incident.",
            example = "INTER_URBAN"
    )
    private IncidentKind kind;

    @Schema(
            description = "Category of the incident."
    )
    private CategoryResponseDto category;

    @Schema(
            description = "Location where the incident occurred.",
            example = "Douala, Bonanjo"
    )
    private String location;

    @Schema(
            description = "Current status of the incident.",
            example = "NEW"
    )
    private IncidentStatus status;

    @Schema(
            description = "User who reported the incident."
    )
    private UserResponseDto author;

    @Schema(
            description = "Date and time when the incident was created."
    )
    private LocalDateTime dateCreation;

    @Schema(
            description = "Date and time when the incident was last modified."
    )
    private LocalDateTime lastModifiedDate;
}