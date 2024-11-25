package gajudama.javematch.accesoDatos;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import gajudama.javematch.model.Plan;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    @SuppressWarnings("null")
    Optional<Plan> findById(Long id); 
}
