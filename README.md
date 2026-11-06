# Catálogo Independente de Cursos

Projeto desenvolvido para a disciplina de Programação com Java e Spring Boot, seguindo a atividade prática das Aulas 00 a 14.

## Integrantes do grupo

- Pâmela Rodrigue
- Emilly Bedin
- Maria Clara Faustino

**Professor:** Jefferson

## Sobre o projeto

O Catálogo Independente de Cursos é uma API REST desenvolvida para realizar o cadastro de instrutores e cursos, permitindo consultar cursos e listar os cursos associados a cada instrutor.

O projeto utiliza um relacionamento de um para muitos (1:N), no qual um instrutor pode possuir vários cursos, mas cada curso pertence a um único instrutor.

## Tecnologias utilizadas

- Java 21
- Spring Boot 4.0.9
- Spring Data JPA
- PostgreSQL
- Liquibase
- Maven
- JUnit 5 e Mockito

## Funcionalidades

- Cadastrar instrutores.
- Cadastrar cursos vinculados a instrutores existentes.
- Consultar cursos por ID.
- Listar os cursos de um instrutor em ordem alfabética.
- Validar dados obrigatórios.
- Impedir o cadastro de instrutores com emails duplicados.
- Retornar respostas HTTP adequadas para erros de validação e recursos inexistentes.

## Endpoints da API

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/api/instrutores` | Cadastrar instrutor |
| POST | `/api/cursos` | Cadastrar curso |
| GET | `/api/cursos/{id}` | Consultar curso por ID |
| GET | `/api/instrutores/{id}/cursos` | Listar cursos de um instrutor |

## Como executar

É necessário possuir Java 21 e PostgreSQL instalados.

1. Clone o repositório.
2. Crie um banco PostgreSQL para o projeto.
3. Configure as variáveis de ambiente `DB_URL`, `DB_USERNAME` e `DB_PASSWORD`.
4. Execute a aplicação com o Maven Wrapper:

```powershell
.\mvnw.cmd spring-boot:run
```

O Liquibase é responsável por aplicar as migrations do banco de dados.

## Testes automatizados

O projeto possui testes automatizados para verificar:

- O relacionamento bidirecional entre instrutores e cursos.
- A rejeição de cursos associados a instrutores inexistentes.
- O retorno HTTP 400 para dados inválidos.
- A listagem de cursos filtrada por instrutor e ordenada por título.

Os testes de integração utilizam um banco PostgreSQL separado, configurado pelas variáveis `TEST_DB_URL`, `TEST_DB_USERNAME` e `TEST_DB_PASSWORD`.

## Organização do projeto

O código está organizado em camadas de domínio, repositórios, serviços e API REST, utilizando DTOs para as requisições e respostas.
