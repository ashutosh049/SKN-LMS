# spring-boot-starter-parent


This project serves as parent project for other child modules intended to use spring boot dependencies. Each release of Spring Boot is associated with a base version of the Spring Framework.
it's highly recommended not to specify its version.

### Uses
To use this pom as a `parent` include the following tag as:


```xml
<parent>
    <groupId>skn.lms</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>1.0-SNAPSHOT</version>
</parent>
```    

### System Requirements
[Spring Boot 2.3.2.RELEASE](https://github.com/spring-projects/spring-boot/releases/tag/v2.3.2.RELEASE) requires [Java 8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html) or higher.
[Spring Framework 5.2.8.RELEASE](https://docs.spring.io/spring/docs/5.2.8.RELEASE/spring-framework-reference) or above is also required.

| Build Tool  | Version |
| ------------- | ------------- |
| Maven  | 3.3+  |
| Gradle  | 6 (6.3 or later). 5.6.x is also supported but in a deprecated form  |
