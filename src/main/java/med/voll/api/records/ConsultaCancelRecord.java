package med.voll.api.records;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import med.voll.api.enums.MotivoCancelamentoEnum;

public record ConsultaCancelRecord(
        @NotNull
        Long idConsulta,

        @NotNull
        @Enumerated(EnumType.STRING)
        MotivoCancelamentoEnum motivo
) {
}
