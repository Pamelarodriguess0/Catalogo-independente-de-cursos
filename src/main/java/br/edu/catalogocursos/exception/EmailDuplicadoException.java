package br.edu.catalogocursos.exception;

public class EmailDuplicadoException extends RuntimeException {

    public EmailDuplicadoException() {
        super("Já existe um instrutor com este email");
    }
}
