package Mbemlevel.example.VigieCam.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data required to create or update an incident category.")
public class CategoryRequestDto {

    @NotBlank(message = "Category name is required")
    @Size(
            max = 100,
            message = "Category name must not exceed 100 characters"
    )
    @Schema(
            description = "Name of the category.",
            example = "Road accident",
            requiredMode = Schema.RequiredMode.REQUIRED,
            maxLength = 100
    )
    private String name;

    @Size(
            max = 500,
            message = "Category description must not exceed 500 characters"
    )
    @Schema(
            description = "Optional description of the category.",
            example = "Incidents involving road accidents."
    )
    private String description;
}