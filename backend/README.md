# Backend - Desafio Java JR

## Como rodar com Docker

1. Gere o arquivo JAR do projeto (necessário Maven):
   ```bash
   docker-compose up --build
   ```
2. Acesse a API em: http://localhost:8080

- O banco de dados H2 estará disponível em memória.
- O console do H2 pode ser acessado em: http://localhost:8080/h2-console
- Usuário e senha do sistema: `admin` / `admin` (Basic Auth)

## Endpoints principais

- `/api/autores` (CRUD de autores)
- `/api/obras` (CRUD de obras, paginação e filtragem)

## Documentação Swagger

Acesse a documentação interativa em:
- http://localhost:8080/swagger-ui.html

## Testes

Para rodar os testes unitários:
```bash
./mvnw test
```

## Observações
- Todos os endpoints exigem autenticação Basic Auth.
- Para associar autores e obras, utilize os campos `obrasIds` (em Autor) e `autoresIds` (em Obra) nos DTOs. 