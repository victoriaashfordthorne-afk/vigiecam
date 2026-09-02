package Mbemlevel.example.VigieCam.Mapper;

import Mbemlevel.example.VigieCam.Dto.IncidentTrackingResponseDto;
import Mbemlevel.example.VigieCam.Model.IncidentTracking;
import org.springframework.stereotype.Component;

@Component
public class IncidentTrackingMapper {
    private final UserMapper userMapper;

    public IncidentTrackingMapper(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    public IncidentTrackingResponseDto toResponseDto(IncidentTracking tracking){
      if (tracking == null){
          return null;
      }
      IncidentTrackingResponseDto dto = new IncidentTrackingResponseDto();
      dto.setId(tracking.getId());

      if (tracking.getIncident()!= null){
          dto.setIncidentId(tracking.getIncident().getId());
      }
      if (tracking.getModerator()!= null){
          dto.setModerator(userMapper.toResponseDto(tracking.getModerator()));
      }
      dto.setOldStatus(tracking.getOldstatus());
      dto.setNewStatus(tracking.getNewstatus());
      dto.setMessage(tracking.getMessage());
      dto.setDateAdded(tracking.getDateAdded());
      return dto;
    }
}
