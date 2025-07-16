# Todolist - Backend

## 1. Configuração Inicial
- [X] Inicializar projeto Spring Boot (Maven)
- [X] Configurar conexão com Postgres
- [X] Configurar Dockerfile e docker-compose

## 2. Modelagem e Entidades
- [X] Criar entidade Autor
- [X] Criar entidade Obra
- [X] Mapear relacionamento N:N entre Autor e Obra

## 3. Regras de Negócio e Validações
- [X] Nome do autor obrigatório
- [X] Sexo do autor
- [X] E-mail único e válido (não obrigatório)
- [X] Data de nascimento obrigatória e válida
- [X] País de origem obrigatório e válido
- [X] CPF obrigatório e válido se país = Brasil (único)
- [X] Nome da obra obrigatório
- [X] Descrição da obra obrigatória (máx. 240 caracteres)
- [X] Data de publicação obrigatória se data de exposição não informada
- [X] Data de exposição obrigatória se data de publicação não informada
- [X] Nunca permitir ambas datas nulas

## 4. Camadas do Projeto
- [X] Criar repositórios (JPA)
- [X] Criar serviços (camada de negócio)
- [X] Criar controladores REST (CRUD Autor e Obra)
- [X] Implementar DTOs e mapeamento
- [X] Implementar tratamento de exceções

## 5. Regras de Exclusão
- [X] Autor só pode ser excluído se não possuir obras
- [X] Obra pode ser excluída sem restrição

## 6. Extras (Diferenciais)
- [X] Autenticação (Basic Auth)
- [X] Testes unitários (JUnit)
- [X] Padrão de projeto (ex: Service, DTO, Repository)
- [X] Paginação e filtragem de obras

## 7. Finalização
- [X] Documentar endpoints (Swagger ou README)
- [X] Garantir que tudo roda via Docker
- [X] Revisar requisitos e regras 