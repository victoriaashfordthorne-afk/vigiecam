package Mbemlevel.example.VigieCam.Controller;




import Mbemlevel.example.VigieCam.Dto.IncidentRequestDto;
import Mbemlevel.example.VigieCam.Dto.IncidentResponseDto;
import Mbemlevel.example.VigieCam.Model.User;
import Mbemlevel.example.VigieCam.Services.AuthenticatedUserServices;
import Mbemlevel.example.VigieCam.Services.IncidentServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidents")
@Tag(
        name = "Incidents",
        description = "Citizen incident reporting and consultation endpoints"
)
public class IncidentController {

    private final IncidentServices incidentService;
    private final AuthenticatedUserServices authenticatedUserService;

    public IncidentController(
            IncidentServices incidentService,
            AuthenticatedUserServices authenticatedUserService
    ) {
        this.incidentService = incidentService;
        this.authenticatedUserService = authenticatedUserService;
    }

    @Operation(
            summary = "Report an incident",
            description = "Allows an authenticated citizen to report a new incident."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Incident successfully created"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid incident data"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not authenticated"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found"
            )
    })
    @PostMapping
    public ResponseEntity<IncidentResponseDto> createIncident(
            @Valid @RequestBody IncidentRequestDto request
    ) {

        User user =
                authenticatedUserService.getCurrentUser();

        IncidentResponseDto incident =
                incidentService.createIncident(
                        request,
                        user
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(incident);
    }

    @Operation(
            summary = "Get my incidents",
            description = "Returns only the incidents reported by the authenticated user."
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
            )
    })
    @GetMapping("/my")
    public ResponseEntity<List<IncidentResponseDto>> getMyIncidents() {

        User user =
                authenticatedUserService.getCurrentUser();

        List<IncidentResponseDto> incidents =
                incidentService.getMyIncidents(user);

        if (incidents.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(incidents);
    }

    @Operation(
            summary = "Get my incident by ID",
            description = "Returns an incident belonging to the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Incident successfully retrieved"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not authenticated"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Incident does not belong to the authenticated user"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Incident not found"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<IncidentResponseDto> getMyIncident(
            @PathVariable Long id
    ) {

        User user =
                authenticatedUserService.getCurrentUser();

        return ResponseEntity.ok(
                incidentService.getMyIncident(
                        id,
                        user
                )
        );
    }
}