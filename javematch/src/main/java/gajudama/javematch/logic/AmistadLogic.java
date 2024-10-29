package gajudama.javematch.logic;

import java.util.List;

import org.springframework.stereotype.Service;
import gajudama.javematch.accesoDatos.AmistadRepository;
import gajudama.javematch.model.Amistad;
import gajudama.javematch.model.Usuario;

@Service
public class AmistadLogic {
    private final AmistadRepository AmistadRepository;

    public AmistadLogic(AmistadRepository AmistadRepository) {
        this.AmistadRepository = AmistadRepository;
    }

    public List<Amistad> getAllAmistades() {
        return AmistadRepository.findAll();
    }

    public Amistad createLike(Usuario usuarioAmistad, Usuario amigoUsuario) {
        Amistad amistad = new Amistad();
        amistad.setUsuarioAmistad(usuarioAmistad);
        amistad.setAmigoUsuario(amigoUsuario);
        return AmistadRepository.save(amistad);
    }

    public void deleteLike(Long id) {
        AmistadRepository.deleteById(id);
    }

}