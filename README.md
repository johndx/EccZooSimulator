# Getting Started


## Installation Instructions
Having extracted the App from github, perform the following steps to build, configure and execute the App:

> mvn clean install

>./mvnw spring-boot:run

## Testing Strategy

## Unit Testing

- Junit Tests for Domain classes are not required.
- Spring Junit Tests for the integration tier are provided.

## Integration Testing

Mock MVC Tests have been generated for the Web/Rest Tier, to exercise the REST API ( and subsequent tiers) , by using Mock API REST requests to emulate real REST requests to the API.
Invoke the tests vai maven with;

> mvn test



##  POSTMAN
Postman has been used as the REST Client and the following requests were configured for testing the api;

- GET to http://localhost:8080/api/zoo/animals
- POST to http://localhost:8080/api/zoo/animals/feed
- GET to http://localhost:8080/console/zoo

These will respectively feed the Animals in the Zoo, report on their status and display the console.

##  CONSOLE
The Zoo application can also be accessd via a management console, that allows the current state of the anaimals in the Zoo to be viewed, togther with the option to 'Feed' the animals.
The console is accessed from the URL;


- http://localhost:8080/console/zoo


### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.5/maven-plugin/reference/html/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#web)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#data.sql.jpa-and-spring-data)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)



Built in IntelliJ
