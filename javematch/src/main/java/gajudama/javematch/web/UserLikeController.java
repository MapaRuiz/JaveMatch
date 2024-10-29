package gajudama.javematch.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gajudama.javematch.logic.UserLikeLogic;
import gajudama.javematch.model.UserLike;

@RestController
@RequestMapping("/api/userlike")
public class UserLikeController {

    @Autowired
    private UserLikeLogic userLikeLogic;

    @PostMapping("/{usuarioId}/like/{likedUsuarioId}")
    public ResponseEntity<UserLike> likeUser (
        @PathVariable Long usuarioId, 
        @PathVariable Long likedUsuarioId
    ) throws Exception {
        UserLike userLike = userLikeLogic.likeUser(usuarioId, likedUsuarioId);
        return new ResponseEntity<>(userLike, HttpStatus.CREATED);
    }
}
