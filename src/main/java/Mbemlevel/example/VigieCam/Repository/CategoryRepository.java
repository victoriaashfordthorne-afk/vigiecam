package Mbemlevel.example.VigieCam.Repository;

import Mbemlevel.example.VigieCam.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository  extends JpaRepository<Category,Long> {
    boolean existsByName(String name);
}
