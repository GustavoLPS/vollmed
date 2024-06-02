package med.voll.api.validations.consulta.agendamento;

import med.voll.api.exceptions.ValidacaoException;
import med.voll.api.records.ConsultaRecord;
import med.voll.api.repository.ConsultaRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements IValidadorAgendamentoDeConsulta {

    private final ConsultaRepository consultaRepository;

    public ValidadorMedicoComOutraConsultaNoMesmoHorario(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public void validar(ConsultaRecord dados) {
        var medicoPossuiOutraConsultaNoMesmoHorario = consultaRepository.existsByMedicoIdAndData(dados.idMedico(), dados.data());
        if (medicoPossuiOutraConsultaNoMesmoHorario) {
            throw new ValidacaoException("Médico já possui outra consulta agendada nesse mesmo horário");
        }
    }
}
