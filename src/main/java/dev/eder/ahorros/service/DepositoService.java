package dev.eder.ahorros.service;

import java.util.List;

import dev.eder.ahorros.dto.DepositoRequest;
import dev.eder.ahorros.dto.DepositoResponse;

public interface DepositoService {

    List<DepositoResponse> listar();

    DepositoResponse buscarPorId(Long id);

    List<DepositoResponse> buscarPorAhorrista(Long ahorristaId);

    DepositoResponse crear(DepositoRequest request);

    void eliminar (Long id);
}
