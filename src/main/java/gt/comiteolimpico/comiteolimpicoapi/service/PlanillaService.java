package gt.comiteolimpico.comiteolimpicoapi.service;

import gt.comiteolimpico.comiteolimpicoapi.model.entity.Atleta;
import gt.comiteolimpico.comiteolimpicoapi.model.entity.Entrenamiento;
import org.springframework.stereotype.Service;
import java.time.YearMonth;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PlanillaService {

    private final AtletaService atletaService;
    private final EntrenamientoService entrenamientoService;
    private final PagoService pagoService;

    public PlanillaService(AtletaService atletaService, EntrenamientoService entrenamientoService, PagoService pagoService) {
        this.atletaService = atletaService;
        this.entrenamientoService = entrenamientoService;
        this.pagoService = pagoService;
    }

    @Transactional
    public double calcularPlanilla(Long atletaId, int year, int month) {
        Atleta atleta = atletaService.obtener(atletaId);
        YearMonth mes = YearMonth.of(year, month);

        double total = 3000.0;

        List<Entrenamiento> entrenamientos = entrenamientoService.listarPorMes(atletaId, year, month);
        if (entrenamientos == null) entrenamientos = List.of();

        boolean internacional = entrenamientos.stream()
                .anyMatch(e -> e != null && e.isInternacional() && e.getPais() != null && !e.getPais().trim().isEmpty());
        if (internacional) total += 1000.0;

        double mejorAnterior = obtenerMejorMarcaAnterior(atletaId, mes);
        double mejorActual = entrenamientos.stream()
                .filter(e -> e != null && "Competencia".equals(e.getTipo()))
                .mapToDouble(Entrenamiento::getValor)
                .max().orElse(0.0);

        if (mejorActual > mejorAnterior && mejorActual > 0) total += 500.0;

        String descripcion = "Planilla " + mes +
                (internacional ? " + Internacional" : "") +
                (mejorActual > mejorAnterior ? " + Mejora" : "");

        pagoService.registrar(atletaId, mes, total, descripcion);

        return total;
    }

    private double obtenerMejorMarcaAnterior(Long atletaId, YearMonth mesActual) {
        YearMonth mesAnterior = mesActual.minusMonths(1);
        List<Entrenamiento> prev = entrenamientoService.listarPorMes(atletaId, mesAnterior.getYear(), mesAnterior.getMonthValue());
        if (prev == null) return 0.0;

        return prev.stream()
                .filter(e -> e != null && "Competencia".equals(e.getTipo()))
                .mapToDouble(Entrenamiento::getValor)
                .max().orElse(0.0);
    }
}