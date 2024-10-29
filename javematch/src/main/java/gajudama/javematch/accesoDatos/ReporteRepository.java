package gajudama.javematch.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gajudama.javematch.model.Reporte;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {

}
