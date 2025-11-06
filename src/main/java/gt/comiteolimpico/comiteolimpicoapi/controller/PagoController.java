package gt.comiteolimpico.comiteolimpicoapi.controller;

import gt.comiteolimpico.comiteolimpicoapi.model.entity.Pago;
import gt.comiteolimpico.comiteolimpicoapi.service.PagoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final PagoService service;

    public PagoController(PagoService service) {
        this.service = service;
    }

    @PostMapping("/atleta/{atletaId}")
    public Pago registrar(@PathVariable Long atletaId,
                          @RequestParam int year,
                          @RequestParam int month,
                          @RequestParam double monto,
                          @RequestParam String descripcion) {
        YearMonth mes = YearMonth.of(year, month);
        return service.registrar(atletaId, mes, monto, descripcion);
    }

    @GetMapping("/atleta/{atletaId}")
    public List<Pago> listarPorAtleta(@PathVariable Long atletaId) {
        return service.listarPorAtleta(atletaId);
    }

    @GetMapping("/atleta/{atletaId}/mes/{year}/{month}")
    public List<Pago> listarPorMes(@PathVariable Long atletaId,
                                   @PathVariable int year,
                                   @PathVariable int month) {
        return service.listarPorMes(atletaId, year, month);
    }
}