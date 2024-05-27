package med.voll.api.records;

import med.voll.api.models.Paciente;

public record PacienteListRecord(
        Long id,
        String nome,
        String email,
        String cpf
) {
    public PacienteListRecord(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf());
    }
}
