package edu.ufersa.course_manager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String usuario;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @OneToMany(mappedBy = "instrutor", cascade = CascadeType.ALL)
    private List<Minicurso> minicursosCriados;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(tipo);
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return usuario;
    }

    public void setSenha(String encode) {

    }

    public CharSequence getSenha() {
        return senha;
    }

    public enum Tipo implements GrantedAuthority {
        ADMIN, PROFESSOR, ALUNO;

        @Override
        public String getAuthority() {
            return name();
        }
    }
}

