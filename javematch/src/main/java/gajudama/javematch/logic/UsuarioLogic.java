package gajudama.javematch.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import gajudama.javematch.accesoDatos.UsuarioRepository;
import gajudama.javematch.model.Usuario;

@Service
public class UsuarioLogic {
    private final UsuarioRepository usuarioRepository;

    public UsuarioLogic(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario createUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario updateUsuario(Long id, Usuario updatedUsuario) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(updatedUsuario.getNombre());
            usuario.setCorreo(updatedUsuario.getCorreo());
            usuario.setPlan(updatedUsuario.getPlan());
            return usuarioRepository.save(usuario);
        }).orElseGet(() -> {
            updatedUsuario.setId(id);
            return usuarioRepository.save(updatedUsuario);
        });
    }

    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
    
}
