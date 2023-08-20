package com.challenge2.challenge2.controllersTest;

import com.challenge2.challenge2.restControllers.SquadController;
import com.challenge2.challenge2.services.impl.SquadService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = SquadController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class SquadControllerTest2 {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SquadService squadService;

}
