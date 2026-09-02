package Mbemlevel.example.VigieCam.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Information about an incident category.")
public class CategoryResponseDto {

    @Schema(
            description = "Unique identifier of the category.",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Name of the category.",
            example = "Road accident"
    )
    private String name;

    @Schema(
            description = "Description of the category.",
            example = "Incidents involving road accidents."
    )
    private String description;
}