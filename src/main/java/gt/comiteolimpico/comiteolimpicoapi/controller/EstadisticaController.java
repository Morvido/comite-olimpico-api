package gt.comiteolimpico.comiteolimpicoapi.controller;

import gt.comiteolimpico.comiteolimpicoapi.model.dto.EstadisticaResponseDTO;
import gt.comiteolimpico.comiteolimpicoapi.service.EstadisticaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/atletas/{atletaId}/estadisticas")
public class EstadisticaController {

    private final EstadisticaService service;

    public EstadisticaController(EstadisticaService service) {
        this.service = service;
    }

    /**
     * Obtiene todas las estadísticas del atleta (promedio, mejor marca,
     * evolución y comparación nacional vs internacional).
     * Usa todos los entrenamientos registrados.
     */
    @GetMapping
    public ResponseEntity<EstadisticaResponseDTO> obtenerTodas(@PathVariable Long atletaId) {
        EstadisticaResponseDTO estadisticas = service.calcularEstadisticas(atletaId);
        return ResponseEntity.ok(estadisticas);
    }

    /**
     * Obtiene estadísticas filtradas por rango de fechas.
     * Parámetros opcionales:
     * - desde: fecha inicial (formato: yyyy-MM-dd)
     * - hasta: fecha final (formato: yyyy-MM-dd)
     * Si no se envían, usa el último año.
     */
    @GetMapping("/rango")
    public ResponseEntity<EstadisticaResponseDTO> obtenerPorRango(
            @PathVariable Long atletaId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {

        EstadisticaResponseDTO estadisticas = service.calcularEstadisticasPorRango(atletaId, desde, hasta);
        return ResponseEntity.ok(estadisticas);
    }
}