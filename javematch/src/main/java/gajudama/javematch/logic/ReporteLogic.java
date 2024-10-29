package gajudama.javematch.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gajudama.javematch.accesoDatos.ReporteRepository;
import gajudama.javematch.model.Reporte;

@Service
public class ReporteLogic {
    @Autowired
    private ReporteRepository reporteRepository;

    public Reporte createReporte(Reporte reporte) {
        return reporteRepository.save(reporte);
    }

    public Optional<Reporte> getReporteById(Long id) {
        return reporteRepository.findById(id);
    }

    public Reporte updateReporte(Long id, Reporte reporteDetails) {
        return reporteRepository.findById(id).map(reporte -> {
            reporte.setTipo(reporteDetails.getTipo());
            reporte.setDescripcion(reporteDetails.getDescripcion());
            reporte.setFechaRep(reporteDetails.getFechaRep());
            reporte.setVideollamada(reporteDetails.getVideollamada());
            return reporteRepository.save(reporte);
        }).orElseThrow(() -> new RuntimeException("Reporte not found"));
    }

    public void deleteReporte(Long id) {
        reporteRepository.deleteById(id);
    }

    public List<Reporte> getAllReportes() {
        return reporteRepository.findAll();
    }

}
