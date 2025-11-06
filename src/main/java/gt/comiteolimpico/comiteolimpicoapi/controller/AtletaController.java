package gt.comiteolimpico.comiteolimpicoapi.controller;

import gt.comiteolimpico.comiteolimpicoapi.model.dto.AtletaRequestDTO;
import gt.comiteolimpico.comiteolimpicoapi.model.dto.AtletaResponseDTO;
import gt.comiteolimpico.comiteolimpicoapi.model.entity.Atleta;
import gt.comiteolimpico.comiteolimpicoapi.service.AtletaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atletas")
public class AtletaController {

    private final AtletaService service;

    public AtletaController(AtletaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AtletaResponseDTO> crear(@Valid @RequestBody AtletaRequestDTO dto) {
        Atleta atleta = service.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AtletaResponseDTO(atleta));
    }

    @GetMapping("/{id}")
    public AtletaResponseDTO obtener(@PathVariable Long id) {
        return new AtletaResponseDTO(service.obtener(id));
    }

    @GetMapping
    public List<AtletaResponseDTO> listar(@RequestParam(required = false) String disciplina) {
        return service.listar(disciplina).stream()
                .map(AtletaResponseDTO::new)
                .toList();
    }

    @PutMapping("/{id}")
    public AtletaResponseDTO actualizar(@PathVariable Long id, @Valid @RequestBody AtletaRequestDTO dto) {
        return new AtletaResponseDTO(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}