package gajudama.javematch.web;

import gajudama.javematch.logic.InteresLogic;
import gajudama.javematch.model.Interes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InteresController.class)
public class InteresControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InteresLogic interesLogic;

    @Test
    public void testGetInteresById_Found() throws Exception {
        Long interesId = 1L;
        Interes mockInteres = new Interes();
        mockInteres.setInteresId(interesId);
        mockInteres.setNombre("Example Interes");

        when(interesLogic.getInteresById(interesId)).thenReturn(Optional.of(mockInteres));

        mockMvc.perform(get("/api/interes/{id}", interesId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.interesId").value(interesId))
                .andExpect(jsonPath("$.nombre").value("Example Interes"));
    }

    @Test
    public void testGetInteresById_NotFound() throws Exception {
        Long interesId = 1L;

        when(interesLogic.getInteresById(interesId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/interes/{id}", interesId))
                .andExpect(status().isNotFound());
    }
}
