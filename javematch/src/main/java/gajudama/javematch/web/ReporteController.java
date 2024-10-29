package gajudama.javematch.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import gajudama.javematch.logic.ReporteLogic;
import gajudama.javematch.model.Reporte;

import java.util.List;

@RestController
@RequestMapping("/api/reporte")
public class ReporteController {

    @Autowired
    private ReporteLogic reporteLogic;

    @PostMapping
    public ResponseEntity<Reporte> createReporte(@RequestBody Reporte reporte) {
        Reporte newReporte = reporteLogic.createReporte(reporte);
        return new ResponseEntity<>(newReporte, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reporte> getReporteById(@PathVariable Long id) {
        return reporteLogic.getReporteById(id)
            .map(reporte -> new ResponseEntity<>(reporte, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reporte> updateReporte(@PathVariable Long id, @RequestBody Reporte reporteDetails) {
        Reporte updatedReporte = reporteLogic.updateReporte(id, reporteDetails);
        return new ResponseEntity<>(updatedReporte, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReporte(@PathVariable Long id) {
        reporteLogic.deleteReporte(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Reporte>> getAllReportes() {
        List<Reporte> reportes = reporteLogic.getAllReportes();
        return new ResponseEntity<>(reportes, HttpStatus.OK);
    }

    // Endpoint específico para crear un reporte
    @PostMapping("/createReport")
    public ResponseEntity<Reporte> createReport(@RequestParam Long autorId, @RequestParam Long reportadoId, 
                                                @RequestParam String tipo, @RequestParam String descripcion, 
                                                @RequestParam Long videollamadaId) {
        Reporte reporte = reporteLogic.createReport(autorId, reportadoId, tipo, descripcion, videollamadaId);
        return new ResponseEntity<>(reporte, HttpStatus.CREATED);
    }
}
