package dev.eder.ahorros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.eder.ahorros.model.Deposito;

public interface DepositoRepo extends JpaRepository<Deposito, Long>{

    List<Deposito> findByAhorristaId(Long ahorristaId);

    boolean existsByAhorristaId(Long ahorristaId);
    
}
