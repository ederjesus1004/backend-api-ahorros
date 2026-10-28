package dev.eder.ahorros.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.eder.ahorros.model.Ahorrista;

public interface AhorristaRepo extends JpaRepository<Ahorrista, Long> {

    boolean existsByDni(String dni);

    boolean existsByDniAndIdNot(String dni, Long id);

    /* --------------------------------------------------------------- */
    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);
    /* --------------------------------------------------------------- */

    boolean existsByCelular(String celular);

    boolean existsByCelularAndIdNot(String celular, Long Id);
}
