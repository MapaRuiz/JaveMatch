package gajudama.javematch.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gajudama.javematch.accesoDatos.ReporteRepository;
import gajudama.javematch.model.Reporte;
import gajudama.javematch.model.Usuario;
import gajudama.javematch.model.Videollamada;
import jakarta.transaction.Transactional;

@Service
public class ReporteLogic {
    @Autowired
    private ReporteRepository reporteRepository;

    @Transactional
    public Reporte createReporte(Reporte reporte) {
        return reporteRepository.save(reporte);
    }

    public Optional<Reporte> getReporteById(Long id) {
        return reporteRepository.findById(id);
    }

    @Transactional
    public Reporte updateReporte(Long id, Reporte reporteDetails) {
        return reporteRepository.findById(id).map(reporte -> {
            reporte.setTipo(reporteDetails.getTipo());
            reporte.setDescripcion(reporteDetails.getDescripcion());
            reporte.setFechaRep(reporteDetails.getFechaRep());
            reporte.setVideollamada_Reports(reporteDetails.getVideollamada_Reports());
            return reporteRepository.save(reporte);
        }).orElseThrow(() -> new RuntimeException("Reporte not found"));
    }

    @Transactional
    public void deleteReporte(Long id) {
        reporteRepository.deleteById(id);
    }

    public List<Reporte> getAllReportes() {
        return reporteRepository.findAll();
    }

    @Autowired
    private UsuarioLogic usuarioLogic;
    @Autowired
    private VideollamadaLogic videollamadaLogic;

    @Transactional
    public Reporte createReport(Long autorId, Long reportadoId, String tipo, String descripcion, Long videollamadaId) {
        Usuario autor = usuarioLogic.getUsuarioById(autorId)
            .orElseThrow(() -> new RuntimeException("Author not found"));
        Usuario reportado = usuarioLogic.getUsuarioById(reportadoId)
            .orElseThrow(() -> new RuntimeException("Reported user not found"));
        Videollamada videollamada = videollamadaLogic.getVideollamadaById(videollamadaId)
            .orElseThrow(() -> new RuntimeException("Videollamada not found"));

        Reporte reporte = new Reporte();
        reporte.setAutor(autor);
        reporte.setReportado(reportado);
        reporte.setTipo(tipo);
        reporte.setDescripcion(descripcion);
        reporte.setVideollamada_Reports(videollamada);
        return reporteRepository.save(reporte);
    }
}
