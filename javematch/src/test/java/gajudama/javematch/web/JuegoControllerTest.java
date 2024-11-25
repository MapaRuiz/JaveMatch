package gajudama.javematch.web;

import gajudama.javematch.logic.JuegoLogic;
import gajudama.javematch.model.Juego;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JuegoController.class)
public class JuegoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JuegoLogic juegoLogic;

    @Test
    public void testCreateJuego() throws Exception {
        Juego mockJuego = new Juego();
        mockJuego.setJuego_id(1L);
        mockJuego.setNombre("Test Game");

        when(juegoLogic.createJuego(Mockito.any(Juego.class))).thenReturn(mockJuego);

        mockMvc.perform(post("/api/juego")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Test Game\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.juego_id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Test Game"));
    }

    @Test
    public void testGetJuegoById_Found() throws Exception {
        Long juegoId = 1L;
        Juego mockJuego = new Juego();
        mockJuego.setJuego_id(juegoId);
        mockJuego.setNombre("Test Game");

        when(juegoLogic.getJuegosById(juegoId)).thenReturn(Optional.of(mockJuego));

        mockMvc.perform(get("/api/juego/{id}", juegoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.juego_id").value(juegoId))
                .andExpect(jsonPath("$.nombre").value("Test Game"));
    }

    @Test
    public void testGetJuegoById_NotFound() throws Exception {
        Long juegoId = 1L;

        when(juegoLogic.getJuegosById(juegoId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/juego/{id}", juegoId))
                .andExpect(status().isNotFound());
    }
}
