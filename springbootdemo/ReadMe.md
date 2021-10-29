# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.6/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.6/maven-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.5.6/reference/htmlsingle/#using-boot-devtools)

This sample application is a very basic SpringBoot App which demonstrates below :

* [Basic HelloWorld SpringBoot App](Creating a basic HelloWorld SpringBoot App )
* [Simple Get Controller](Simple Get Controller which prints the simple string as success response)
* [Tomcat Port](Default tomcat port runs on the 8080 but we can change using server.port property in the application.properties file.)
* [Context Load Test](Spring context load can be test using test cases using the annotation SpringBootTest.)

This simple controller can be tested using GET method in postman and using below URL :
http://localhost:8888/springboot/helloSpringBoot

Note: I have changed the Tomcat Port to 8888. You can change that port to port you want to use. 
If the property server.port has been set to 0, tomcat will run on any random port.