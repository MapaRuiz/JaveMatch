package gajudama.javematch.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import gajudama.javematch.accesoDatos.UsuarioInteresRepository;
import gajudama.javematch.model.UsuarioInteres;

@Service
public class UsuarioInteresLogic {
    private final UsuarioInteresRepository usuarioInteresRepository;

    public UsuarioInteresLogic(UsuarioInteresRepository usuarioInteresRepository) {
        this.usuarioInteresRepository = usuarioInteresRepository;
    }

    public List<UsuarioInteres> getAllUsuarioIntereses() {
        return usuarioInteresRepository.findAll();
    }

    public Optional<UsuarioInteres> getUsuarioInteresById(Long id) {
        return usuarioInteresRepository.findById(id);
    }

    public UsuarioInteres createUsuarioInteres(UsuarioInteres usuarioInteres) {
        return usuarioInteresRepository.save(usuarioInteres);
    }

    public UsuarioInteres updateUsuarioInteres(Long id, UsuarioInteres updatedUsuarioInteres) {
        return usuarioInteresRepository.findById(id).map(usuarioInteres -> {
            usuarioInteres.setUsuarioIntereses(updatedUsuarioInteres.getUsuarioIntereses());
            usuarioInteres.setInteresUsuario(updatedUsuarioInteres.getInteresUsuario());
            return usuarioInteresRepository.save(usuarioInteres);
        }).orElseGet(() -> {
            updatedUsuarioInteres.setId(id);
            return usuarioInteresRepository.save(updatedUsuarioInteres);
        });
    }

    public void deleteUsuarioInteres(Long id) {
        usuarioInteresRepository.deleteById(id);
    }
    
}
