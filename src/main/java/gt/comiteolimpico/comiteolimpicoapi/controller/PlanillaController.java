package gt.comiteolimpico.comiteolimpicoapi.controller;

import gt.comiteolimpico.comiteolimpicoapi.service.PlanillaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/planilla")
public class PlanillaController {

    private final PlanillaService service;

    public PlanillaController(PlanillaService service) {
        this.service = service;
    }

    @PostMapping("/calcular/{atletaId}/{year}/{month}")
    public ResponseEntity<Double> calcular(@PathVariable Long atletaId,
                                           @PathVariable int year,
                                           @PathVariable int month) {
        double total = service.calcularPlanilla(atletaId, year, month);
        return ResponseEntity.ok(total);
    }
}