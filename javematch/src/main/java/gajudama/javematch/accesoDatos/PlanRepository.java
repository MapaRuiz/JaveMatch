package gajudama.javematch.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import gajudama.javematch.model.Plan;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
}
