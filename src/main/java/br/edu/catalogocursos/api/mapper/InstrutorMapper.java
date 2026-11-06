package br.edu.catalogocursos.api.mapper;

import br.edu.catalogocursos.api.dto.InstrutorRequest;
import br.edu.catalogocursos.api.dto.InstrutorResponse;
import br.edu.catalogocursos.domain.Instrutor;

public class InstrutorMapper {

    public static Instrutor toEntity(InstrutorRequest request) {
        Instrutor instrutor = new Instrutor();
        instrutor.setNome(request.nome());
        instrutor.setEmail(request.email());
        return instrutor;
    }

    public static InstrutorResponse toResponse(Instrutor instrutor) {
        return new InstrutorResponse(instrutor.getId(), instrutor.getNome(), instrutor.getEmail());
    }
}
