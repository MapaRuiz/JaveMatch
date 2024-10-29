package gajudama.javematch.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gajudama.javematch.logic.UsuarioLogic;
import gajudama.javematch.model.Plan;
import gajudama.javematch.model.Usuario;

@RestController
@RequestMapping("/api/plan")
public class PlanController {
    @Autowired
    private UsuarioLogic usuarioLogic;

    @PostMapping("/upgrade")
    public ResponseEntity<Usuario> upgradePlan(@RequestParam Long usuarioId, @RequestBody Plan newPlan) {
        Usuario usuario = usuarioLogic.getUsuarioById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario not found"));
        usuario.setPlan(newPlan);
        Usuario updatedUsuario = usuarioLogic.updateUsuario(usuarioId, usuario);
        return new ResponseEntity<>(updatedUsuario, HttpStatus.OK);
    }

}
