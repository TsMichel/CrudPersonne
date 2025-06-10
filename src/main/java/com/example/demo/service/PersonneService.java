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