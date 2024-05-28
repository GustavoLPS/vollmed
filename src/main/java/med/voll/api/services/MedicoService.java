package med.voll.api.services;

import med.voll.api.models.Medico;
import med.voll.api.records.MedicoUpdateRecord;
import med.voll.api.records.MedicoUpdateReturnRecord;
import med.voll.api.repository.MedicoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {
    private final MedicoRepository medicoRepository;
    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public MedicoUpdateReturnRecord save(Medico medico) {
        return new MedicoUpdateReturnRecord(medicoRepository.save(medico));
    }

    public MedicoUpdateReturnRecord findById(Long id) {
        return new MedicoUpdateReturnRecord(medicoRepository.getReferenceById(id));
    }

    public Page<Medico> findAll(Pageable pageable) {
        return medicoRepository.findAllByAtivoTrue(pageable);
    }

    public MedicoUpdateReturnRecord update(MedicoUpdateRecord medicoRecord) {
        Medico medico = medicoRepository.getReferenceById(medicoRecord.id());
        medico.updateData(medicoRecord);
        return new MedicoUpdateReturnRecord(medicoRepository.save(medico));
    }

    public void delete(Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.excluir();
        medicoRepository.save(medico);
    }
}
