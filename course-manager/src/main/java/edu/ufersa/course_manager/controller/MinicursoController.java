package edu.ufersa.course_manager.controller;

import edu.ufersa.course_manager.model.Minicurso;
import edu.ufersa.course_manager.service.MinicursoService;
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

    @GetMapping
    public ResponseEntity<List<Minicurso>> listarMinicursos(){
        List<Minicurso> lista = minicursoService.listarMinicursos();
        return ResponseEntity.ok(lista);
    }

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

    @GetMapping("/detalhar/{idMinicurso}")
    public ResponseEntity<?> detalharMinicurso(@PathVariable("idMinicurso") Long id){
        Optional<Minicurso> minicursoOptional = minicursoService.buscarPorId(id);
        if(minicursoOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Não foi encontrado minicurso com esse ID");
        }
        Minicurso minicurso = minicursoOptional.get();
        return ResponseEntity.ok(minicurso);
    }

    @DeleteMapping("/deletar/{idMinicurso}")
    public ResponseEntity<String> deletarMinicurso(@PathVariable("idMinicurso") Long id, Authentication auth){
        if(auth == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado. Faça login para cadastrar minicurso.");
        }

        String resposta = minicursoService.deletarMinicursoPorId(id, auth);

        return ResponseEntity.ok().body(resposta);
    }

    @PostMapping("/validar/{id}/{valida}")
    public ResponseEntity<String> validarMinicurso(@PathVariable("id") Long id, Authentication auth, @PathVariable("valida")Boolean valida){
        String resposta = minicursoService.validarMinicurso(id, auth, valida);
        return ResponseEntity.ok(resposta);
    }
}
