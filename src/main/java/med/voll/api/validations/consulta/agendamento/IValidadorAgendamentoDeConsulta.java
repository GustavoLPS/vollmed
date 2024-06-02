package med.voll.api.validations.consulta.agendamento;

import med.voll.api.records.ConsultaRecord;

public interface IValidadorAgendamentoDeConsulta {
    void validar(ConsultaRecord consultaRecord);
}
