# Market Place API.

## Technology Stack.
 - SpringBoot.
 - Hibernate ORM.
 - H2 database.
 - HikariCP.
 - Jersey.
 - JSON.
 - Lombok.
 - Logback.

## Prerequisites.
 - JDK 8.
 - Maven.

## How to Run.

``` mvn clean install -DskipTest```

``` mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)```

``` docker build -t francis/market-place . ```

``` docker run -p 8080:8080 -t francis/market-place ```

## H2 console Access.
```http://localhost:8080/h2-console```

### References.
 ```spring.io```
