package Mbemlevel.example.VigieCam.Repository;

import Mbemlevel.example.VigieCam.Enums.IncidentStatus;
import Mbemlevel.example.VigieCam.Model.Incident;
import Mbemlevel.example.VigieCam.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<Incident,Long> {
    List<Incident>findByAuthor(User author);

    List<IncidentStatus> findByAuthorAndStatus(
            User author,
            IncidentStatus status
    );
    List<Incident>findByStatus(IncidentStatus status);
}
