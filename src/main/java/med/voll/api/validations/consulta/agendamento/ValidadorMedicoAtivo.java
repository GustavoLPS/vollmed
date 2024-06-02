package med.voll.api.validations.consulta.agendamento;

import med.voll.api.exceptions.ValidacaoException;
import med.voll.api.records.ConsultaRecord;
import med.voll.api.repository.MedicoRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements IValidadorAgendamentoDeConsulta {

    private final MedicoRepository medicoRepository;

    public ValidadorMedicoAtivo(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public void validar(ConsultaRecord dados) {
        if (dados.idMedico() == null) {
            return;
        }

        var medicoAtivo = medicoRepository.findAtivoById(dados.idMedico());
        if (!medicoAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada com médico excluído");
        }
    }
}
