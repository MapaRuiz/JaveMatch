package gajudama.javematch.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import gajudama.javematch.accesoDatos.ReporteRepository;
import gajudama.javematch.model.Reporte;

@Service
public class ReporteLogic {
    private final ReporteRepository reporteRepository;

    public ReporteLogic(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }

    public List<Reporte> getAllReportes() {
        return reporteRepository.findAll();
    }

    public Optional<Reporte> getReporteById(Long id) {
        return reporteRepository.findById(id);
    }

    public Reporte createReporte(Reporte reporte) {
        return reporteRepository.save(reporte);
    }

    public Reporte updateReporte(Long id, Reporte updatedReporte) {
        return reporteRepository.findById(id).map(reporte -> {
            reporte.setAutor(updatedReporte.getAutor());
            reporte.setReportado(updatedReporte.getReportado());
            reporte.setTipo(updatedReporte.getTipo());
            reporte.setDescripcion(updatedReporte.getDescripcion());
            reporte.setFechaRep(updatedReporte.getFechaRep());
            reporte.setVideollamada(updatedReporte.getVideollamada());
            return reporteRepository.save(reporte);
        }).orElseGet(() -> {
            updatedReporte.setId(id);
            return reporteRepository.save(updatedReporte);
        });
    }

    public void deleteReporte(Long id) {
        reporteRepository.deleteById(id);
    }

}
