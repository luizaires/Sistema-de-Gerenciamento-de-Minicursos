package edu.ufersa.course_manager.controller;

import edu.ufersa.course_manager.model.Usuario;
import edu.ufersa.course_manager.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        if(usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody Usuario usuario){
        try{
            return ResponseEntity.ok(usuarioService.registrarUsuario(usuario));
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/meus-dados")
    public ResponseEntity<Usuario> buscarMeusDados(@AuthenticationPrincipal UserDetails userDetails){
        return usuarioService.buscarPorUsername(userDetails.getUsername())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/atualizar/{username}")
    public ResponseEntity<String> atualizarMeusDados(@PathVariable("username") String username, @RequestBody UsuarioDto usuarioAtualizado){

        return usuarioService.atualizarUsuario(username, usuarioAtualizado)
                .map(usuario -> ResponseEntity.ok("Usuario atualizado: " + usuario))
                .orElseGet(() -> ResponseEntity.badRequest().body("Erro ao atualizar usuário"));
    }

}
