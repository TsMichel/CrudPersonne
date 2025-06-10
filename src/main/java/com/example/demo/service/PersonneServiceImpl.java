
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