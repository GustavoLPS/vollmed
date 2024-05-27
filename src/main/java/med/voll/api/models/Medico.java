package med.voll.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.enums.EspecialidadeEnum;
import med.voll.api.records.MedicoRecord;
import med.voll.api.records.MedicoUpdateRecord;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;

    @Enumerated(EnumType.STRING)
    private EspecialidadeEnum especialidade;

    @Embedded
    private Endereco endereco;

    private Boolean ativo;

    public Medico(MedicoRecord medicoRecord) {
        this.nome = medicoRecord.nome();
        this.email = medicoRecord.email();
        this.telefone = medicoRecord.telefone();
        this.crm = medicoRecord.crm();
        this.especialidade = medicoRecord.especialidade();
        this.endereco = new Endereco(medicoRecord.endereco());
        this.ativo = true;
    }

    public void updateData(MedicoUpdateRecord medicoUpdateRecord) {
        if (medicoUpdateRecord.nome() != null) {
            this.nome = medicoUpdateRecord.nome();
        }

        if (medicoUpdateRecord.telefone() != null) {
            this.telefone = medicoUpdateRecord.telefone();
        }

        if (medicoUpdateRecord.endereco() != null) {
            this.endereco.updateData(medicoUpdateRecord.endereco());
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
