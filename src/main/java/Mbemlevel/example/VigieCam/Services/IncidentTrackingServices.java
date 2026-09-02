package Mbemlevel.example.VigieCam.Services;

import Mbemlevel.example.VigieCam.Dto.IncidentStatusUpdateDto;
import Mbemlevel.example.VigieCam.Dto.IncidentTrackingResponseDto;
import Mbemlevel.example.VigieCam.Enums.IncidentStatus;
import Mbemlevel.example.VigieCam.Mapper.IncidentTrackingMapper;
import Mbemlevel.example.VigieCam.Model.Incident;
import Mbemlevel.example.VigieCam.Model.IncidentTracking;
import Mbemlevel.example.VigieCam.Model.User;
import Mbemlevel.example.VigieCam.Repository.IncidentRepository;
import Mbemlevel.example.VigieCam.Repository.IncidentTrackingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidentTrackingServices {
    private final IncidentRepository incidentRepository;
    private final IncidentTrackingRepository trackingRepository;
    private final IncidentTrackingMapper trackingMapper;

    public IncidentTrackingServices(
            IncidentRepository incidentRepository,
            IncidentTrackingRepository trackingRepository,
            IncidentTrackingMapper trackingMapper
    ) {
        this.incidentRepository = incidentRepository;
        this.trackingRepository = trackingRepository;
        this.trackingMapper = trackingMapper;
    }
    public IncidentTrackingResponseDto updateStatus(
            Long incidentId,
            IncidentStatusUpdateDto request,
            User moderator
    ) {

        Incident incident =
                incidentRepository.findById(incidentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Incident not found"
                                )
                        );

        IncidentStatus oldStatus =
                incident.getStatus();

        IncidentStatus newStatus =
                request.getNewStatus();

        // Create tracking entity
        IncidentTracking tracking =
                new IncidentTracking();

        tracking.setIncident(incident);
        tracking.setModerator(moderator);
        tracking.setOldStatus(oldStatus);
        tracking.setNewStatus(newStatus);
        tracking.setMessage(request.getMessage());

        // Update incident
        incident.setStatus(newStatus);

        incidentRepository.save(incident);

        IncidentTracking savedTracking =
                trackingRepository.save(tracking);

        // Entity → DTO
        return trackingMapper.toResponseDto(
                savedTracking
        );
    }
    public List<IncidentTrackingResponseDto> getHistory(
            Long incidentId
    ) {

        Incident incident =
                incidentRepository.findById(incidentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Incident not found"
                                )
                        );

        return trackingRepository
                .findByIncidentOrderByDateAddedAsc(incident)
                .stream()
                .map(trackingMapper::toResponseDto)
                .toList();
    }

}
