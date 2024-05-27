package med.voll.api.records;

import jakarta.validation.constraints.NotNull;

public record PacienteUpdateRecord(
        @NotNull
        Long id,
        String nome,
        String telefone,
        EnderecoRecord endereco
) { }
