package br.edu.catalogocursos.service;

import br.edu.catalogocursos.domain.Curso;
import br.edu.catalogocursos.exception.InstrutorNaoEncontradoException;
import br.edu.catalogocursos.repository.CursoRepository;
import br.edu.catalogocursos.repository.InstrutorRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CursoServiceTests {

    @Test
    void deveRejeitarCadastroComInstrutorInexistente() {
        InstrutorRepository instrutorRepository = mock(InstrutorRepository.class);
        CursoRepository cursoRepository = mock(CursoRepository.class);
        InstrutorService instrutorService = new InstrutorService(instrutorRepository, cursoRepository);
        CursoService cursoService = new CursoService(cursoRepository, instrutorService);
        when(instrutorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(InstrutorNaoEncontradoException.class,
                () -> cursoService.cadastrar(99L, new Curso()));

        verify(instrutorRepository).findById(99L);
        verifyNoInteractions(cursoRepository);
    }
}
