package med.voll.api.services;

import med.voll.api.models.Paciente;
import med.voll.api.records.PacienteUpdateRecord;
import med.voll.api.records.PacienteUpdateReturnRecord;
import med.voll.api.repository.PacienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {
    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public PacienteUpdateReturnRecord save(Paciente paciente) {
        return new PacienteUpdateReturnRecord(pacienteRepository.save(paciente));
    }

    public PacienteUpdateReturnRecord findById(Long id) {
        return new PacienteUpdateReturnRecord(pacienteRepository.getReferenceById(id));
    }

    public Page<Paciente> findAll(Pageable pageable) {
        return pacienteRepository.findAllByAtivoTrue(pageable);
    }

    public PacienteUpdateReturnRecord update(PacienteUpdateRecord pacienteRecord) {
        Paciente paciente = pacienteRepository.getReferenceById(pacienteRecord.id());
        paciente.updateData(pacienteRecord);
        return new PacienteUpdateReturnRecord(pacienteRepository.save(paciente));
    }

    public void delete(Long id) {
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.excluir();
        pacienteRepository.save(paciente);
    }
}
