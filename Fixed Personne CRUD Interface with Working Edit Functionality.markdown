## Fixed Personne CRUD Interface with Working Edit Functionality

### Updated Controller
Le contrôleur est modifié pour mieux gérer l'édition en passant les données de la personne au formulaire dans le popup.

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
        model.addAttribute("isEdit", false);
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String editPersonne(@PathVariable Long id, Model model) {
        model.addAttribute("personne", personneService.getPersonneById(id));
        model.addAttribute("personnes", personneService.getAllPersonnes());
        model.addAttribute("isEdit", true);
        return "index";
    }

    @PostMapping("/save")
    public String savePersonne(@ModelAttribute Personne personne) {
        personneService.savePersonne(personne);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deletePersonne(@PathVariable Long id) {
        personneService.deletePersonne(id);
        return "redirect:/";
    }
}
```

### Updated Interface
L'interface est ajustée pour utiliser le popup pour la modification, avec un titre dynamique et un indicateur pour distinguer l'ajout de la modification.

### src/main/resources/templates/index.html
```html
<!DOCTYPE html>
<html lang="fr" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Gestion des Personnes</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #1e3a8a, #000000);
            font-family: 'Poppins', sans-serif;
        }
        .container {
            background: rgba(255, 255, 255, 0.95);
            border-radius: 16px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.4);
        }
        .popup {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.6);
            justify-content: center;
            align-items: center;
            z-index: 1000;
        }
        .popup-content {
            background: white;
            padding: 2rem;
            border-radius: 12px;
            width: 90%;
            max-width: 500px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.5);
            transform: scale(0.9);
            transition: transform 0.3s ease;
        }
        .popup.active {
            display: flex;
        }
        .popup-content:hover {
            transform: scale(1);
        }
        .btn {
            transition: all 0.3s ease;
        }
        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.5);
        }
        table tr {
            transition: all 0.2s ease;
        }
        table tr:hover {
            background: rgba(30, 58, 138, 0.2);
            transform: scale(1.01);
        }
        h1 {
            background: linear-gradient(to right, #1e3a8a, #000000);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
        }
        select, input {
            border: 1px solid #1e3a8a;
        }
    </style>
</head>
<body class="min-h-screen flex items-center justify-center p-4">
    <div class="container w-full max-w-5xl p-8">
        <h1 class="text-4xl font-bold text-center mb-8">Gestion des Personnes</h1>

        <div class="mb-6 flex justify-end">
            <button onclick="document.getElementById('popupForm').classList.add('active')" 
                    class="btn bg-blue-900 text-white p-3 rounded-lg hover:bg-black">
                Nouveau
            </button>
        </div>

        <div id="popupForm" class="popup" th:classappend="${isEdit} ? 'active' : ''">
            <div class="popup-content">
                <h2 th:text="${isEdit} ? 'Modifier Personne' : 'Ajouter Personne'" class="text-2xl font-bold text-blue-900 mb-4"></h2>
                <form th:action="@{/save}" th:object="${personne}" method="post" class="space-y-4">
                    <input type="hidden" th:field="*{id}">
                    <div class="grid grid-cols-1 gap-4">
                        <input th:field="*{nom}" placeholder="Nom" class="w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-900">
                        <input th:field="*{prenom}" placeholder="Prénom" class="w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-900">
                        <input th:field="*{adresse}" placeholder="Adresse" class="w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-900">
                        <select th:field="*{sexe}" class="w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-900">
                            <option value="">Sélectionner Sexe</option>
                            <option value="Homme">Homme</option>
                            <option value="Femme">Femme</option>
                        </select>
                        <input th:field="*{dateNaissance}" type="date" class="w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-900">
                        <select th:field="*{travaille}" class="w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-900">
                            <option value="">Travaille?</option>
                            <option value="true">Oui</option>
                            <option value="false">Non</option>
                        </select>
                        <input th:field="*{salaire}" type="number" step="0.01" placeholder="Salaire" class="w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-900">
                    </div>
                    <div class="flex gap-4">
                        <button type="submit" class="btn w-full bg-blue-900 text-white p-3 rounded-lg hover:bg-black">Enregistrer</button>
                        <button type="button" onclick="document.getElementById('popupForm').classList.remove('active')" 
                                class="btn w-full bg-black text-white p-3 rounded-lg hover:bg-blue-900">Annuler</button>
                    </div>
                </form>
            </div>
        </div>

        <div class="mb-6 flex items-center gap-4 justify-end">
            <form th:action="@{/}" method="get" class="flex items-center gap-4">
                <input name="search" placeholder="Rechercher par nom" class="w-1/3 p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-900">
                <button type="submit" class="btn bg-blue-900 text-white p-3 rounded-lg hover:bg-black">
                    <i class="fas fa-search"></i>
                </button>
                <select onchange="window.location.href=this.value" class="w-1/4 p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-900">
                    <option value="">Trier par</option>
                    <option th:value="@{/?sort=nom}">Nom</option>
                    <option th:value="@{/?sort=salaire}">Salaire</option>
                </select>
            </form>
        </div>

        <table class="w-full border-collapse">
            <thead>
                <tr class="bg-blue-900 text-white">
                    <th class="p-3 border">ID</th>
                    <th class="p-3 border">Nom</th>
                    <th class="p-3 border">Prénom</th>
                    <th class="p-3 border">Adresse</th>
                    <th class="p-3 border">Sexe</th>
                    <th class="p-3 border">Date de Naissance</th>
                    <th class="p-3 border">Travaille</th>
                    <th class="p-3 border">Salaire</th>
                    <th class="p-3 border">Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr th:each="personne : ${personnes}" class="border-b">
                    <td class="p-3 border" th:text="${personne.id}"></td>
                    <td class="p-3 border" th:text="${personne.nom}"></td>
                    <td class="p-3 border" th:text="${personne.prenom}"></td>
                    <td class="p-3 border" th:text="${personne.adresse}"></td>
                    <td class="p-3 border" th:text="${personne.sexe}"></td>
                    <td class="p-3 border" th:text="${personne.dateNaissance}"></td>
                    <td class="p-3 border" th:text="${personne.travaille} ? 'Oui' : 'Non'"></td>
                    <td class="p-3 border" th:text="${personne.salaire}"></td>
                    <td class="p-3 border flex gap-2">
                        <a th:href="@{/edit/{id}(id=${personne.id})}" class="btn bg-blue-900 text-white p-2 rounded-lg hover:bg-black">
                            <i class="fas fa-edit"></i>
                        </a>
                        <a th:href="@{/delete/{id}(id=${personne.id})}" class="btn bg-blue-900 text-white p-2 rounded-lg hover:bg-black">
                            <i class="fas fa-trash"></i>
                        </a>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</body>
</html>
```

## Explication
- **Contrôleur** : J'ai ajouté un attribut `isEdit` au modèle pour indiquer si le formulaire est en mode édition ou ajout. La méthode `editPersonne` charge les données de la personne et active le popup avec `isEdit` à `true`.
- **Interface** : Le popup s'affiche automatiquement en mode édition grâce à `th:classappend="${isEdit} ? 'active' : ''"`. Le titre du formulaire change dynamiquement avec `th:text="${isEdit} ? 'Modifier Personne' : 'Ajouter Personne'"` pour plus de clarté.
- **Test** : Cliquez sur l'icône "Modifier" pour une personne. Le popup s'ouvrira avec les champs pré-remplis. Modifiez les valeurs, cliquez sur "Enregistrer", et la page se rafraîchira avec les données mises à jour.

Remplacez les fichiers correspondants dans votre projet et testez à nouveau. Si vous rencontrez encore des problèmes, décrivez-moi ce qui ne fonctionne pas (par exemple, le popup ne s'affiche pas, les champs ne sont pas pré-remplis, etc.).