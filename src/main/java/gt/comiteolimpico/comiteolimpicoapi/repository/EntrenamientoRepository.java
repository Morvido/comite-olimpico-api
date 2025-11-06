package gt.comiteolimpico.comiteolimpicoapi.repository;

import gt.comiteolimpico.comiteolimpicoapi.model.entity.Entrenamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface EntrenamientoRepository extends JpaRepository<Entrenamiento, Long> {

    List<Entrenamiento> findByAtletaIdOrderByFechaAsc(Long atletaId);

    @Query("SELECT e FROM Entrenamiento e WHERE e.atleta.id = :atletaId AND e.fecha BETWEEN :inicio AND :fin")
    List<Entrenamiento> findByAtletaIdAndFechaBetween(Long atletaId, LocalDate inicio, LocalDate fin);
}