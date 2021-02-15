# Roteiro Entrevista Spring Boot

## Requisitos da entrega

* Cadastrar cidade
* Cadastrar cliente
* Consultar cidade pelo nome
* Consultar cidade pelo estado
* Consultar cliente pelo nome
* Consultar cliente pelo Id
* Remover cliente
* Alterar o nome do cliente

Considere o cadastro com dados básicos: 
* Cidades: nome e estado
* Cliente: nome completo, sexo, data de nascimento, idade e cidade onde mora.

## Rodar o projeto com o Docker ou localmente com o ambiente configurado

### Docker

Pré Requisitos
- [Docker](https://www.docker.com)
- [Docker Compose](https://docs.docker.com/compose/install/)

Basta executar o comando:

```
docker-compose up --build -d
```

A API e o Banco de Dados já estarão configurados e prontos para utilização.

### Localmente com ambiente configurado

Primeiramente será necessário configurar as variáveis de ambiente. Para isso, no arquivo que se encontra no diretório "src\main\resources\application-dev.properties" há um exemplo com variáveis de ambiente. Basta copiar os dados desse arquivo e colar outro arquivo do mesmo diretório chamado "application.properties".

Em seguida, será necessário configurar um banco de dados localmente e informar as credenciais corretas no arquivo "application.properties".

A API pode ser iniciada clicando com o botão direito do mouse e "Run" na classe "src\main\java\com\compassouol\backendrecruitment\BackendRecruitmentApplication.java".

### Link e documentação da API

Após rodar o projeto com o Docker ou com o ambiente configurado localmente, a API estará disponível em http://localhost:8080/ e a sua documentação com o Swagger estará disponível em http://localhost:8080/swagger-ui/#/

## Banco de Dados

As migrações e documentação do banco de dados se encontram no diretório "src\main\resources\database" desse projeto.

![DB](https://user-images.githubusercontent.com/37259280/107872952-1b284480-6e8d-11eb-831d-2aa134ea2855.png)

## Ferramentas e outras explicações

### Swagger

o Swagger é uma ferramenta para documentação de API's. Ela foi utilizada nesse projeto pela sua facilidade de integração e também pela garantia de que a API sempre estará com sua documentação de endpoints atualizada, uma vez que não é necessário salvar tal documentação em arquivos externos com risco de esquecimento, por exemplo.

Link para documentação: https://swagger.io/

### Flyway DB

O Flyway é uma ferramenta de migração de banco de dados. Ela foi utilizada nesse projeto para tornar explícitas todas as alterações no banco de dados como: criação de tabelas, inserções, alterações em atributos, etc...

Após configurar as variáveis de ambiente necessárias, a ferramenta buscará por códigos .sql para realizar as migrações. Tais códigos podem ser encontrados no diretório "src\main\resources\database\migrations".

Link para documentação: https://flywaydb.org/

### Lombok

O Lombok é uma biblioteca Java focada em produtividade e redução de código. Ela foi utilizada nesse projeto para reduzir "códigos sujos" como getters, setters, toString, equals, etc...

Link para documentação: https://projectlombok.org/

### Por que não há o cadastro da idade do cliente?

Porque como já temos a data de nascimento, adicionar mais um atributo de idade seria redundância, uma vez que com base na data de nascimento já é possível obter a idade do cliente.

A idade do cliente é retornada nos endpoints de criação e listagens.