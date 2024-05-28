package med.voll.api.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.models.Medico;
import med.voll.api.records.MedicoListRecord;
import med.voll.api.records.MedicoRecord;
import med.voll.api.records.MedicoUpdateRecord;
import med.voll.api.records.MedicoUpdateReturnRecord;
import med.voll.api.services.MedicoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("medicos")
public class MedicoController {
    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<MedicoUpdateReturnRecord> cadastrar(@RequestBody @Valid MedicoRecord medicoRecord, UriComponentsBuilder uriBuilder) {
        var medico = new Medico(medicoRecord);
        MedicoUpdateReturnRecord medicoUpdateReturnRecord = medicoService.save(medico);
        URI uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medicoUpdateReturnRecord.id()).toUri();
        return ResponseEntity.created(uri).body(medicoUpdateReturnRecord);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoUpdateReturnRecord> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(medicoService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<MedicoListRecord>> listar(Pageable pageable) {
        return ResponseEntity.ok(medicoService.findAll(pageable).map(MedicoListRecord::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<MedicoUpdateReturnRecord> atualizar(@RequestBody @Valid MedicoUpdateRecord medicoRecord) {
        return ResponseEntity.ok(medicoService.update(medicoRecord));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id) {
        medicoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
