package med.voll.api.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.models.Paciente;
import med.voll.api.records.PacienteListRecord;
import med.voll.api.records.PacienteRecord;
import med.voll.api.records.PacienteUpdateRecord;
import med.voll.api.services.PacienteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pacientes")
public class PacienteController {
    private final PacienteService pacienteService;
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid PacienteRecord pacienteRecord) {
        pacienteService.save(new Paciente(pacienteRecord));
    }

    @GetMapping
    public Page<PacienteListRecord> listar(Pageable pageable) {
        return pacienteService.findAll(pageable).map(PacienteListRecord::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid PacienteUpdateRecord pacienteRecord) {
        pacienteService.update(pacienteRecord);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        pacienteService.delete(id);
    }
}
