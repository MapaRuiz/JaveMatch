package gajudama.javematch.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gajudama.javematch.accesoDatos.AmistadRepository;
import gajudama.javematch.model.Amistad;
import jakarta.transaction.Transactional;

@Service
public class AmistadLogic {

    @Autowired
    private AmistadRepository amistadRepository;

    @Transactional
    public Amistad createAmistad(Amistad amistad) {
        return amistadRepository.save(amistad);
    }

    public Optional<Amistad> getAmistadById(Long id) {
        return amistadRepository.findById(id);
    }

    @Transactional
    public Amistad updateAmistad(Long id, Amistad amistadDetails) {
        return amistadRepository.findById(id).map(amistad -> {
            amistad.setAmigoUsuario(amistadDetails.getAmigoUsuario());
            amistad.setUsuarioAmistad(amistadDetails.getUsuarioAmistad());
            return amistadRepository.save(amistad);
        }).orElseThrow(() -> new RuntimeException("Amistad not found"));
    }

    @Transactional
    public void deleteAmistad(Long id) {
        if (!amistadRepository.existsById(id)) {
            throw new RuntimeException("Amistad not found");
        }
        amistadRepository.deleteById(id);
    }

    public List<Amistad> getAllAmistades() {
        return amistadRepository.findAll();
    }

}