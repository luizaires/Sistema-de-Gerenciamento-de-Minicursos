package edu.ufersa.course_manager.controller;

import edu.ufersa.course_manager.model.Inscricao;
import edu.ufersa.course_manager.model.Minicurso;
import edu.ufersa.course_manager.model.Usuario;
import edu.ufersa.course_manager.service.InscricaoService;
import edu.ufersa.course_manager.service.MinicursoService;
import edu.ufersa.course_manager.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/inscricoes")
public class InscricaoController {
    @Autowired
    private InscricaoService inscricaoService;

    @Autowired
    private MinicursoService minicursoService;

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Listar as inscrições em um minicurso", description = "Necessário especificar o id do minicurso")
    @GetMapping("/{idMinicurso}")
    public ResponseEntity<List<String>> listarInscricoesPorMinicurso(@PathVariable("idMinicurso") Long idMinicurso){
        Optional<Minicurso> minicursoOptional = minicursoService.buscarPorId(idMinicurso);
        if(minicursoOptional.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        Minicurso minicurso = minicursoOptional.get();
        List<Inscricao> inscricoes = inscricaoService.listarInscricoesPorMinicurso(minicurso);
        List<String> inscricoesString = inscricoes.stream()
                .map(Inscricao::toString)
                .toList();
        return ResponseEntity.ok(inscricoesString);
    }

    @Operation(summary = "Inscreve o usuário que está logado no minicurso")
    @PostMapping("/inscrever/{idMinicurso}")
    public ResponseEntity<String> inscrever(Authentication auth, @PathVariable("idMinicurso") Long idMinicurso){
        Optional<Usuario> usuarioOptional = usuarioService.buscarPorUsername(auth.getName());
        if(usuarioOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado");
        }
        Usuario usuario = usuarioOptional.get();
        String resultado = inscricaoService.inscreverUsuarioEmMinicurso(usuario, idMinicurso);
        return ResponseEntity.ok(resultado);
    }

    @Operation(summary = "Cancelar a inscrição do usuário em um minicurso, desde que esteja inscrito", description = "O usuário precisa informar o ID do minicurso")
    @DeleteMapping("/cancelar/{idMinicurso}")
    public ResponseEntity<String> cancelarInscricao(Authentication auth, @PathVariable("idMinicurso") Long idMinicurso){
        Optional<Usuario> usuarioOptional = usuarioService.buscarPorUsername(auth.getName());
        if(usuarioOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado");
        }
        Optional<Minicurso> minicursoOptional = minicursoService.buscarPorId(idMinicurso);
        if(minicursoOptional.isEmpty()) {
            return ResponseEntity.ok("Não existe minicurso com ID:"+idMinicurso);
        }
        Minicurso minicurso = minicursoOptional.get();
        Usuario usuario = usuarioOptional.get();
        String resposta = inscricaoService.cancelarInscricao(minicurso, usuario);
        return ResponseEntity.ok(resposta);
    }
}
