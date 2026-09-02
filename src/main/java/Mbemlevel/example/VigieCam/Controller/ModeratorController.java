package Mbemlevel.example.VigieCam.Controller;


import Mbemlevel.example.VigieCam.Dto.IncidentResponseDto;
import Mbemlevel.example.VigieCam.Dto.IncidentStatusUpdateDto;
import Mbemlevel.example.VigieCam.Dto.IncidentTrackingResponseDto;
import Mbemlevel.example.VigieCam.Enums.IncidentStatus;
import Mbemlevel.example.VigieCam.Model.User;
import Mbemlevel.example.VigieCam.Services.AuthenticatedUserServices;
import Mbemlevel.example.VigieCam.Services.IncidentServices;
import Mbemlevel.example.VigieCam.Services.IncidentTrackingServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/moderator")
@Tag(
        name = "Moderator",
        description = "Endpoints used by moderators to process incidents"
)
public class ModeratorController {

    private final IncidentServices incidentService;
    private final IncidentTrackingServices trackingService;
    private final AuthenticatedUserServices authenticatedUserService;

    public ModeratorController(
            IncidentServices incidentService,
            IncidentTrackingServices trackingService,
            AuthenticatedUserServices authenticatedUserService
    ) {
        this.incidentService = incidentService;
        this.trackingService = trackingService;
        this.authenticatedUserService = authenticatedUserService;
    }

    @Operation(
            summary = "Get all incidents",
            description = "Allows a moderator to view all reported incidents."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Incidents successfully retrieved"
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No incidents found"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not authenticated"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "User is not a moderator"
            )
    })
    @GetMapping("/incidents")
    public ResponseEntity<List<IncidentResponseDto>> getAllIncidents() {

        List<IncidentResponseDto> incidents =
                incidentService.getAllIncidents();

        if (incidents.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(incidents);
    }

    @Operation(
            summary = "Get incidents by status",
            description = "Allows a moderator to filter incidents by status."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Incidents successfully retrieved"
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No incidents match the requested status"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not authenticated"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "User is not a moderator"
            )
    })
    @GetMapping("/incidents/status/{status}")
    public ResponseEntity<List<IncidentResponseDto>> getIncidentsByStatus(
            @PathVariable IncidentStatus status
    ) {

        List<IncidentResponseDto> incidents =
                incidentService.getIncidentsByStatus(status);

        if (incidents.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(incidents);
    }

    @Operation(
            summary = "Update incident status",
            description = "Allows a moderator to change an incident status and records the change."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Incident status successfully updated"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid status update"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not authenticated"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "User is not a moderator"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Incident not found"
            )
    })
    @PatchMapping("/incidents/{id}/status")
    public ResponseEntity<IncidentTrackingResponseDto> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody IncidentStatusUpdateDto request
    ) {

        User moderator =
                authenticatedUserService.getCurrentUser();

        IncidentTrackingResponseDto tracking =
                trackingService.updateStatus(
                        id,
                        request,
                        moderator
                );

        return ResponseEntity.ok(tracking);
    }

    @Operation(
            summary = "Get incident tracking history",
            description = "Returns the complete history of status changes for an incident."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Tracking history successfully retrieved"
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No tracking history found"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not authenticated"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "User is not a moderator"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Incident not found"
            )
    })
    @GetMapping("/incidents/{id}/tracking")
    public ResponseEntity<List<IncidentTrackingResponseDto>> getHistory(
            @PathVariable Long id
    ) {

        List<IncidentTrackingResponseDto> history =
                trackingService.getHistory(id);

        if (history.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(history);
    }
}