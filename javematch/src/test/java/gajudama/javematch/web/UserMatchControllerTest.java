package gajudama.javematch.web;

import gajudama.javematch.logic.UserMatchLogic;
import gajudama.javematch.model.UserMatch;
import gajudama.javematch.model.Usuario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserMatchControllerTest {

    @Mock
    private UserMatchLogic userMatchLogic;

    @InjectMocks
    private UserMatchController userMatchController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Esto inicializa los mocks
    }

    @SuppressWarnings("null")
    @Test
    void testCreateMatch() {
        Long usuarioId = 1L;
        Long likedUsuarioId = 2L;
    
        // Crear usuarios mock para user1 y user2
        Usuario usuario1 = new Usuario();
        usuario1.setUserId(usuarioId);  // Asignar un id para el primer usuario
    
        Usuario usuario2 = new Usuario();
        usuario2.setUserId(likedUsuarioId);  // Asignar un id para el segundo usuario
    
        // Crear un match con los usuarios mock
        UserMatch match = new UserMatch();
        match.setUser1(usuario1);  // Asignar usuario1 al match
        match.setUser2(usuario2);  // Asignar usuario2 al match
        match.setUserMatchId(1L);
       
    
        // Simular la creación de un match
        when(userMatchLogic.createMatch(usuarioId, likedUsuarioId)).thenReturn(match);
    
        ResponseEntity<UserMatch> response = userMatchController.createMatch(usuarioId, likedUsuarioId);
    
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(usuarioId, response.getBody().getUser1().getUserId());
        assertEquals(likedUsuarioId, response.getBody().getUser2().getUserId());
    }

    @SuppressWarnings("null")
    @Test
    void testGetMatchById() {
        Long matchId = 1L;
        UserMatch match = new UserMatch();
        match.setUserMatchId(matchId);

        // Simular la búsqueda de un match por ID
        when(userMatchLogic.getMatchById(matchId)).thenReturn(Optional.of(match));

        ResponseEntity<UserMatch> response = userMatchController.getMatchById(matchId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(matchId, response.getBody().getUserMatchId());
    }

    @Test
    void testGetMatchByIdNotFound() {
        Long matchId = 1L;

        // Simular la no existencia de un match
        when(userMatchLogic.getMatchById(matchId)).thenReturn(Optional.empty());

        ResponseEntity<UserMatch> response = userMatchController.getMatchById(matchId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testRandomMatch() {
        //Long usuarioId = 1L;
        //UserMatch match = new UserMatch();
        //match.setUserMatchId(1L);
       

        // Simular la creación de un match aleatorio
        //when(userMatchLogic.randomMatch(usuarioId)).thenReturn(match);

       // ResponseEntity<UserMatch> response = userMatchController.randomMatch(usuarioId);

       // assertEquals(HttpStatus.CREATED, response.getStatusCode());
       // assertNotNull(response.getBody());
    }

    @SuppressWarnings("null")
    @Test
    void testUpdateMatch() {
        Long matchId = 1L;
        UserMatch matchDetails = new UserMatch();
        matchDetails.setFechaMatch(new Date());
        

        UserMatch updatedMatch = new UserMatch();
        updatedMatch.setUserMatchId(matchId);
        updatedMatch.setFechaMatch(new Date());
        

        // Simular la actualización de un match
        when(userMatchLogic.updateMatch(matchId, matchDetails)).thenReturn(updatedMatch);

        ResponseEntity<UserMatch> response = userMatchController.updateMatch(matchId, matchDetails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedMatch.getFechaMatch(), response.getBody().getFechaMatch());
        
    }
}