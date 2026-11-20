# Loja de Eletrônicos - Backend

Trabalho final da disciplina de Backend: Web Services (Opção 1 - Estendendo os serviços).

O projeto estende o servidor de autenticação visto em aula (Kotlin, Spring Boot, JPA, JWT) com os serviços de uma loja de eletrônicos.

## Integrantes

- Nome: Mariana Cordeiro Calado Silva
- Nome: Mateus Grabarski de Mantova

## Vídeo

Link do vídeo no YouTube: https://youtu.be/B8d3s4oKUpw

## Tecnologias

- Kotlin, Java 21 e Spring Boot 4
- Spring Data JPA com banco H2 (em memória)
- Spring Security com JWT
- Swagger / OpenAPI
- Gradle

## Como rodar

1. Abra um terminal na pasta do projeto.
2. Execute:

   ```
   ./gradlew.bat bootRun
   ```

3. Acesse a documentação (Swagger) em http://localhost:8080/api/

Para usar os endpoints protegidos, faça login em `POST /users/login` com o administrador criado automaticamente na inicialização (`admin@authserver.com` / `admin`), copie o `token` e informe-o no botão **Authorize** do Swagger.

O banco é em memória: os dados são apagados sempre que o servidor é reiniciado.

## Classes

| Classe | Relacionamento |
|---|---|
| `Category` | um para muitos com `Product` |
| `Product` | muitos para um com `Category` |

## Endpoints

| Método | Rota | Acesso |
|---|---|---|
| GET | `/categories` | Público |
| GET | `/categories/{id}` | Público |
| POST | `/categories` | Autenticado |
| PUT | `/categories/{id}` | Autenticado |
| DELETE | `/categories/{id}` | Administrador |
| GET | `/products` | Público |
| GET | `/products/{id}` | Público |
| POST | `/products` | Autenticado |
| PUT | `/products/{id}` | Autenticado |
| DELETE | `/products/{id}` | Administrador |

### Consulta com filtro e ordenação

`GET /products` aceita os parâmetros de query abaixo, todos opcionais:

| Parâmetro | Descrição |
|---|---|
| `categoryId` | Filtra pela categoria |
| `minPrice` | Preço mínimo |
| `maxPrice` | Preço máximo |
| `sortBy` | Campo de ordenação: `name` (padrão) ou `price` |
| `sortDir` | Direção: `ASC` (padrão) ou `DESC` |

Exemplo: `/products?categoryId=1&minPrice=1000&sortBy=price&sortDir=DESC`

## Logs

Os eventos de criação, atualização e exclusão são gravados na pasta `logs/`.
# Trabalho-final---Backend
# Trabalho-Final-Backend
# Trabalho-Final-Backend
