package gt.comiteolimpico.comiteolimpicoapi.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public record AtletaRequestDTO(
        @NotBlank String nombre,
        @NotBlank String apellido,
        @Min(15) int edad,
        @NotBlank String disciplina,
        @NotBlank String departamento,
        @NotBlank String nacionalidad,
        LocalDate fechaIngreso
) {}