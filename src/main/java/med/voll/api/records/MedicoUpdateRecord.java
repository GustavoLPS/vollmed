package med.voll.api.records;

import jakarta.validation.constraints.NotNull;

public record MedicoUpdateRecord(
        @NotNull
        Long id,
        String nome,
        String telefone,
        EnderecoRecord endereco
) { }
