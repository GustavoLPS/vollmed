package med.voll.api.services;

import med.voll.api.exceptions.ValidacaoException;
import med.voll.api.models.Consulta;
import med.voll.api.models.Medico;
import med.voll.api.records.ConsultaCancelRecord;
import med.voll.api.records.ConsultaRecord;
import med.voll.api.records.ConsultaReturnRecord;
import med.voll.api.repository.ConsultaRepository;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;
import med.voll.api.validations.consulta.agendamento.IValidadorAgendamentoDeConsulta;
import med.voll.api.validations.consulta.cancelamento.IValidadorCancelamentoDeConsulta;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultasService {
    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final List<IValidadorAgendamentoDeConsulta> validadorAgendamentoDeConsulta;
    private final List<IValidadorCancelamentoDeConsulta> validadoresCancelamento;

    public AgendaDeConsultasService(ConsultaRepository consultaRepository, PacienteRepository pacienteRepository, MedicoRepository medicoRepository, List<IValidadorAgendamentoDeConsulta> validadorAgendamentoDeConsulta, List<IValidadorCancelamentoDeConsulta> validadoresCancelamento) {
        this.consultaRepository = consultaRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
        this.validadorAgendamentoDeConsulta = validadorAgendamentoDeConsulta;
        this.validadoresCancelamento = validadoresCancelamento;
    }

    public ConsultaReturnRecord agendar(ConsultaRecord dados) {
        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado não existe");
        }

        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Id do médico informado não existe");
        }

        validadorAgendamentoDeConsulta.forEach(v -> v.validar(dados));
        
        var paciente = pacienteRepository.findById(dados.idPaciente()).get();
        var medico = escolherMedico(dados);
        if (medico == null) {
            throw new ValidacaoException("Não existe médico disponível nesta nada");
        }

        var consulta = new Consulta(null, medico, paciente, dados.data(), null);
        return new ConsultaReturnRecord(consultaRepository.save(consulta));
    }

    public void cancelar(ConsultaCancelRecord dados) {
        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe!");
        }

        validadoresCancelamento.forEach(v -> v.validar(dados));

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }

    private Medico escolherMedico(ConsultaRecord dados) {
        if (dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatória quando médico não for escolhido!");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }
}
