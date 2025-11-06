package gt.comiteolimpico.comiteolimpicoapi.service;

import gt.comiteolimpico.comiteolimpicoapi.model.dto.EstadisticaResponseDTO;
import gt.comiteolimpico.comiteolimpicoapi.model.entity.Entrenamiento;
import gt.comiteolimpico.comiteolimpicoapi.repository.EntrenamientoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstadisticaService {

    private final EntrenamientoRepository repository;
    private final AtletaService atletaService;

    public EstadisticaService(EntrenamientoRepository repository, AtletaService atletaService) {
        this.repository = repository;
        this.atletaService = atletaService;
    }

    public EstadisticaResponseDTO calcularEstadisticas(Long atletaId) {
        atletaService.obtener(atletaId);

        List<Entrenamiento> entrenamientos = repository.findByAtletaIdOrderByFechaAsc(atletaId);
        if (entrenamientos.isEmpty()) {
            return new EstadisticaResponseDTO(
                    0.0, 0.0,
                    List.of(),
                    new EstadisticaResponseDTO.ComparacionRendimiento(0.0, 0.0)
            );
        }

        double promedio = entrenamientos.stream()
                .mapToDouble(Entrenamiento::getValor)
                .average()
                .orElse(0.0);

        double mejorMarca = entrenamientos.stream()
                .mapToDouble(Entrenamiento::getValor)
                .max()
                .orElse(0.0);

        List<EstadisticaResponseDTO.PuntoEvolucion> evolucion = entrenamientos.stream()
                .map(e -> new EstadisticaResponseDTO.PuntoEvolucion(e.getFecha(), e.getValor()))
                .sorted(Comparator.comparing(EstadisticaResponseDTO.PuntoEvolucion::fecha))
                .collect(Collectors.toList());

        double nacional = entrenamientos.stream()
                .filter(e -> !e.isInternacional())
                .mapToDouble(Entrenamiento::getValor)
                .average()
                .orElse(0.0);

        double internacional = entrenamientos.stream()
                .filter(Entrenamiento::isInternacional)
                .mapToDouble(Entrenamiento::getValor)
                .average()
                .orElse(0.0);

        var comparacion = new EstadisticaResponseDTO.ComparacionRendimiento(nacional, internacional);

        return new EstadisticaResponseDTO(promedio, mejorMarca, evolucion, comparacion);
    }

    public EstadisticaResponseDTO calcularEstadisticasPorRango(
            Long atletaId, LocalDate desde, LocalDate hasta) {

        atletaService.obtener(atletaId);

        LocalDate inicio = desde != null ? desde : LocalDate.now().minusYears(1);
        LocalDate fin = hasta != null ? hasta : LocalDate.now();

        List<Entrenamiento> entrenamientos = repository.findByAtletaIdAndFechaBetween(atletaId, inicio, fin);

        return calcularDesdeLista(entrenamientos);
    }

    private EstadisticaResponseDTO calcularDesdeLista(List<Entrenamiento> entrenamientos) {
        if (entrenamientos.isEmpty()) {
            return new EstadisticaResponseDTO(
                    0.0, 0.0,
                    List.of(),
                    new EstadisticaResponseDTO.ComparacionRendimiento(0.0, 0.0)
            );
        }

        double promedio = entrenamientos.stream()
                .mapToDouble(Entrenamiento::getValor)
                .average()
                .orElse(0.0);

        double mejorMarca = entrenamientos.stream()
                .mapToDouble(Entrenamiento::getValor)
                .max()
                .orElse(0.0);

        List<EstadisticaResponseDTO.PuntoEvolucion> evolucion = entrenamientos.stream()
                .map(e -> new EstadisticaResponseDTO.PuntoEvolucion(e.getFecha(), e.getValor()))
                .sorted(Comparator.comparing(EstadisticaResponseDTO.PuntoEvolucion::fecha))
                .collect(Collectors.toList());

        double nacional = entrenamientos.stream()
                .filter(e -> !e.isInternacional())
                .mapToDouble(Entrenamiento::getValor)
                .average()
                .orElse(0.0);

        double internacional = entrenamientos.stream()
                .filter(Entrenamiento::isInternacional)
                .mapToDouble(Entrenamiento::getValor)
                .average()
                .orElse(0.0);

        var comparacion = new EstadisticaResponseDTO.ComparacionRendimiento(nacional, internacional);

        return new EstadisticaResponseDTO(promedio, mejorMarca, evolucion, comparacion);
    }
}