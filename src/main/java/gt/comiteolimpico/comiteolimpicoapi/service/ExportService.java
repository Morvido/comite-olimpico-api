package gt.comiteolimpico.comiteolimpicoapi.service;

import gt.comiteolimpico.comiteolimpicoapi.adapter.ExportAdapter;
import gt.comiteolimpico.comiteolimpicoapi.model.dto.AtletaResponseDTO;
import gt.comiteolimpico.comiteolimpicoapi.model.entity.Entrenamiento;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ExportService {

    private final AtletaService atletaService;
    private final EntrenamientoService entrenamientoService;
    private final ExportAdapter<AtletaResponseDTO> jsonAdapter;
    private final ExportAdapter<AtletaResponseDTO> csvAtletaAdapter;
    private final ExportAdapter<Entrenamiento> csvEntrenamientoAdapter;

    public ExportService(
            AtletaService atletaService,
            EntrenamientoService entrenamientoService,
            @Qualifier("jsonExportAdapter") ExportAdapter<AtletaResponseDTO> jsonAdapter,
            @Qualifier("csvExportAdapter") ExportAdapter<AtletaResponseDTO> csvAtletaAdapter,
            @Qualifier("csvExportAdapter") ExportAdapter<Entrenamiento> csvEntrenamientoAdapter) {

        this.atletaService = atletaService;
        this.entrenamientoService = entrenamientoService;
        this.jsonAdapter = jsonAdapter;
        this.csvAtletaAdapter = csvAtletaAdapter;
        this.csvEntrenamientoAdapter = csvEntrenamientoAdapter;
    }

    public byte[] exportarAtletasJson() throws Exception {
        List<AtletaResponseDTO> atletas = atletaService.listar(null)
                .stream()
                .map(AtletaResponseDTO::new)
                .toList();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        jsonAdapter.export(atletas, baos);
        return baos.toByteArray();
    }

    public byte[] exportarAtletasCsv() throws Exception {
        List<AtletaResponseDTO> atletas = atletaService.listar(null)
                .stream()
                .map(AtletaResponseDTO::new)
                .toList();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        csvAtletaAdapter.export(atletas, baos);
        return baos.toByteArray();
    }

    public byte[] exportarEntrenamientosCsv(Long atletaId) throws Exception {
        List<Entrenamiento> entrenamientos = entrenamientoService.listarPorAtleta(atletaId);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        csvEntrenamientoAdapter.export(entrenamientos, baos);
        return baos.toByteArray();
    }

    public HttpHeaders getJsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=atletas.json");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        return headers;
    }

    public HttpHeaders getCsvHeaders(String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");
        return headers;
    }
}