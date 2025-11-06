package gt.comiteolimpico.comiteolimpicoapi.controller;

import gt.comiteolimpico.comiteolimpicoapi.model.entity.Entrenamiento;
import gt.comiteolimpico.comiteolimpicoapi.service.EntrenamientoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entrenamientos")
public class EntrenamientoController {

    private final EntrenamientoService service;

    public EntrenamientoController(EntrenamientoService service) {
        this.service = service;
    }

    @PostMapping("/atleta/{atletaId}")
    public Entrenamiento crear(@PathVariable Long atletaId, @RequestBody Entrenamiento entrenamiento) {
        return service.crear(atletaId, entrenamiento);
    }

    @GetMapping("/atleta/{atletaId}")
    public List<Entrenamiento> listarPorAtleta(@PathVariable Long atletaId) {
        return service.listarPorAtleta(atletaId);
    }

    @GetMapping("/atleta/{atletaId}/mes/{year}/{month}")
    public List<Entrenamiento> listarPorMes(@PathVariable Long atletaId,
                                            @PathVariable int year,
                                            @PathVariable int month) {
        return service.listarPorMes(atletaId, year, month);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}