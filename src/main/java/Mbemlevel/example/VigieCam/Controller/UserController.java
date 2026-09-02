package Mbemlevel.example.VigieCam.Controller;

import Mbemlevel.example.VigieCam.Dto.UserResponseDto;
import Mbemlevel.example.VigieCam.Services.UserServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(
        name = "Users",
        description = "Endpoints for managing VigieCam users"
)
public class UserController {

    private final UserServices userServices;
    public UserController(UserServices userServices){
        this.userServices = userServices;
    }



    @Operation(
            summary = "Get all users",
            description = "Returns the list of all registered users."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Users successfully retrieved"
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No users found"
            )
    })
    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDto>>findAllUser(){
  List<UserResponseDto> user = userServices.findAll();
  return ResponseEntity.ok(user);
    }

    @Operation(
            summary = "Get user by ID",
            description = "Returns a user using their unique identifier."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User successfully retrieved"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id){
        return ResponseEntity.ok(userServices.findById(id));
    }
}
