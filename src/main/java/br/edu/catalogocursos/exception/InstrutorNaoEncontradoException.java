package br.edu.catalogocursos.exception;

public class InstrutorNaoEncontradoException extends RuntimeException {

    public InstrutorNaoEncontradoException(Long id) {
        super("Instrutor não encontrado: " + id);
    }
}
