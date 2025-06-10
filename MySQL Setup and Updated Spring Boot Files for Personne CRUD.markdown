# MySQL Setup and Updated Spring Boot Files for Personne CRUD

## MySQL Database Setup
- Install MySQL if not already installed
- Create a database named `personne_db`
- Update the Spring Boot application to connect to MySQL instead of H2

## SQL Code for Database
```sql
CREATE DATABASE personne_db;
USE personne_db;

CREATE TABLE personne (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255),
    prenom VARCHAR(255),
    adresse VARCHAR(255),
    sexe VARCHAR(50),
    date_naissance DATE,
    travaille BOOLEAN,
    salaire DOUBLE
);
```

## Updated Spring Boot Files for MySQL

### pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>demo</name>
    <description>Spring Boot CRUD for Personne</description>
    <properties>
        <java.version>17</java.version>
    </properties>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.5</version>
        <relativePath/>
    </parent>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

### src/main/resources/application.properties
```
spring.datasource.url=jdbc:mysql://localhost:3306/personne_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
```

## Steps to Use MySQL
1. Install MySQL and start the server
2. Execute the SQL code above in MySQL to create the database and table
3. Update `application.properties` with your MySQL username and password
4. Ensure the MySQL connector dependency is in `pom.xml`
5. The rest of the application (model, repository, service, controller, and HTML) remains the same as previously provided
6. Run the application and access at `http://localhost:8080`