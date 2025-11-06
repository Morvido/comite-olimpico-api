package gt.comiteolimpico.comiteolimpicoapi.service;

import gt.comiteolimpico.comiteolimpicoapi.model.entity.Atleta;
import gt.comiteolimpico.comiteolimpicoapi.model.entity.Pago;
import gt.comiteolimpico.comiteolimpicoapi.repository.PagoRepository;
import org.springframework.stereotype.Service;
import java.time.YearMonth;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Service
public class PagoService {

    private final PagoRepository repository;
    private final AtletaService atletaService;

    public PagoService(PagoRepository repository, AtletaService atletaService) {
        this.repository = repository;
        this.atletaService = atletaService;
    }

    @Transactional
    public Pago registrar(Long atletaId, YearMonth mes, double monto, String descripcion) {
        Atleta atleta = atletaService.obtener(atletaId);
        Pago pago = new Pago();
        pago.setAtleta(atleta);
        pago.setMes(mes);
        pago.setMonto(monto);
        pago.setDescripcion(descripcion);
        return repository.save(pago);
    }

    public List<Pago> listarPorAtleta(Long atletaId) {
        return repository.findByAtletaId(atletaId);
    }

    public List<Pago> listarPorMes(Long atletaId, int year, int month) {
        YearMonth mes = YearMonth.of(year, month);
        return repository.findByAtletaIdAndMes(atletaId, mes);
    }
}