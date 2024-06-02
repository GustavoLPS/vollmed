package med.voll.api.validations.consulta.agendamento;

import med.voll.api.exceptions.ValidacaoException;
import med.voll.api.records.ConsultaRecord;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("IValidadorAgendamentoDeConsulta")
public class ValidadorHorarioAntecedencia implements IValidadorAgendamentoDeConsulta {

    public void validar(ConsultaRecord dados) {
        var dataConsulta = dados.data();
        var agora = LocalDateTime.now();
        var diferencaEmMinutos = Duration.between(agora, dataConsulta).toMinutes();

        if (diferencaEmMinutos < 30) {
            throw new ValidacaoException("Consulta deve ser agendada com antecedência mínima de 30 minutos");
        }
    }
}
