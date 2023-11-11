Projeto Accounts

Este é um serviço de contas para usuários de jogos.

Tecnologias utilizadas

* Java 11
* Spring Boot 2.6.6
* Spring Data JDBC
* Spring Data JPA
* Spring Validation
* Spring Web
* MySQL Connector Java
* Lombok
* Flyway Core 5.2.4
* JavaMail API 1.6.2
* Swagger 2.9.2
* Java JWT 4.3.0

Pré-requisitos

Antes de executar o projeto localmente, certifique-se de ter o seguinte instalado em sua máquina:

Java 11 ou superior
MySQL Server
Maven

Executando o projeto

Siga as etapas abaixo para executar o projeto localmente:

Clone o repositório do projeto:

git clone

Acesse o diretório do projeto:

cd accounts

Execute o seguinte comando para compilar e empacotar o projeto:

mvn package

Configure as informações de conexão com o banco de dados no arquivo application.properties ou application.yml.

Execute o seguinte comando para iniciar o projeto:

mvn spring-boot:run

O projeto estará disponível em http://localhost:8080.

Para visualizar a documentação da API com o Swagger, acesse http://localhost:8080/swagger-ui.html.

Essas são as etapas básicas para rodar o projeto localmente. Certifique-se de fornecer as configurações corretas para o banco de dados e outras dependências necessárias.
