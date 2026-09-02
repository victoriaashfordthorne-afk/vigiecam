package Mbemlevel.example.VigieCam.Services;

import Mbemlevel.example.VigieCam.Dto.UserResponseDto;
import Mbemlevel.example.VigieCam.Mapper.UserMapper;
import Mbemlevel.example.VigieCam.Model.User;
import Mbemlevel.example.VigieCam.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServices(UserRepository userRepository,UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    public List<UserResponseDto> findAll(){
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDto)
                .toList();
    }
    public UserResponseDto findById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found with id"+ id));
        return userMapper.toResponseDto(user);
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found with email"+ email));
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }
}
