package Mbemlevel.example.VigieCam.Model;

import Mbemlevel.example.VigieCam.Enums.IncidentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "incident_tracking")
@Schema(
        name = "IncidentTracking",
        description = "Keeps the history of status changes made to an incident by moderators."
)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IncidentTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Unique identifier of the tracking record. Automatically generated.",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "incident_id",
            nullable = false
    )
    @Schema(
            description = "Incident concerned by the status change. Automatically determined from the context.",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Incident incident;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "moderator_id",
            nullable = false
    )
    @Schema(
            description = "Moderator who initiated the status change. Automatically obtained from the authenticated session.",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private User moderator;
    @Enumerated(EnumType.STRING)
    @Column(
            name = "old_status",
            nullable = false,
            length = 20
    )
    @Schema(
            description = "Status of the incident before the modification.",
            example = "NEW",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private IncidentStatus oldstatus;
    @Enumerated(EnumType.STRING)
    @Column(
            name = "new_status",
            nullable = false,
            length = 20
    )
    @Schema(
            description = "Status assigned to the incident after the modification.",
            example = "IN_PROGRESS",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private IncidentStatus newstatus;
    @Column(
            nullable = false,
            updatable = false
    )
    @Schema(
            description = "Date and time when the tracking record was created. Automatically generated.",
            example = "2026-08-31T22:30:00",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private LocalDateTime dateAdded;

    private String message;

    @PrePersist
    protected void onCreate() {
        this.dateAdded = LocalDateTime.now();
    }

    public void setNewStatus(IncidentStatus newStatus) {
    }

    public void setOldStatus(IncidentStatus oldStatus) {
    }
}
