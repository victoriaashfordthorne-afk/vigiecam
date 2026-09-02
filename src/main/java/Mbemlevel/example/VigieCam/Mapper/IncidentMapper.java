package Mbemlevel.example.VigieCam.Mapper;

import Mbemlevel.example.VigieCam.Dto.IncidentRequestDto;
import Mbemlevel.example.VigieCam.Dto.IncidentResponseDto;
import Mbemlevel.example.VigieCam.Model.Incident;
import org.springframework.stereotype.Component;

@Component
public class IncidentMapper {
    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;

    public IncidentMapper(CategoryMapper categoryMapper,UserMapper userMapper){
        this.categoryMapper = categoryMapper;
        this.userMapper = userMapper;
    }

    public Incident toEntity(IncidentRequestDto dto){
        if (dto == null){
            return null;
        }

        Incident incident = new Incident();
        incident.setTitle(dto.getTitle());
        incident.setDescription(dto.getDescription());
        incident.setKind(dto.getKind());
        incident.setLocation(dto.getLocation());
        return incident;
    }

    public IncidentResponseDto toResponseDto(Incident incident) {
        if (incident == null) {
            return null;
        }
        IncidentResponseDto dto = new IncidentResponseDto();

        dto.setId(incident.getId());
        dto.setTitle(incident.getTitle());
        dto.setKind(incident.getKind());
        dto.setDescription(incident.getDescription());
        dto.setLocation(incident.getLocation());
        dto.setDateCreation(incident.getDateCreated());
        dto.setLastModifiedDate(incident.getLastModifiedDate());


        if (incident.getCategory() != null){
            dto.setCategory(categoryMapper.toResponseDto(incident.getCategory()));
        }
        if (incident.getAuthor() != null){
            dto.setAuthor(userMapper.toResponseDto(incident.getAuthor()));
        }
        return dto;
    }
}
