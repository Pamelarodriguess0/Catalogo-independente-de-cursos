package br.edu.catalogocursos.service;

import br.edu.catalogocursos.domain.Curso;
import br.edu.catalogocursos.domain.Instrutor;
import br.edu.catalogocursos.exception.CursoNaoEncontradoException;
import br.edu.catalogocursos.repository.CursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final InstrutorService instrutorService;

    public CursoService(CursoRepository cursoRepository, InstrutorService instrutorService) {
        this.cursoRepository = cursoRepository;
        this.instrutorService = instrutorService;
    }

    @Transactional
    public Curso cadastrar(Long instrutorId, Curso curso) {
        Instrutor instrutor = instrutorService.buscarPorId(instrutorId);
        instrutor.adicionarCurso(curso);
        return cursoRepository.save(curso);
    }

    @Transactional(readOnly = true)
    public Curso buscarPorId(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new CursoNaoEncontradoException(id));
    }
}
