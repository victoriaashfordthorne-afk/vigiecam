package Mbemlevel.example.VigieCam.Model;

import Mbemlevel.example.VigieCam.Enums.IncidentKind;
import Mbemlevel.example.VigieCam.Enums.IncidentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "incidents")
@Schema(
        name = "Incident",
        description = "Represents an incident reported by an authenticated VigieCam user."
)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Unique identifier of the incident. Automatically generated.",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;
    @NotBlank(message = "Title is required")
    @Size(
            min = 5,
            max = 150,
            message = "Title must contain between 5 and 150 characters"
    )
    @Column(
            nullable = false,
            length = 150
    )
    @Schema(
            description = "Short title describing the incident.",
            example = "Road accident between two vehicles",
            minLength = 5,
            maxLength = 150,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String title;

    @NotBlank(message = "Description is required")
    @Size(
            min = 20,
            max = 1000,
            message = "Description must contain between 20 and 1000 characters"
    )
    @Column(
            nullable = false,
            length = 1000
    )
    @Schema(
            description = "Detailed description of the incident.",
            example = "Two vehicles collided at the intersection after one driver ignored the traffic light.",
            minLength = 20,
            maxLength = 1000,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String description;

    @NotNull(message = "Kind is required")
    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 20
    )
    @Schema(
            description = "Type of incident.",
            example = "INTER_URBAN",
            allowableValues = {
                    "INTER_URBAN",
                    "DOMESTIC"
            },
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private IncidentKind kind;

    @NotNull(message = "Category is required")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "category_id",
            nullable = false
    )
    @Schema(
            description = "Category associated with the incident. Must reference an existing category.",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Category category;

    @NotBlank(message = "Location is required")
    @Size(
            min = 2,
            max = 150,
            message = "Location must contain between 2 and 150 characters"
    )
    @Column(
            nullable = false,
            length = 150
    )
    @Schema(
            description = "Location where the incident occurred.",
            example = "Douala, Bonanjo",
            minLength = 2,
            maxLength = 150,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 20
    )
    @Schema(
            description = "Current processing status of the incident.",
            example = "NEW",
            allowableValues = {
                    "NEW",
                    "IN_PROGRESS",
                    "RESOLVED",
                    "CLOSED",
                    "PRIORITY"
            },
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private IncidentStatus status;

    @NotNull(message = "Author is required")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "author_id",
            nullable = false
    )
    @Schema(
            description = "User who reported the incident. Automatically obtained from the authenticated session.",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private User author;
    @Column(
            nullable = false,
            updatable = false
    )
    @Schema(
            description = "Date and time when the incident was created. Automatically generated.",
            example = "2026-08-31T21:30:00",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private LocalDateTime dateCreated;
    @Column(nullable = false)
    @Schema(
            description = "Date and time when the incident was last modified. Automatically generated.",
            example = "2026-08-31T22:15:00",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private LocalDateTime lastModifiedDate;

    @PrePersist
    protected void onCreate() {
        this.dateCreated = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();
        this.status = IncidentStatus.NEW;
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastModifiedDate = LocalDateTime.now();
    }
}
