package br.edu.catalogocursos.api.dto;

public record ErroResponse(int status, String message, String path) {
}
