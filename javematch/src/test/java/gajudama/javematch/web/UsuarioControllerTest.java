package gajudama.javematch.web;

import gajudama.javematch.logic.UsuarioLogic;
import gajudama.javematch.model.Interes;
import gajudama.javematch.model.Plan;
import gajudama.javematch.model.Usuario;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioLogic usuarioLogic;

    @Test
    public void testGetUsuarios() throws Exception {
        Usuario mockUsuario = new Usuario();
        mockUsuario.setUserId(1L);
        mockUsuario.setNombre("John Doe");
        mockUsuario.setCorreo("john@example.com");

        Interes mockInteres = new Interes();
        mockInteres.setNombre("Gaming");
        mockUsuario.setIntereses(Collections.singletonList(mockInteres));

        when(usuarioLogic.getAllUsuarios()).thenReturn(Collections.singletonList(mockUsuario));

        mockMvc.perform(get("/api/usuario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("John Doe"))
                .andExpect(jsonPath("$[0].intereses[0]").value("Gaming"));
    }

    @Test
    public void testCreateUsuario() throws Exception {
        Usuario mockUsuario = new Usuario();
        mockUsuario.setUserId(1L);
        mockUsuario.setNombre("John Doe");
        mockUsuario.setCorreo("john@example.com");

        when(usuarioLogic.createUsuario(Mockito.any(Usuario.class))).thenReturn(mockUsuario);

        mockMvc.perform(post("/api/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"John Doe\",\"correo\":\"john@example.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.nombre").value("John Doe"))
                .andExpect(jsonPath("$.correo").value("john@example.com"));
    }

    @Test
    public void testGetUsuarioById_Found() throws Exception {
        Usuario mockUsuario = new Usuario();
        mockUsuario.setUserId(1L);
        mockUsuario.setNombre("John Doe");

        Interes mockInteres = new Interes();
        mockInteres.setNombre("Gaming");
        mockUsuario.setIntereses(Collections.singletonList(mockInteres));

        Plan mockPlan = new Plan();
        mockPlan.setNombre("Premium");
        mockUsuario.setPlan(mockPlan);

        when(usuarioLogic.getUsuarioById(1L)).thenReturn(Optional.of(mockUsuario));

        mockMvc.perform(get("/api/usuario/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.nombre").value("John Doe"))
                .andExpect(jsonPath("$.intereses[0]").value("Gaming"))
                .andExpect(jsonPath("$.plan").value("Premium"));
    }

    @Test
    public void testGetUsuarioById_NotFound() throws Exception {
        when(usuarioLogic.getUsuarioById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/usuario/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddInteres() throws Exception {
        Usuario mockUsuario = new Usuario();
        mockUsuario.setUserId(1L);
        mockUsuario.setNombre("John Doe");

        when(usuarioLogic.addInteres(Mockito.eq(1L), Mockito.any(Interes.class))).thenReturn(mockUsuario);

        mockMvc.perform(post("/api/usuario/addInteres")
                .param("usuarioId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Gaming\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.nombre").value("John Doe"));
    }

    @Test
    public void testFindUsersWithMatchingInterests() throws Exception {
        Usuario mockUsuario = new Usuario();
        mockUsuario.setUserId(2L);
        mockUsuario.setNombre("Jane Doe");

        when(usuarioLogic.findUsersWithMatchingInterests(1L)).thenReturn(Collections.singletonList(mockUsuario));

        mockMvc.perform(get("/api/usuario/findUsersWithMatchingInterests")
                .param("usuarioId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(2L))
                .andExpect(jsonPath("$[0].nombre").value("Jane Doe"));
    }

    @Test
    public void testLoginUsuario_Found() throws Exception {
        Usuario mockUsuario = new Usuario();
        mockUsuario.setUserId(1L);
        mockUsuario.setCorreo("john@example.com");

        when(usuarioLogic.loginUsuario("john@example.com")).thenReturn(Optional.of(mockUsuario));

        mockMvc.perform(post("/api/usuario/login")
                .param("email", "john@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.correo").value("john@example.com"));
    }

    @Test
    public void testLoginUsuario_NotFound() throws Exception {
        when(usuarioLogic.loginUsuario("john@example.com")).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/usuario/login")
                .param("email", "john@example.com"))
                .andExpect(status().isNotFound());
    }
}
