package gt.comiteolimpico.comiteolimpicoapi.controller;

import gt.comiteolimpico.comiteolimpicoapi.service.ExportService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/export")
public class ExportController {

    private final ExportService service;

    public ExportController(ExportService service) {
        this.service = service;
    }

    @GetMapping("/atletas/json")
    public ResponseEntity<ByteArrayResource> exportarAtletasJson() throws Exception {
        byte[] data = service.exportarAtletasJson();
        return ResponseEntity.ok()
                .headers(service.getJsonHeaders())
                .body(new ByteArrayResource(data));
    }

    @GetMapping("/atletas/csv")
    public ResponseEntity<ByteArrayResource> exportarAtletasCsv() throws Exception {
        byte[] data = service.exportarAtletasCsv();
        return ResponseEntity.ok()
                .headers(service.getCsvHeaders("atletas.csv"))
                .body(new ByteArrayResource(data));
    }

    @GetMapping("/entrenamientos/csv")
    public ResponseEntity<ByteArrayResource> exportarEntrenamientosCsv(@RequestParam Long atletaId) throws Exception {
        byte[] data = service.exportarEntrenamientosCsv(atletaId);
        return ResponseEntity.ok()
                .headers(service.getCsvHeaders("entrenamientos_atleta_" + atletaId + ".csv"))
                .body(new ByteArrayResource(data));
    }
}