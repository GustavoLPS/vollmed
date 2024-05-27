package med.voll.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.records.PacienteRecord;
import med.voll.api.records.PacienteUpdateRecord;

@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String cpf;
    private String telefone;

    @Embedded
    private Endereco endereco;

    private Boolean ativo;

    public Paciente(PacienteRecord pacienteRecord) {
        this.nome = pacienteRecord.nome();
        this.email = pacienteRecord.email();
        this.cpf = pacienteRecord.cpf();
        this.telefone = pacienteRecord.telefone();
        this.endereco = new Endereco(pacienteRecord.endereco());
        this.ativo = true;
    }

    public void updateData(PacienteUpdateRecord pacienteRecord) {
        if (pacienteRecord.nome() != null) {
            this.nome = pacienteRecord.nome();
        }
        if (pacienteRecord.telefone() != null) {
            this.telefone = pacienteRecord.telefone();
        }
        if (pacienteRecord.endereco() != null) {
            this.endereco.updateData(pacienteRecord.endereco());
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
