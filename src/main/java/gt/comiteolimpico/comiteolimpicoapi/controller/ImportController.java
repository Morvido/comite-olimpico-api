package gt.comiteolimpico.comiteolimpicoapi.controller;

import gt.comiteolimpico.comiteolimpicoapi.model.dto.AtletaRequestDTO;
import gt.comiteolimpico.comiteolimpicoapi.service.AtletaService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/import")
public class ImportController {

    private final AtletaService atletaService;

    public ImportController(AtletaService atletaService) {
        this.atletaService = atletaService;
    }

    @PostMapping("/atletas/csv")
    public ResponseEntity<String> importarAtletasCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Archivo CSV vacío");
        }

        List<AtletaRequestDTO> atletas = new ArrayList<>();

        try (var reader = new InputStreamReader(file.getInputStream());
             var csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {

            for (CSVRecord record : csvParser) {
                AtletaRequestDTO dto = new AtletaRequestDTO(
                        record.get("nombre"),
                        record.get("apellido"),
                        Integer.parseInt(record.get("edad")),
                        record.get("disciplina"),
                        record.get("departamento"),
                        record.get("nacionalidad"),
                        record.get("fechaIngreso") != null && !record.get("fechaIngreso").isBlank()
                                ? LocalDate.parse(record.get("fechaIngreso"))
                                : LocalDate.now()
                );
                atletas.add(dto);
            }

            // Crear todos los atletas
            for (AtletaRequestDTO dto : atletas) {
                atletaService.crear(dto);
            }

            return ResponseEntity.ok("Importados " + atletas.size() + " atletas correctamente.");

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al importar CSV: " + e.getMessage());
        }
    }
}