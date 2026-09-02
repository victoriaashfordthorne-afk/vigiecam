package Mbemlevel.example.VigieCam.Services;

import Mbemlevel.example.VigieCam.Dto.IncidentRequestDto;
import Mbemlevel.example.VigieCam.Dto.IncidentResponseDto;
import Mbemlevel.example.VigieCam.Enums.IncidentStatus;
import Mbemlevel.example.VigieCam.Mapper.IncidentMapper;
import Mbemlevel.example.VigieCam.Model.Category;
import Mbemlevel.example.VigieCam.Model.Incident;
import Mbemlevel.example.VigieCam.Model.User;
import Mbemlevel.example.VigieCam.Repository.IncidentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidentServices {
    private final IncidentRepository incidentRepository;
    private final CategoryServices categoryService;
    private final IncidentMapper incidentMapper;

    public IncidentServices(
            IncidentRepository incidentRepository,
            CategoryServices categoryService,
            IncidentMapper incidentMapper
    ) {
        this.incidentRepository = incidentRepository;
        this.categoryService = categoryService;
        this.incidentMapper = incidentMapper;
    }
    public List<IncidentResponseDto> getMyIncidents(
            User user
    ) {

        return incidentRepository
                .findByAuthor(user)
                .stream()
                .map(incidentMapper::toResponseDto)
                .toList();
    }
    public IncidentResponseDto getMyIncident(
            Long incidentId,
            User user
    ) {

        Incident incident =
                incidentRepository.findById(incidentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Incident not found"
                                )
                        );
        if (!incident.getAuthor()
                .getId()
                .equals(user.getId())) {

            throw new RuntimeException(
                    "You are not authorized to access this incident"
            );
        }

        return incidentMapper.toResponseDto(incident);
    }
    public IncidentResponseDto createIncident(
            IncidentRequestDto request,
            User author
    ) {

        // Find category
        Category category =
                categoryService.getCategoryEntityById(
                        request.getCategoryId()
                );

        // DTO → Entity
        Incident incident =
                incidentMapper.toEntity(request);

        // Set backend-controlled fields
        incident.setCategory(category);
        incident.setAuthor(author);
        incident.setStatus(IncidentStatus.NEW);

        // Save
        Incident savedIncident =
                incidentRepository.save(incident);

        // Entity → Response DTO
        return incidentMapper.toResponseDto(savedIncident);
    }
    public List<IncidentResponseDto> getAllIncidents() {

        return incidentRepository.findAll()
                .stream()
                .map(incidentMapper::toResponseDto)
                .toList();
    }
    public List<IncidentResponseDto> getIncidentsByStatus(
            IncidentStatus status
    ) {

        return incidentRepository.findByStatus(status)
                .stream()
                .map(incidentMapper::toResponseDto)
                .toList();
    }
}
