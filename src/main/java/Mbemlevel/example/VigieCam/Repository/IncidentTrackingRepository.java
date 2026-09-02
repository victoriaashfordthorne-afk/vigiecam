package Mbemlevel.example.VigieCam.Repository;

import Mbemlevel.example.VigieCam.Model.Incident;
import Mbemlevel.example.VigieCam.Model.IncidentTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentTrackingRepository extends JpaRepository<IncidentTracking,Long> {
    List<IncidentTracking>findByIncidentOrderByDateAddedAsc(
            Incident incident
    );


}
