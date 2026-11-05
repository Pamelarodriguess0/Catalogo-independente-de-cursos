package br.edu.catalogocursos.domain;

import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DominioTests {

    @Test
    void deveAssociarCursoAoInstrutorSemDuplicar() {
        Instrutor instrutor = new Instrutor();
        Curso curso = new Curso();

        instrutor.adicionarCurso(curso);
        instrutor.adicionarCurso(curso);

        assertSame(instrutor, curso.getInstrutor());
        assertEquals(1, instrutor.getCursos().size());
        assertSame(curso, instrutor.getCursos().getFirst());
    }

    @Test
    void deveValidarCamposObrigatoriosECargaHorariaInvalida() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            var validator = factory.getValidator();
            Instrutor instrutor = new Instrutor();
            Curso curso = new Curso();
            assertEquals(2, validator.validate(instrutor).size());
            assertEquals(4, validator.validate(curso).size());

            instrutor.setNome("Ana");
            instrutor.setEmail("ana@example.com");
            curso.setTitulo("Java");
            curso.setCargaHoraria(40);
            curso.setNivel(NivelCurso.INICIANTE);
            curso.setInstrutor(instrutor);
            assertTrue(validator.validate(instrutor).isEmpty());
            assertTrue(validator.validate(curso).isEmpty());
            curso.setCargaHoraria(0);
            var violacoes = validator.validate(curso);
            assertEquals(1, violacoes.size());
            assertEquals("cargaHoraria", violacoes.iterator().next().getPropertyPath().toString());
        }
    }
}
