package Mbemlevel.example.VigieCam.Dto;


import Mbemlevel.example.VigieCam.Enums.IncidentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "History of a status change made to an incident.")
public class IncidentTrackingResponseDto {

    @Schema(example = "1")
    private Long id;

    @Schema(
            description = "Identifier of the incident concerned by the status change.",
            example = "10"
    )
    private Long incidentId;

    @Schema(
            description = "Moderator who initiated the status change."
    )
    private UserResponseDto moderator;

    @Schema(
            description = "Status before the modification.",
            example = "NEW"
    )
    private IncidentStatus oldStatus;

    @Schema(
            description = "Status after the modification.",
            example = "IN_PROGRESS"
    )
    private IncidentStatus newStatus;

    @Schema(
            description = "Optional comment from the moderator.",
            example = "Investigation started."
    )
    private String message;

    @Schema(
            description = "Date and time of the status change."
    )
    private LocalDateTime dateAdded;
}