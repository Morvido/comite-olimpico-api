package gt.comiteolimpico.comiteolimpicoapi.service;

import gt.comiteolimpico.comiteolimpicoapi.model.dto.AtletaRequestDTO;
import gt.comiteolimpico.comiteolimpicoapi.model.entity.Atleta;
import gt.comiteolimpico.comiteolimpicoapi.repository.AtletaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AtletaService {

    private final AtletaRepository repository;

    public AtletaService(AtletaRepository repository) {
        this.repository = repository;
    }

    public Atleta crear(AtletaRequestDTO dto) {
        Atleta atleta = new Atleta();
        atleta.setNombre(dto.nombre());
        atleta.setApellido(dto.apellido());
        atleta.setEdad(dto.edad());
        atleta.setDisciplina(dto.disciplina());
        atleta.setDepartamento(dto.departamento());
        atleta.setNacionalidad(dto.nacionalidad());
        atleta.setFechaIngreso(dto.fechaIngreso());
        return repository.save(atleta);
    }

    public Atleta obtener(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atleta no encontrado: " + id));
    }

    public List<Atleta> listar(String disciplina) {
        return disciplina == null || disciplina.isBlank()
                ? repository.findAll()
                : repository.findByDisciplinaIgnoreCase(disciplina);
    }

    public Atleta actualizar(Long id, AtletaRequestDTO dto) {
        Atleta atleta = obtener(id);
        atleta.setNombre(dto.nombre());
        atleta.setApellido(dto.apellido());
        atleta.setEdad(dto.edad());
        atleta.setDisciplina(dto.disciplina());
        atleta.setDepartamento(dto.departamento());
        atleta.setNacionalidad(dto.nacionalidad());
        if (dto.fechaIngreso() != null) {
            atleta.setFechaIngreso(dto.fechaIngreso());
        }
        return repository.save(atleta);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}