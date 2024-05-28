package med.voll.api.records;

import jakarta.validation.constraints.NotBlank;

public record UsuarioRecord(
        @NotBlank
        String login,

        @NotBlank
        String senha
) { }
