package med.voll.api.records;

import med.voll.api.enums.EspecialidadeEnum;
import med.voll.api.models.Consulta;

import java.time.LocalDateTime;

public record ConsultaReturnRecord(
        Long idMedico,
        Long idPaciente,
        LocalDateTime data,
        EspecialidadeEnum especialidade
) {
    public ConsultaReturnRecord(Consulta consulta) {
        this(consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData(), consulta.getMedico().getEspecialidade());
    }
}
