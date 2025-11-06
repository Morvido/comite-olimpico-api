package gt.comiteolimpico.comiteolimpicoapi.service;

import gt.comiteolimpico.comiteolimpicoapi.model.entity.Atleta;
import gt.comiteolimpico.comiteolimpicoapi.model.entity.Entrenamiento;
import gt.comiteolimpico.comiteolimpicoapi.repository.EntrenamientoRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class EntrenamientoService {

    private final EntrenamientoRepository repository;
    private final AtletaService atletaService;

    public EntrenamientoService(EntrenamientoRepository repository, AtletaService atletaService) {
        this.repository = repository;
        this.atletaService = atletaService;
    }

    public Entrenamiento crear(Long atletaId, Entrenamiento entrenamiento) {
        Atleta atleta = atletaService.obtener(atletaId);
        entrenamiento.setAtleta(atleta);
        return repository.save(entrenamiento);
    }

    public List<Entrenamiento> listarPorAtleta(Long atletaId) {
        return repository.findByAtletaIdOrderByFechaAsc(atletaId);
    }

    public List<Entrenamiento> listarPorMes(Long atletaId, int year, int month) {
        LocalDate inicio = LocalDate.of(year, month, 1);
        LocalDate fin = inicio.withDayOfMonth(inicio.lengthOfMonth());
        return repository.findByAtletaIdAndFechaBetween(atletaId, inicio, fin);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}