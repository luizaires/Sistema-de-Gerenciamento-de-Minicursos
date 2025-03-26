package edu.ufersa.course_manager.controller;

import edu.ufersa.course_manager.model.Minicurso;
import edu.ufersa.course_manager.service.MinicursoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/minicursos")
public class MinicursoController {
    @Autowired
    private MinicursoService minicursoService;

    @Operation(summary = "Listar os minicursos cadastrados")
    @GetMapping
    public ResponseEntity<List<Minicurso>> listarMinicursos(){
        List<Minicurso> lista = minicursoService.listarMinicursos();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Cadastrar um minicurso")
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarMinicurso(@RequestBody MinicursoDto minicursoDto, Authentication auth){
        if(auth == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado. Faça login para cadastrar minicurso.");
        }
        Optional<Minicurso> minicursoCriado = minicursoService.cadastrarMinicurso(minicursoDto, auth);

        if(minicursoCriado.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(minicursoCriado);
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado");
        }
    }

    @Operation(summary = "Detalhar as informações de um minicurso quando informado o seu ID")
    @GetMapping("/detalhar/{idMinicurso}")
    public ResponseEntity<?> detalharMinicurso(@PathVariable("idMinicurso") Long id){
        Optional<Minicurso> minicursoOptional = minicursoService.buscarPorId(id);
        if(minicursoOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Não foi encontrado minicurso com esse ID");
        }
        Minicurso minicurso = minicursoOptional.get();
        return ResponseEntity.ok(minicurso);
    }

    @Operation(summary = "Deletar um minicurso, quando informado o seu ID")
    @DeleteMapping("/deletar/{idMinicurso}")
    public ResponseEntity<String> deletarMinicurso(@PathVariable("idMinicurso") Long id, Authentication auth){
        if(auth == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado. Faça login para deletar minicurso.");
        }

        String resposta = minicursoService.deletarMinicursoPorId(id, auth);

        return ResponseEntity.ok().body(resposta);
    }

    @Operation(summary = "Validar ou não um minicurso, atualizando o seu status.", description = "Necessário especificar ID e o status de valida, como true ou false")
    @PostMapping("/validar/{id}/{valida}")
    public ResponseEntity<String> validarMinicurso(@PathVariable("id") Long id, Authentication auth, @PathVariable("valida")Boolean valida){
        String resposta = minicursoService.validarMinicurso(id, auth, valida);
        return ResponseEntity.ok(resposta);
    }
}
