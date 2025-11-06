package gt.comiteolimpico.comiteolimpicoapi.model.dto;

import gt.comiteolimpico.comiteolimpicoapi.model.entity.Atleta;
import java.time.LocalDate;

public record AtletaResponseDTO(
        Long id,
        String nombreCompleto,
        int edad,
        String disciplina,
        String departamento,
        String nacionalidad,
        LocalDate fechaIngreso
) {
    public AtletaResponseDTO(Atleta a) {
        this(a.getId(), a.getNombre() + " " + a.getApellido(), a.getEdad(),
                a.getDisciplina(), a.getDepartamento(), a.getNacionalidad(), a.getFechaIngreso());
    }
}