package med.voll.api.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.models.Medico;
import med.voll.api.records.MedicoListRecord;
import med.voll.api.records.MedicoRecord;
import med.voll.api.records.MedicoUpdateRecord;
import med.voll.api.services.MedicoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("medicos")
public class MedicoController {
    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid MedicoRecord medicoRecord) {
        medicoService.save(new Medico(medicoRecord));
    }

    @GetMapping
    public Page<MedicoListRecord> listar(Pageable pageable) {
        return medicoService.findAll(pageable).map(MedicoListRecord::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid MedicoUpdateRecord medicoRecord) {
        medicoService.update(medicoRecord);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deletar(@PathVariable Long id) {
        medicoService.delete(id);
    }
}
