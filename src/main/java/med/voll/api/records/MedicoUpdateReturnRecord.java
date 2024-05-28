package med.voll.api.records;

import med.voll.api.enums.EspecialidadeEnum;
import med.voll.api.models.Endereco;
import med.voll.api.models.Medico;

public record MedicoUpdateReturnRecord(
        Long id,
        String nome,
        String email,
        String telefone,
        String crm,
        EspecialidadeEnum especialidade,
        Endereco endereco
) {
    public MedicoUpdateReturnRecord(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getTelefone(), medico.getCrm(), medico.getEspecialidade(), medico.getEndereco());
    }
}
