package med.voll.api.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.records.EnderecoRecord;

@Embeddable
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    private String logradouro;
    private String bairro;
    private String cep;
    private String cidade;
    private String uf;
    private String numero;
    private String complemento;

    public Endereco(EnderecoRecord enderecoRecord) {
        this.logradouro = enderecoRecord.logradouro();
        this.bairro = enderecoRecord.bairro();
        this.cep = enderecoRecord.cep();
        this.cidade = enderecoRecord.cidade();
        this.uf = enderecoRecord.uf();
        this.numero = enderecoRecord.numero();
        this.complemento = enderecoRecord.complemento();
    }

    public void updateData(EnderecoRecord endereco) {
        if (endereco.logradouro() != null) {
            this.logradouro = endereco.logradouro();
        }
        if (endereco.bairro() != null) {
            this.bairro = endereco.bairro();
        }
        if (endereco.cep() != null) {
            this.cep = endereco.cep();
        }
        if (endereco.cidade() != null) {
            this.cidade = endereco.cidade();
        }
        if (endereco.uf() != null) {
            this.uf = endereco.uf();
        }
        if (endereco.numero() != null) {
            this.numero = endereco.numero();
        }
        if (endereco.complemento() != null) {
            this.complemento = endereco.complemento();
        }
    }
}
