package gajudama.javematch.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import gajudama.javematch.logic.UserLikeLogic;
import gajudama.javematch.model.UserLike;

import java.util.List;

@RestController
@RequestMapping("/api/userlike")
public class UserLikeController {

    @Autowired
    private UserLikeLogic userLikeLogic;

    @PostMapping
    public ResponseEntity<UserLike> createLike(@RequestBody UserLike like) {
        UserLike newLike = userLikeLogic.createLike(like);
        return new ResponseEntity<>(newLike, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserLike> getLikeById(@PathVariable Long id) {
        return userLikeLogic.getLikeById(id)
            .map(like -> new ResponseEntity<>(like, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserLike> updateLike(@PathVariable Long id, @RequestBody UserLike likeDetails) {
        UserLike updatedLike = userLikeLogic.updateLike(id, likeDetails);
        return new ResponseEntity<>(updatedLike, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLike(@PathVariable Long id) {
        userLikeLogic.deleteLike(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<UserLike>> getAllLikes() {
        List<UserLike> likes = userLikeLogic.getAllLikes();
        return new ResponseEntity<>(likes, HttpStatus.OK);
    }

    // Endpoint específico para dar like a un usuario
    @PostMapping("/likeUser")
    public ResponseEntity<UserLike> likeUser(@RequestParam Long usuarioId, @RequestParam Long likedUsuarioId) {
        try {
            UserLike like = userLikeLogic.likeUser(usuarioId, likedUsuarioId);
            return new ResponseEntity<>(like, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
