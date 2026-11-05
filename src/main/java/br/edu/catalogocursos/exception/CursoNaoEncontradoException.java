package br.edu.catalogocursos.exception;

public class CursoNaoEncontradoException extends RuntimeException {

    public CursoNaoEncontradoException(Long id) {
        super("Curso não encontrado: " + id);
    }
}
