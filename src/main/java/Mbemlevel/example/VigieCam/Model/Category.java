package Mbemlevel.example.VigieCam.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Schema(
        name = "Category",
        description = "Represents a category used to classify reported incidents."
)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Unique identifier of the category. Automatically generated.",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @NotBlank(message = "Category name is required")
    @Size(
            max = 100,
            message = "Category name must not exceed 100 characters"
    )
    @Column(
            nullable = false,
            length = 100
    )
    @Schema(
            description = "Name used to classify incidents.",
            example = "Road accident",
            maxLength = 100,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    @Size(
            max = 500,
            message = "Category description must not exceed 500 characters"
    )
    @Column(length = 500)
    @Schema(
            description = "Optional description of the category.",
            example = "Incidents involving collisions or accidents involving vehicles."
    )
    private String description;
}
