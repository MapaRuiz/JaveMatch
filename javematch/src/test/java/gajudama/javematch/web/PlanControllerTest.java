package gajudama.javematch.web;

import gajudama.javematch.logic.PlanLogic;
import gajudama.javematch.model.Plan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlanController.class)
public class PlanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlanLogic planLogic;

    @Test
    public void testGetPlanById_Found() throws Exception {
        Long planId = 1L;
        Plan mockPlan = new Plan();
        mockPlan.setPlan_id(planId);
        mockPlan.setNombre("Premium");
        mockPlan.setMaxLikes(100);

        when(planLogic.getPlansById(planId)).thenReturn(Optional.of(mockPlan));

        mockMvc.perform(get("/api/plan/{id}", planId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.plan_id").value(planId))
                .andExpect(jsonPath("$.nombre").value("Premium"))
                .andExpect(jsonPath("$.maxLikes").value(100));
    }

    @Test
    public void testGetPlanById_NotFound() throws Exception {
        Long planId = 1L;

        when(planLogic.getPlansById(planId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/plan/{id}", planId))
                .andExpect(status().isNotFound());
    }
}
