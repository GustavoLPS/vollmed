package med.voll.api.validations.consulta.cancelamento;

import med.voll.api.exceptions.ValidacaoException;
import med.voll.api.records.ConsultaCancelRecord;
import med.voll.api.repository.ConsultaRepository;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("IValidadorCancelamentoDeConsulta")
public class ValidadorHorarioAntecedencia implements IValidadorCancelamentoDeConsulta {

    private final ConsultaRepository consultaRepository;

    public ValidadorHorarioAntecedencia(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    @Override
    public void validar(ConsultaCancelRecord dados) {
        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        var agora = LocalDateTime.now();
        var diferencaEmHoras = Duration.between(agora, consulta.getData()).toHours();

        if (diferencaEmHoras < 24) {
            throw new ValidacaoException("Consulta somente pode ser cancelada com antecedência mínima de 24h!");
        }
    }
}
