package Mbemlevel.example.VigieCam.Dto;


import Mbemlevel.example.VigieCam.Enums.IncidentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data required for a moderator to update an incident status.")
public class IncidentStatusUpdateDto {

    @NotNull(message = "New status is required")
    @Schema(
            description = "New status to assign to the incident.",
            example = "IN_PROGRESS",
            allowableValues = {
                    "NEW",
                    "IN_PROGRESS",
                    "RESOLVED",
                    "CLOSED",
                    "PRIORITY"
            },
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private IncidentStatus newStatus;

    @Size(
            max = 200,
            message = "Message must not exceed 200 characters"
    )
    @Schema(
            description = "Optional comment explaining the status change.",
            example = "The incident is currently being investigated.",
            maxLength = 200
    )
    private String message;


}