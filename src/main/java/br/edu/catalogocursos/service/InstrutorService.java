package br.edu.catalogocursos.service;

import br.edu.catalogocursos.domain.Curso;
import br.edu.catalogocursos.domain.Instrutor;
import br.edu.catalogocursos.exception.EmailDuplicadoException;
import br.edu.catalogocursos.exception.InstrutorNaoEncontradoException;
import br.edu.catalogocursos.repository.CursoRepository;
import br.edu.catalogocursos.repository.InstrutorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InstrutorService {

    private final InstrutorRepository instrutorRepository;
    private final CursoRepository cursoRepository;

    public InstrutorService(InstrutorRepository instrutorRepository, CursoRepository cursoRepository) {
        this.instrutorRepository = instrutorRepository;
        this.cursoRepository = cursoRepository;
    }

    @Transactional
    public Instrutor cadastrar(Instrutor instrutor) {
        if (instrutorRepository.existsByEmail(instrutor.getEmail())) {
            throw new EmailDuplicadoException();
        }
        return instrutorRepository.save(instrutor);
    }

    @Transactional(readOnly = true)
    public Instrutor buscarPorId(Long id) {
        return instrutorRepository.findById(id)
                .orElseThrow(() -> new InstrutorNaoEncontradoException(id));
    }

    @Transactional(readOnly = true)
    public List<Curso> listarCursos(Long instrutorId) {
        buscarPorId(instrutorId);
        return cursoRepository.findByInstrutorIdOrderByTituloAsc(instrutorId);
    }
}
