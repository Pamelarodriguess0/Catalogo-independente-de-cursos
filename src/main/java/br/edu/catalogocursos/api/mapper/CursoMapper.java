package br.edu.catalogocursos.api.mapper;

import br.edu.catalogocursos.api.dto.CursoRequest;
import br.edu.catalogocursos.api.dto.CursoResponse;
import br.edu.catalogocursos.api.dto.CursoResumoResponse;
import br.edu.catalogocursos.api.dto.InstrutorResumoResponse;
import br.edu.catalogocursos.domain.Curso;
import br.edu.catalogocursos.domain.Instrutor;

public class CursoMapper {

    public static Curso toEntity(CursoRequest request) {
        Curso curso = new Curso();
        curso.setTitulo(request.titulo());
        curso.setCargaHoraria(request.cargaHoraria());
        curso.setNivel(request.nivel());
        return curso;
    }

    public static CursoResponse toResponse(Curso curso) {
        Instrutor instrutor = curso.getInstrutor();
        return new CursoResponse(curso.getId(), curso.getTitulo(), curso.getCargaHoraria(),
                curso.getNivel(), new InstrutorResumoResponse(instrutor.getId(), instrutor.getNome()));
    }

    public static CursoResumoResponse toResumoResponse(Curso curso) {
        return new CursoResumoResponse(curso.getId(), curso.getTitulo(),
                curso.getCargaHoraria(), curso.getNivel());
    }
}
