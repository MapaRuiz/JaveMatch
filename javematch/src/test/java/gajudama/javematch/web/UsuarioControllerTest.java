package gajudama.javematch.web;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gajudama.javematch.logic.UsuarioLogic;
import gajudama.javematch.model.Usuario;
import gajudama.javematch.model.Interes;
import gajudama.javematch.model.Plan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Optional;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;
    

    @MockBean
    private UsuarioLogic usuarioLogic;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    @Test
    void testCreateUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUserId(1L);
        usuario.setNombre("John Doe");
        usuario.setCorreo("john@javeriana.edu.co");

        when(usuarioLogic.createUsuario(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/api/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"John Doe\",\"correo\":\"john@javeriana.edu.co\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("John Doe"));
    }

    @Test
    void testGetUsuarioById() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUserId(1L);
        usuario.setNombre("John Doe");

        when(usuarioLogic.getUsuarioById(1L)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("John Doe"));
    }

    @Test
    void testDeleteUsuario() throws Exception {
        mockMvc.perform(delete("/api/usuario/1"))
                .andExpect(status().isNoContent());
        
        verify(usuarioLogic, times(1)).deleteUsuario(1L);
    }

    @Test
void testAddInteres() throws Exception {
    Long usuarioId = 1L;  // Asigna el ID del usuario que quieras probar
    Interes interes = new Interes();
    interes.setNombre("Deporte");  // Define las propiedades del objeto Interes

    // Convierte el objeto Interes a JSON usando una herramienta como Jackson
    String interesJson = new ObjectMapper().writeValueAsString(interes);

    mockMvc.perform(post("/api/usuario/addInteres")
            .param("usuarioId", usuarioId.toString())
            .contentType("application/json")  // Asegura que el Content-Type sea application/json
            .content(interesJson))
            .andExpect(status().isOk());
}


    @Test
    void testFindUsersWithMatchingInterests() throws Exception {
        Usuario usuario1 = new Usuario();
        usuario1.setUserId(1L);
        usuario1.setNombre("Alice");

        Usuario usuario2 = new Usuario();
        usuario2.setUserId(2L);
        usuario2.setNombre("Bob");

        when(usuarioLogic.findUsersWithMatchingInterests(1L)).thenReturn(Arrays.asList(usuario1, usuario2));

        mockMvc.perform(get("/api/usuario/findUsersWithMatchingInterests?usuarioId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Alice"))
                .andExpect(jsonPath("$[1].nombre").value("Bob"));
    }

    @Test
    void testGetAllUsuarios() throws Exception {
        Usuario usuario1 = new Usuario();
        usuario1.setUserId(1L);
        usuario1.setNombre("Alice");

        Usuario usuario2 = new Usuario();
        usuario2.setUserId(2L);
        usuario2.setNombre("Bob");

        when(usuarioLogic.getAllUsuarios()).thenReturn(Arrays.asList(usuario1, usuario2));

        mockMvc.perform(get("/api/usuario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Alice"))
                .andExpect(jsonPath("$[1].nombre").value("Bob"));
    }

    @Test
    void testGetRandomUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUserId(1L);
        usuario.setNombre("Random User");

        when(usuarioLogic.getRandomUsuario()).thenReturn(usuario);

        mockMvc.perform(get("/api/usuario/random"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Random User"));
    }

    @Test
    void testLoginUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUserId(1L);
        usuario.setCorreo("test@javeriana.edu.co");

        when(usuarioLogic.loginUsuario("test@javeriana.edu.co")).thenReturn(Optional.of(usuario));

        mockMvc.perform(post("/api/usuario/login")
                .param("email", "test@javeriana.edu.co"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correo").value("test@javeriana.edu.co"));
    }

    @Test
    void testRegisterUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUserId(1L);
        usuario.setCorreo("test@javeriana.edu.co");

        when(usuarioLogic.registerUsuario(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/api/usuario/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"John Doe\",\"correo\":\"test@javeriana.edu.co\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.correo").value("test@javeriana.edu.co"));
    }

    @Test
    void testUpdateUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUserId(1L);
        usuario.setNombre("Updated User");

        when(usuarioLogic.updateUsuario(eq(1L), any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(put("/api/usuario/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Updated User\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Updated User"));
    }

    @Test
    void testUpgradePlan() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUserId(1L);

        Plan plan = new Plan();
        plan.setPlan_id(1L);

        when(usuarioLogic.upgradePlan(eq(1L), anyLong())).thenReturn(usuario);

        mockMvc.perform(post("/api/usuario/upgradePlan")
                .param("usuarioId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"plan_id\":1}"))
                .andExpect(status().isOk());
    }
}
