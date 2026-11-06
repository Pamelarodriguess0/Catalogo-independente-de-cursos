package br.edu.catalogocursos.api.controller;

import br.edu.catalogocursos.domain.Curso;
import br.edu.catalogocursos.domain.Instrutor;
import br.edu.catalogocursos.domain.NivelCurso;
import br.edu.catalogocursos.repository.CursoRepository;
import br.edu.catalogocursos.repository.InstrutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Configure TEST_DB_* exclusivamente para um PostgreSQL dedicado aos testes.
// O Liquibase prepara o esquema; os dados inseridos pelo teste sofrem rollback.
@EnabledIfEnvironmentVariable(named = "TEST_DB_URL", matches = "jdbc:postgresql:.*")
@SpringBootTest(properties = {
        "spring.datasource.url=${TEST_DB_URL}",
        "spring.datasource.username=${TEST_DB_USERNAME}",
        "spring.datasource.password=${TEST_DB_PASSWORD}"
})
@AutoConfigureMockMvc
@Transactional
class InstrutorCursosIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InstrutorRepository instrutorRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Test
    void deveListarSomenteCursosDoInstrutorOrdenadosPorTitulo() throws Exception {
        Instrutor ana = salvarInstrutor("Ana");
        Instrutor bruno = salvarInstrutor("Bruno");
        Curso java = salvarCurso("Java", ana);
        salvarCurso("Banco de dados", bruno);
        Curso algoritmos = salvarCurso("Algoritmos", ana);
        cursoRepository.flush();

        mockMvc.perform(get("/api/instrutores/{id}/cursos", ana.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(algoritmos.getId()))
                .andExpect(jsonPath("$[0].titulo").value("Algoritmos"))
                .andExpect(jsonPath("$[1].id").value(java.getId()))
                .andExpect(jsonPath("$[1].titulo").value("Java"));
    }

    private Instrutor salvarInstrutor(String nome) {
        Instrutor instrutor = new Instrutor();
        instrutor.setNome(nome);
        instrutor.setEmail(UUID.randomUUID() + "@example.com");
        return instrutorRepository.save(instrutor);
    }

    private Curso salvarCurso(String titulo, Instrutor instrutor) {
        Curso curso = new Curso();
        curso.setTitulo(titulo);
        curso.setCargaHoraria(40);
        curso.setNivel(NivelCurso.INICIANTE);
        instrutor.adicionarCurso(curso);
        return cursoRepository.save(curso);
    }
}
