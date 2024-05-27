package med.voll.api.services;

import med.voll.api.models.Paciente;
import med.voll.api.records.PacienteUpdateRecord;
import med.voll.api.repository.PacienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class PacienteService {
    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente save(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public Page<Paciente> findAll(Pageable pageable) {
        return pacienteRepository.findAllByAtivoTrue(pageable);
    }

    public void update(PacienteUpdateRecord pacienteRecord) {
        Paciente paciente = pacienteRepository.getReferenceById(pacienteRecord.id());
        paciente.updateData(pacienteRecord);
    }

    public void delete(Long id) {
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.excluir();
        pacienteRepository.save(paciente);
    }
}
