package gajudama.javematch.web;

import gajudama.javematch.logic.VideollamadaLogic;
import gajudama.javematch.logic.UsuarioLogic;
import gajudama.javematch.accesoDatos.JuegoRepository;
import gajudama.javematch.model.Videollamada;
import gajudama.javematch.model.Juego;
import gajudama.javematch.model.UserMatch;
import gajudama.javematch.model.Usuario;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VideollamadaController.class)
public class VideollamadaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VideollamadaLogic videollamadaLogic;

    @MockBean
    private UsuarioLogic usuarioLogic;

    @MockBean
    private JuegoRepository juegoRepository;

    @Test
    public void testCreateVideollamada() throws Exception {
        Videollamada mockVideollamada = new Videollamada();
        mockVideollamada.setVideollamada_id(1L);
        mockVideollamada.setFechaVideollamada(LocalDateTime.now());
        mockVideollamada.setEstado("Active");

        when(videollamadaLogic.createVideollamada(Mockito.any(Videollamada.class))).thenReturn(mockVideollamada);

        mockMvc.perform(post("/api/videollamada")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"estado\":\"Active\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.videollamada_id").value(1L))
                .andExpect(jsonPath("$.estado").value("Active"));
    }

    @Test
    public void testGetVideollamadaById_Found() throws Exception {
        Videollamada mockVideollamada = new Videollamada();
        mockVideollamada.setVideollamada_id(1L);
        mockVideollamada.setEstado("Active");

        when(videollamadaLogic.getVideollamadaById(1L)).thenReturn(Optional.of(mockVideollamada));

        mockMvc.perform(get("/api/videollamada/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.videollamada_id").value(1L))
                .andExpect(jsonPath("$.estado").value("Active"));
    }

    @Test
    public void testGetVideollamadaById_NotFound() throws Exception {
        when(videollamadaLogic.getVideollamadaById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/videollamada/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateVideollamadaWithMatch() throws Exception {
        Videollamada mockVideollamada = new Videollamada();
        mockVideollamada.setVideollamada_id(1L);

        when(videollamadaLogic.createVideollamada(1L)).thenReturn(mockVideollamada);

        mockMvc.perform(post("/api/videollamada/createWithMatch")
                .param("matchId", "1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testAddJuegoToVideollamada() throws Exception {
        // Mock data
        Usuario user1 = new Usuario();
        user1.setUserId(1L);
        user1.setNombre("John Doe");

        UserMatch mockMatch = new UserMatch();
        mockMatch.setUserMatchId(1L);
        mockMatch.setUser1(user1); // Ensure user1 is set

        Videollamada mockVideollamada = new Videollamada();
        mockVideollamada.setVideollamada_id(1L);
        mockVideollamada.setMatch(mockMatch); // Ensure match is set
        mockVideollamada.setJuegos(new ArrayList<>());

        Juego mockJuego = new Juego();
        mockJuego.setNombre("Chess");

        // Mock behaviors
        when(videollamadaLogic.getVideollamadaById(1L)).thenReturn(Optional.of(mockVideollamada));
        when(juegoRepository.findByNombre("Chess")).thenReturn(Optional.of(mockJuego));
        when(videollamadaLogic.createVideollamada(Mockito.any(Videollamada.class))).thenReturn(mockVideollamada);

        // Perform the request
        mockMvc.perform(post("/api/videollamada/{videollamadaId}/addJuego", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Chess\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.videollamada_id").value(1L));
    }

    @Test
    public void testGetVideollamadaByMatchId_Found() throws Exception {
        Videollamada mockVideollamada = new Videollamada();
        mockVideollamada.setVideollamada_id(1L);

        UserMatch mockMatch = new UserMatch();
        mockMatch.setUserMatchId(1L);
        mockMatch.setUser1(new Usuario());
        mockMatch.setUser2(new Usuario());
        mockVideollamada.setMatch(mockMatch);

        when(videollamadaLogic.getVideollamadaByMatchId(1L)).thenReturn(mockVideollamada);

        mockMvc.perform(get("/api/videollamada/match/{matchId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.videollamada_id").value(1L))
                .andExpect(jsonPath("$.match.userMatchId").value(1L));
    }

    @Test
    public void testGetVideollamadaByMatchId_NotFound() throws Exception {
        when(videollamadaLogic.getVideollamadaByMatchId(1L)).thenReturn(null);

        mockMvc.perform(get("/api/videollamada/match/{matchId}", 1L))
                .andExpect(status().isNotFound());
    }
}
