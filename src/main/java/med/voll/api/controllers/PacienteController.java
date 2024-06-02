package med.voll.api.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.models.Paciente;
import med.voll.api.records.PacienteListRecord;
import med.voll.api.records.PacienteRecord;
import med.voll.api.records.PacienteUpdateRecord;
import med.voll.api.records.PacienteUpdateReturnRecord;
import med.voll.api.services.PacienteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {
    private final PacienteService pacienteService;
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PacienteUpdateReturnRecord> cadastrar(@RequestBody @Valid PacienteRecord pacienteRecord, UriComponentsBuilder uriBuilder) {
        PacienteUpdateReturnRecord pacienteUpdateReturnRecord = pacienteService.save(new Paciente(pacienteRecord));
        URI uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(pacienteUpdateReturnRecord.id()).toUri();
        return ResponseEntity.created(uri).body(pacienteUpdateReturnRecord);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteUpdateReturnRecord> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pacienteService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<PacienteListRecord>> listar(Pageable pageable) {
        return ResponseEntity.ok(pacienteService.findAll(pageable).map(PacienteListRecord::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<PacienteUpdateReturnRecord> atualizar(@RequestBody @Valid PacienteUpdateRecord pacienteRecord) {
        return ResponseEntity.ok(pacienteService.update(pacienteRecord));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id) {
        pacienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
