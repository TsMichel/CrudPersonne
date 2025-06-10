package com.example.demo.repository;
import com.example.demo.model.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PersonneRepository extends JpaRepository<Personne, Long> {
    List<Personne> findByNomContainingIgnoreCase(String nom);
    List<Personne> findAllByOrderByNomAsc();
    List<Personne> findAllByOrderBySalaireAsc();
}