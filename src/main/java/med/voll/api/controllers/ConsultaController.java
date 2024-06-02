package med.voll.api.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.records.ConsultaCancelRecord;
import med.voll.api.records.ConsultaRecord;
import med.voll.api.services.AgendaDeConsultasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {
    private final AgendaDeConsultasService agendaDeConsultasService;

    public ConsultaController(AgendaDeConsultasService agendaDeConsultasService) {
        this.agendaDeConsultasService = agendaDeConsultasService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid ConsultaRecord consultaRecord) {
        return ResponseEntity.ok(agendaDeConsultasService.agendar(consultaRecord));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid ConsultaCancelRecord dados) {
        agendaDeConsultasService.cancelar(dados);
        return ResponseEntity.noContent().build();
    }
}
