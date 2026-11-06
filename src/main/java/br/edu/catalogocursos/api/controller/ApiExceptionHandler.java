package br.edu.catalogocursos.api.controller;

import br.edu.catalogocursos.api.dto.ErroResponse;
import br.edu.catalogocursos.exception.CursoNaoEncontradoException;
import br.edu.catalogocursos.exception.EmailDuplicadoException;
import br.edu.catalogocursos.exception.InstrutorNaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> validarEntrada(MethodArgumentNotValidException exception,
                                                       HttpServletRequest request) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .sorted().collect(java.util.stream.Collectors.joining("; "));
        return erro(HttpStatus.BAD_REQUEST, message, request);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErroResponse> entradaInvalida(HttpServletRequest request) {
        return erro(HttpStatus.BAD_REQUEST, "Entrada inválida: verifique o JSON e os tipos dos campos", request);
    }

    @ExceptionHandler({InstrutorNaoEncontradoException.class, CursoNaoEncontradoException.class})
    public ResponseEntity<ErroResponse> naoEncontrado(RuntimeException exception, HttpServletRequest request) {
        return erro(HttpStatus.NOT_FOUND, exception.getMessage(), request);
    }

    @ExceptionHandler(EmailDuplicadoException.class)
    public ResponseEntity<ErroResponse> emailDuplicado(EmailDuplicadoException exception,
                                                       HttpServletRequest request) {
        return erro(HttpStatus.CONFLICT, exception.getMessage(), request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroResponse> integridade(DataIntegrityViolationException exception,
                                                    HttpServletRequest request) {
        for (Throwable causa = exception; causa != null; causa = causa.getCause()) {
            if (causa instanceof ConstraintViolationException violacao
                    && "23505".equals(violacao.getSQLState())
                    && "uk_instrutor_email".equals(violacao.getConstraintName())) {
                return erro(HttpStatus.CONFLICT, "Já existe um instrutor com este email", request);
            }
        }
        return erro(HttpStatus.INTERNAL_SERVER_ERROR, "Erro de integridade ao persistir os dados", request);
    }

    private ResponseEntity<ErroResponse> erro(HttpStatus status, String message, HttpServletRequest request) {
        return ResponseEntity.status(status)
                .body(new ErroResponse(status.value(), message, request.getRequestURI()));
    }
}
