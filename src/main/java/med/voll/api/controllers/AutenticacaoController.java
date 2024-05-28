package med.voll.api.controllers;

import jakarta.validation.Valid;
import med.voll.api.models.Usuario;
import med.voll.api.records.TokenJWTRecord;
import med.voll.api.records.UsuarioRecord;
import med.voll.api.services.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class AutenticacaoController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AutenticacaoController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid UsuarioRecord usuario) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(usuario.login(), usuario.senha());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var tokenJWT = tokenService.createToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenJWTRecord(tokenJWT));
    }
}
