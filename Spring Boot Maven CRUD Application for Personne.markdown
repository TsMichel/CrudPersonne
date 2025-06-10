# Spring Boot Maven CRUD Application for Personne

## Project Structure
- **src/main/java/com/example/demo/**
  - `DemoApplication.java`: Main application class
  - **model/**
    - `Personne.java`: Entity class
  - **repository/**
    - `PersonneRepository.java`: JPA repository interface
  - **service/**
    - `PersonneService.java`: Service interface
    - `PersonneServiceImpl.java`: Service implementation
  - **controller/**
    - `PersonneController.java`: REST controller
- **src/main/resources/**
  - `application.properties`: Configuration file
  - **templates/**
    - `index.html`: Main HTML page with CRUD UI
- **pom.xml**: Maven configuration file

## Step-by-Step Guide

1. **Setup Maven Project**
   - Create a new Maven project in your IDE (e.g., IntelliJ, Eclipse)
   - Configure `pom.xml` with Spring Boot, JPA, Thymeleaf, and H2 database dependencies

2. **Configure Application**
   - Set up `application.properties` for H2 database connection and JPA settings

3. **Create Entity**
   - Define `Personne` class with fields: id, nom, prenom, adresse, sexe, dateNaissance, travaille, salaire
   - Use JPA annotations for persistence

4. **Create Repository**
   - Define `PersonneRepository` interface extending JpaRepository
   - Add custom methods for search and sorting

5. **Create Service**
   - Define `PersonneService` interface with CRUD operations
   - Implement in `PersonneServiceImpl` using the repository

6. **Create Controller**
   - Create `PersonneController` to handle HTTP requests
   - Implement endpoints for create, read, update, delete, search, and sort

7. **Create Frontend**
   - Design `index.html` with Thymeleaf for dynamic content
   - Style with Tailwind CSS via CDN for a clean, responsive UI

8. **Run Application**
   - Run `DemoApplication`
   - Access at `http://localhost:8080`
   - Use H2 console at `http://localhost:8080/h2-console` (JDBC URL: jdbc:h2:mem:testdb)

## Code

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
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
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

### src/main/java/com/example/demo/DemoApplication.java
```java
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

### src/main/java/com/example/demo/model/Personne.java
```java
package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class Personne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String adresse;
    private String sexe;
    private LocalDate dateNaissance;
    private Boolean travaille;
    private Double salaire;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Boolean getTravaille() {
        return travaille;
    }

    public void setTravaille(Boolean travaille) {
        this.travaille = travaille;
    }

    public Double getSalaire() {
        return salaire;
    }

    public void setSalaire(Double salaire) {
        this.salaire = salaire;
    }
}
```

### src/main/java/com/example/demo/repository/PersonneRepository.java
```java
package com.example.demo.repository;

import com.example.demo.model.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PersonneRepository extends JpaRepository<Personne, Long> {
    List<Personne> findByNomContainingIgnoreCase(String nom);
    List<Personne> findAllByOrderByNomAsc();
    List<Personne> findAllByOrderBySalaireAsc();
}
```

### src/main/java/com/example/demo/service/PersonneService.java
```java
package com.example.demo.service;

import com.example.demo.model.Personne;
import java.util.List;

public interface PersonneService {
    List<Personne> getAllPersonnes();
    Personne getPersonneById(Long id);
    Personne savePersonne(Personne personne);
    void deletePersonne(Long id);
    List<Personne> searchByNom(String nom);
    List<Personne> sortByNom();
    List<Personne> sortBySalaire();
}
```

### src/main/java/com/example/demo/service/PersonneServiceImpl.java
```java
package com.example.demo.service;

import com.example.demo.model.Personne;
import com.example.demo.repository.PersonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PersonneServiceImpl implements PersonneService {
    @Autowired
    private PersonneRepository personneRepository;

    @Override
    public List<Personne> getAllPersonnes() {
        return personneRepository.findAll();
    }

    @Override
    public Personne getPersonneById(Long id) {
        Optional<Personne> optional = personneRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public Personne savePersonne(Personne personne) {
        return personneRepository.save(personne);
    }

    @Override
    public void deletePersonne(Long id) {
        personneRepository.deleteById(id);
    }

    @Override
    public List<Personne> searchByNom(String nom) {
        return personneRepository.findByNomContainingIgnoreCase(nom);
    }

    @Override
    public List<Personne> sortByNom() {
        return personneRepository.findAllByOrderByNomAsc();
    }

    @Override
    public List<Personne> sortBySalaire() {
        return personneRepository.findAllByOrderBySalaireAsc();
    }
}
```

### src/main/java/com/example/demo/controller/PersonneController.java
```java
package com.example.demo.controller;

import com.example.demo.model.Personne;
import com.example.demo.service.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PersonneController {
    @Autowired
    private PersonneService personneService;

    @GetMapping("/")
    public String listPersonnes(Model model, @RequestParam(required = false) String search,
                                @RequestParam(required = false) String sort) {
        if (search != null && !search.isEmpty()) {
            model.addAttribute("personnes", personneService.searchByNom(search));
        } else if (sort != null) {
            if (sort.equals("nom")) {
                model.addAttribute("personnes", personneService.sortByNom());
            } else if (sort.equals("salaire")) {
                model.addAttribute("personnes", personneService.sortBySalaire());
            }
        } else {
            model.addAttribute("personnes", personneService.getAllPersonnes());
        }
        model.addAttribute("personne", new Personne());
        return "index";
    }

    @PostMapping("/save")
    public String savePersonne(@ModelAttribute Personne personne) {
        personneService.savePersonne(personne);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editPersonne(@PathVariable Long id, Model model) {
        model.addAttribute("personne", personneService.getPersonneById(id));
        model.addAttribute("personnes", personneService.getAllPersonnes());
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deletePersonne(@PathVariable Long id) {
        personneService.deletePersonne(id);
        return "redirect:/";
    }
}
```

### src/main/resources/application.properties
```
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=update
```

### src/main/resources/templates/index.html
```html
<!DOCTYPE html>
<html lang="fr" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Gestion des Personnes</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen flex items-center justify-center">
    <div class="w-full max-w-4xl p-6 bg-white rounded-lg shadow-lg">
        <h1 class="text-2xl font-bold text-center text-gray-800 mb-6">Gestion des Personnes</h1>

        <div class="mb-6">
            <form th:action="@{/save}" th:object="${personne}" method="post" class="space-y-4">
                <input type="hidden" th:field="*{id}">
                <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <input th:field="*{nom}" placeholder="Nom" class="w-full p-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500">
                    <input th:field="*{prenom}" placeholder="Prénom" class="w-full p-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500">
                    <input th:field="*{adresse}" placeholder="Adresse" class="w-full p-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500">
                    <select th:field="*{sexe}" class="w-full p-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500">
                        <option value="">Sélectionner Sexe</option>
                        <option value="Homme">Homme</option>
                        <option value="Femme">Femme</option>
                    </select>
                    <input th:field="*{dateNaissance}" type="date" class="w-full p-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500">
                    <select th:field="*{travaille}" class="w-full p-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500">
                        <option value="">Travaille?</option>
                        <option value="true">Oui</option>
                        <option value="false">Non</option>
                    </select>
                    <input th:field="*{salaire}" type="number" step="0.01" placeholder="Salaire" class="w-full p-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500">
                </div>
                <button type="submit" class="w-full bg-blue-500 text-white p-2 rounded-md hover:bg-blue-600">Enregistrer</button>
            </form>
        </div>

        <div class="mb-6 flex gap-4">
            <form th:action="@{/}" method="get" class="flex-1">
                <input name="search" placeholder="Rechercher par nom" class="w-full p-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500">
                <button type="submit" class="mt-2 w-full bg-gray-500 text-white p-2 rounded-md hover:bg-gray-600">Rechercher</button>
            </form>
            <div class="flex gap-2">
                <a th:href="@{/?sort=nom}" class="bg-green-500 text-white p-2 rounded-md hover:bg-green-600">Trier par Nom</a>
                <a th:href="@{/?sort=salaire}" class="bg-green-500 text-white p-2 rounded-md hover:bg-green-600">Trier par Salaire</a>
            </div>
        </div>

        <table class="w-full border-collapse">
            <thead>
                <tr class="bg-gray-200">
                    <th class="p-2 border">ID</th>
                    <th class="p-2 border">Nom</th>
                    <th class="p-2 border">Prénom</th>
                    <th class="p-2 border">Adresse</th>
                    <th class="p-2 border">Sexe</th>
                    <th class="p-2 border">Date de Naissance</th>
                    <th class="p-2 border">Travaille</th>
                    <th class="p-2 border">Salaire</th>
                    <th class="p-2 border">Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr th:each="personne : ${personnes}" class="hover:bg-gray-50">
                    <td class="p-2 border" th:text="${personne.id}"></td>
                    <td class="p-2 border" th:text="${personne.nom}"></td>
                    <td class="p-2 border" th:text="${personne.prenom}"></td>
                    <td class="p-2 border" th:text="${personne.adresse}"></td>
                    <td class="p-2 border" th:text="${personne.sexe}"></td>
                    <td class="p-2 border" th:text="${personne.dateNaissance}"></td>
                    <td class="p-2 border" th:text="${personne.travaille} ? 'Oui' : 'Non'"></td>
                    <td class="p-2 border" th:text="${personne.salaire}"></td>
                    <td class="p-2 border flex gap-2">
                        <a th:href="@{/edit/{id}(id=${personne.id})}" class="bg-yellow-500 text-white p-1 rounded-md hover:bg-yellow-600">Modifier</a>
                        <a th:href="@{/delete/{id}(id=${personne.id})}" class="bg-red-500 text-white p-1 rounded-md hover:bg-red-600">Supprimer</a>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</body>
</html>
```