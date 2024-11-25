package gajudama.javematch.web;

import gajudama.javematch.logic.UserMatchLogic;
import gajudama.javematch.logic.UsuarioLogic;
import gajudama.javematch.model.UserMatch;
import gajudama.javematch.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserMatchController.class)
public class UserMatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserMatchLogic userMatchLogic;

    @MockBean
    private UsuarioLogic usuarioLogic;

    @MockBean
    private NotificacionController notificacionController;

    @Test
    public void testGetMatchById_Found() throws Exception {
        Long matchId = 1L;
        UserMatch mockMatch = new UserMatch();
        mockMatch.setUserMatchId(matchId);
        mockMatch.setFechaMatch(new Date());

        when(userMatchLogic.getMatchById(matchId)).thenReturn(Optional.of(mockMatch));

        mockMvc.perform(get("/api/usermatch/{id}", matchId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userMatchId").value(matchId))
                .andExpect(jsonPath("$.fechaMatch").isNotEmpty());
    }

    @Test
    public void testGetMatchById_NotFound() throws Exception {
        Long matchId = 1L;

        when(userMatchLogic.getMatchById(matchId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/usermatch/{id}", matchId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateMatch() throws Exception {
        Long userId = 1L;
        Long likedUserId = 2L;

        UserMatch mockMatch = new UserMatch();
        mockMatch.setUserMatchId(1L);

        when(userMatchLogic.createMatch(userId, likedUserId)).thenReturn(mockMatch);

        mockMvc.perform(post("/api/usermatch/createMatch")
                .param("usuarioId", userId.toString())
                .param("likedUsuarioId", likedUserId.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userMatchId").value(1L));
    }

    @Test
    public void testAcceptUserAndNotify() throws Exception {
        Long userId = 1L;
        Long likedUserId = 2L;

        UserMatch mockMatch = new UserMatch();
        mockMatch.setUserMatchId(1L);

        Usuario mockUser = new Usuario();
        mockUser.setUserId(userId);
        mockUser.setNombre("John Doe");

        when(userMatchLogic.createMatch(userId, likedUserId)).thenReturn(mockMatch);
        when(usuarioLogic.getUsuarioById(userId)).thenReturn(Optional.of(mockUser));

        mockMvc.perform(post("/api/usermatch/accept/{likedUsuarioId}", likedUserId)
                .param("usuarioId", userId.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userMatchId").value(1L));
    }

    @Test
    public void testRejectUser() throws Exception {
        Long likedUserId = 2L;

        mockMvc.perform(post("/api/usermatch/reject/{likedUsuarioId}", likedUserId))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario rechazado"));
    }

    @Test
    public void testGetMutualMatches() throws Exception {
        Long userId = 1L;

        UserMatch mockMatch = new UserMatch();
        mockMatch.setUserMatchId(1L);

        when(userMatchLogic.getMutualMatch(userId)).thenReturn(Collections.singletonList(mockMatch));

        mockMvc.perform(get("/api/usermatch/mutual/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userMatchId").value(1L));
    }
}
