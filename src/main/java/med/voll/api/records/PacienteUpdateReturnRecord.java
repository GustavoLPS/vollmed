package med.voll.api.records;

import med.voll.api.models.Endereco;
import med.voll.api.models.Paciente;

public record PacienteUpdateReturnRecord(
        Long id,
        String nome,
        String email,
        String cpf,
        String telefone,
        Endereco endereco
) {
    public PacienteUpdateReturnRecord(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf(), paciente.getTelefone(), paciente.getEndereco());
    }
}
