package gt.comiteolimpico.comiteolimpicoapi.repository;

import gt.comiteolimpico.comiteolimpicoapi.model.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.YearMonth;
import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByAtletaId(Long atletaId);
    List<Pago> findByAtletaIdAndMes(Long atletaId, YearMonth mes);
}