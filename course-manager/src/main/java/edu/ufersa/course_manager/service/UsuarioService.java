package edu.ufersa.course_manager.service;


import edu.ufersa.course_manager.controller.UsuarioDto;
import edu.ufersa.course_manager.model.Usuario;
import edu.ufersa.course_manager.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public Usuario registrarUsuario(Usuario usuario) {
        if(existeUsername(usuario.getUsername())){
            throw new RuntimeException("Nome de usuário já cadastrado");
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        if(usuario.getTipo() == null){
            usuario.setTipo(Usuario.Tipo.ALUNO);
        }
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public Optional<Usuario> atualizarUsuario(String username, UsuarioDto usuarioAtualizado) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByUsername(username);

        if(usuarioExistente.isEmpty()){
            return Optional.empty();
        }
        Usuario usuario = usuarioExistente.get();

        if (usuarioAtualizado.nome() != null) {
            usuario.setNome(usuarioAtualizado.nome());
        }
        if (usuarioAtualizado.email() != null) {
            usuario.setEmail(usuarioAtualizado.email());
        }
        if (usuarioAtualizado.senha() != null) {
            usuario.setSenha(passwordEncoder.encode(usuarioAtualizado.senha()));
        }
        return Optional.of(usuarioRepository.save(usuario));
    }

    public boolean existeUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }
}
