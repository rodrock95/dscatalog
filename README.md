# dscatalog
https://github.com/rodrock95/dscatalog/blob/master/LICENSE

# Sobre o projeto
O dscatalog é um sistema e-commerce de produtos e categorias sendo uma aplicação web REST que permite aos usuários explorar 
e comprar diversos produtos disponíveis em diferentes categorias. Nessa API, somente as requisições de leitura (GET) de categorias e produtos são públicas. Os demais endpoints estão disponíveis apenas para usuários ADMIN. É um projeto desenvolvido por meio da linguagem Java com uso do framework Spring Boot.

# Modelagem conceitual UML

![dscatalog](https://github.com/rodrock95/dscatalog/assets/79290866/95332f3b-68f0-4907-98ef-0fdd22848857)

# Infraestrutura do dscatalog

Neste projeto, algumas tecnologias a nível backend foram utilizadas para compor o projeto, bem como a descrição da infraestrutura:

- CRUD: Java, Spring Boot, Maven, H2, Postgresql, Postman, Spring Data JPA, dentre outras.
Aqui foi criado monorepositório Git, organização do projeto em camadas, criação das entidades, perfis de projeto, seeding da base de dados, criação web services REST, padrão DTO, CRUD completo da entidade produtos, tratamento de exceções, dados de auditoria e paginação de dados. O Postman foi utilizado para testar as requisições.

- Testes automatizados: JUnit5, Mockito, MockBean, dentre outras.

Toda fundamentação de testes automatizados, incluindo TDD, JUnit com Spring Boot, teste dos repositories, services e controllers (resources), testes de integração, mock das dependências com Mockito e MockBean e realização das assertions.

- Validação e segurança: Bean Validation, Spring Security, JWT, OAuth2, dentre outras.

Dependência Bean Validation para validação de dados, autenticação e autorização com OAuth2 e JWT.

- Domínio e ORM, autorizações: Modelo de domínio, JPA, SQL seed, dentre outras.

A infraestrutura do projeto conta com ORM e seed de banco de dados.

- Consultas personalizadas ao banco de dados: Spring Data JPA, JPQL, SQL, dentre outras.

Foi pensada a realização de consultas ao banco de dados com JPA e JPQL, problema das N+1 consultas e consultas nativas com SQL.

- Docker, implantação, CI/CD: Maven, Docker.

Imagens e conteiners são fundamentos do Docker, importantes para compor o resultado final do projeto.
