package dev.eder.ahorros.service;

import java.util.List;

import dev.eder.ahorros.dto.AhorristaRequest;
import dev.eder.ahorros.dto.AhorristaResponse;

public interface AhorristaService {

    List<AhorristaResponse> listar();

    AhorristaResponse buscarPorId(Long id);

    AhorristaResponse crear (AhorristaRequest request);

    AhorristaResponse actualizar(Long id, AhorristaRequest request);

    void eliminar(Long id);
}
