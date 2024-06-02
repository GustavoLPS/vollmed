package med.voll.api.records;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.enums.EspecialidadeEnum;

import java.time.LocalDateTime;

public record ConsultaRecord(
        Long idMedico,

        @NotNull
        Long idPaciente,

        @NotNull
        @Future
        LocalDateTime data,

        EspecialidadeEnum especialidade
) {
}
