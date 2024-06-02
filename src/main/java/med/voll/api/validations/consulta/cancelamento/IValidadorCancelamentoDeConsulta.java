package med.voll.api.validations.consulta.cancelamento;

import med.voll.api.records.ConsultaCancelRecord;

public interface IValidadorCancelamentoDeConsulta {
    void validar(ConsultaCancelRecord dados);
}
