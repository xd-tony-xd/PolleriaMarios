package com.example.polleriaMarios.security;

import com.example.polleriaMarios.entity.Usuario;
import com.example.polleriaMarios.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No existe usuario con email: " + email));

        String roleName = usuario.getRol() != null ? usuario.getRol().getNombre() : "ADMIN";
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + roleName);

        return new User(usuario.getEmail(), usuario.getPassword(),
                usuario.getActivo() != null && usuario.getActivo(),
                true, true, true,
                Collections.singletonList(authority));
    }
}
