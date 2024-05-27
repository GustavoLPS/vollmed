package med.voll.api.records;

import med.voll.api.enums.EspecialidadeEnum;
import med.voll.api.models.Medico;

public record MedicoListRecord(
        Long id,
        String nome,
        String email,
        String crm,
        EspecialidadeEnum especialidade
) {
    public MedicoListRecord(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }
}
