package gt.comiteolimpico.comiteolimpicoapi.model.dto;

import java.time.LocalDate;
import java.util.List;

public record EstadisticaResponseDTO(
        double promedioRendimiento,
        double mejorMarca,
        List<PuntoEvolucion> evolucion,
        ComparacionRendimiento comparacion
) {
    public record PuntoEvolucion(LocalDate fecha, double valor) {}
    public record ComparacionRendimiento(double nacional, double internacional) {}
}