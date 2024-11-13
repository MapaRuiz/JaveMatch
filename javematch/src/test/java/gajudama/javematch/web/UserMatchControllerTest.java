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
import java.util.List;
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

    @Test
    void testCreateMatch() {
        Long usuarioId = 1L;
        Long likedUsuarioId = 2L;
    
        // Crear usuarios mock para user1 y user2
        Usuario usuario1 = new Usuario();
        usuario1.setUser_id(usuarioId);  // Asignar un id para el primer usuario
    
        Usuario usuario2 = new Usuario();
        usuario2.setUser_id(likedUsuarioId);  // Asignar un id para el segundo usuario
    
        // Crear un match con los usuarios mock
        UserMatch match = new UserMatch();
        match.setUser1(usuario1);  // Asignar usuario1 al match
        match.setUser2(usuario2);  // Asignar usuario2 al match
        match.setUserMatch_id(1L);
        match.setAmistad(false);
    
        // Simular la creación de un match
        when(userMatchLogic.createMatch(usuarioId, likedUsuarioId)).thenReturn(match);
    
        ResponseEntity<UserMatch> response = userMatchController.createMatch(usuarioId, likedUsuarioId);
    
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(usuarioId, response.getBody().getUser1().getUser_id());
        assertEquals(likedUsuarioId, response.getBody().getUser2().getUser_id());
    }
    

    @Test
    void testDeleteMatch() {
        Long matchId = 1L;

        // Simular la eliminación de un match
        doNothing().when(userMatchLogic).deleteMatch(matchId);

        ResponseEntity<Void> response = userMatchController.deleteMatch(matchId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userMatchLogic, times(1)).deleteMatch(matchId);
    }

    @Test
    void testGetAllMatches() {
        // Simular la obtención de todos los matches
        when(userMatchLogic.getAllMatches()).thenReturn(List.of(new UserMatch(), new UserMatch()));

        ResponseEntity<List<UserMatch>> response = userMatchController.getAllMatches();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().size() > 0);
    }

    @Test
    void testGetMatchById() {
        Long matchId = 1L;
        UserMatch match = new UserMatch();
        match.setUserMatch_id(matchId);

        // Simular la búsqueda de un match por ID
        when(userMatchLogic.getMatchById(matchId)).thenReturn(Optional.of(match));

        ResponseEntity<UserMatch> response = userMatchController.getMatchById(matchId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(matchId, response.getBody().getUserMatch_id());
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
        Long usuarioId = 1L;
        UserMatch match = new UserMatch();
        match.setUserMatch_id(1L);
        match.setAmistad(false);

        // Simular la creación de un match aleatorio
        when(userMatchLogic.randomMatch(usuarioId)).thenReturn(match);

        ResponseEntity<UserMatch> response = userMatchController.randomMatch(usuarioId);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testUpdateMatch() {
        Long matchId = 1L;
        UserMatch matchDetails = new UserMatch();
        matchDetails.setFechaMatch(new Date());
        matchDetails.setAmistad(true);

        UserMatch updatedMatch = new UserMatch();
        updatedMatch.setUserMatch_id(matchId);
        updatedMatch.setFechaMatch(new Date());
        updatedMatch.setAmistad(true);

        // Simular la actualización de un match
        when(userMatchLogic.updateMatch(matchId, matchDetails)).thenReturn(updatedMatch);

        ResponseEntity<UserMatch> response = userMatchController.updateMatch(matchId, matchDetails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedMatch.getFechaMatch(), response.getBody().getFechaMatch());
        assertEquals(updatedMatch.getAmistad(), response.getBody().getAmistad());
    }
}