package med.voll.api.validations.consulta.agendamento;

import med.voll.api.exceptions.ValidacaoException;
import med.voll.api.records.ConsultaRecord;
import med.voll.api.repository.PacienteRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements IValidadorAgendamentoDeConsulta {

    private final PacienteRepository pacienteRepository;

    public ValidadorPacienteAtivo (PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public void validar (ConsultaRecord dados) {
        var pacienteEstaAtivo = pacienteRepository.findAtivoById(dados.idPaciente());
        if (!pacienteEstaAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada com paciente excluído");
        }
    }
}
