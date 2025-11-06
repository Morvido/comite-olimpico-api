package gt.comiteolimpico.comiteolimpicoapi.repository;

import gt.comiteolimpico.comiteolimpicoapi.model.entity.Atleta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AtletaRepository extends JpaRepository<Atleta, Long> {
    List<Atleta> findByDisciplinaIgnoreCase(String disciplina);
}