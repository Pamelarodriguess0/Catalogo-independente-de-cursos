package br.edu.catalogocursos.api.controller;

import br.edu.catalogocursos.service.InstrutorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InstrutorController.class)
class InstrutorControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InstrutorService instrutorService;

    @Test
    void deveRetornar400AoCadastrarInstrutorComDadosInvalidos() throws Exception {
        mockMvc.perform(post("/api/instrutores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nome": "", "email": "email-invalido"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.path").value("/api/instrutores"));

        verifyNoInteractions(instrutorService);
    }
}
