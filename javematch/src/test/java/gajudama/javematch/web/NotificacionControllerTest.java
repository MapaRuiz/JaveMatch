package gajudama.javematch.web;

import gajudama.javematch.logic.NotificacionLogic;
import gajudama.javematch.model.Notificacion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificacionController.class)
public class NotificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificacionLogic notificacionLogic;

    @Test
    public void testSendNotification() throws Exception {
        Long usuarioId = 1L;
        String mensaje = "Test Notification";

        doNothing().when(notificacionLogic).sendNotification(usuarioId, mensaje, false);

        mockMvc.perform(post("/api/notificacion/send")
                .param("usuarioId", usuarioId.toString())
                .param("mensaje", mensaje))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetNotificacionesByUsuario() throws Exception {
        Long usuarioId = 1L;

        Notificacion mockNotificacion = new Notificacion();
        mockNotificacion.setNotificacion_id(1L);
        mockNotificacion.setMensaje("Test Notification");
        mockNotificacion.setFechaEnvio(new Date());

        when(notificacionLogic.getNotificacionesByUsuario(usuarioId)).thenReturn(Collections.singletonList(mockNotificacion));

        mockMvc.perform(get("/api/notificacion/usuario/{usuarioId}", usuarioId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].notificacion_id").value(1L))
                .andExpect(jsonPath("$[0].mensaje").value("Test Notification"))
                .andExpect(jsonPath("$[0].fechaEnvio").isNotEmpty());
    }
}
