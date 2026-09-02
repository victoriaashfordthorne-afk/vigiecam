package Mbemlevel.example.VigieCam.Mapper;

import Mbemlevel.example.VigieCam.Dto.UserResponseDto;
import Mbemlevel.example.VigieCam.Model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDto toResponseDto(User user) {
    if (user == null){
        return null;
    }
      UserResponseDto dto = new UserResponseDto();
     dto.setId(user.getId());
     dto.setName(user.getName());
     dto.setEmail(user.getEmail());
     dto.setRole(user.getRole());
     dto.setPhotoUrl(user.getPhotoUrl());
     dto.setDateCreation(user.getDateCreation());
     return dto;
    }


    }
