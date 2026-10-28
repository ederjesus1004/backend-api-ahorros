package dev.eder.ahorros.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.eder.ahorros.calculo.CalculadoraDeposito;
import dev.eder.ahorros.dto.DepositoRequest;
import dev.eder.ahorros.dto.DepositoResponse;
import dev.eder.ahorros.exception.RecursoNoEncontradoException;
import dev.eder.ahorros.exception.ReglaNegocioException;
import dev.eder.ahorros.mapper.DepositoMapper;
import dev.eder.ahorros.model.Ahorrista;
import dev.eder.ahorros.model.Deposito;
import dev.eder.ahorros.repository.AhorristaRepo;
import dev.eder.ahorros.repository.DepositoRepo;
import dev.eder.ahorros.service.DepositoService;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class DepositoServiceImpl implements DepositoService{

    private final DepositoRepo depositoRepo;
    private final AhorristaRepo ahorristaRepo;

    @Override
    @Transactional(readOnly = true)
    public List<DepositoResponse> listar(){
        return depositoRepo.findAll()
        .stream()
        .map(DepositoMapper::toResponse)
        .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DepositoResponse buscarPorId(Long id){
        return DepositoMapper.toResponse(buscarDeposito(id));
    }

    @Override 
    @Transactional(readOnly = true)
    public List<DepositoResponse> buscarPorAhorrista(Long ahorristaId){
        if (!ahorristaRepo.existsById(ahorristaId)) {
            throw new RecursoNoEncontradoException("No existe un ahorrista con este id " + ahorristaId);
        }

        return depositoRepo.findByAhorristaId(ahorristaId)
        .stream()
        .map(DepositoMapper::toResponse)
        .toList();
    }

    @Override 
    @Transactional
    public DepositoResponse crear (DepositoRequest request){
        Ahorrista ahorrista = buscarAhorrista(request.ahorristaId());

        if (!CalculadoraDeposito.esMultiploDe100(request.capital())) {
            throw new ReglaNegocioException("El capital debe ser múltiplo de 100. Recibido: S/ " + request.capital());
        }

        BigDecimal tasaMensual = CalculadoraDeposito.obtenerTasaPorPlazo(request.plazoMeses());
        BigDecimal montoFinal = CalculadoraDeposito.calcularMontoFinal(request.capital(), tasaMensual, request.plazoMeses());
        BigDecimal interesGanado = CalculadoraDeposito.calcularInteres(montoFinal, request.capital());

        Deposito deposito = DepositoMapper.toEntity(request, ahorrista);
        deposito.setTasaMensual(tasaMensual);
        deposito.setMontoFinal(montoFinal);
        deposito.setInteresGanado(interesGanado);

        return DepositoMapper.toResponse(depositoRepo.save(deposito));
    }

    @Override
    @Transactional
    public void eliminar(Long id){
        Deposito deposito = buscarDeposito(id);
        depositoRepo.delete(deposito);
    }


    private Deposito buscarDeposito(Long id){
        return depositoRepo.findById(id)
        .orElseThrow(()-> new RecursoNoEncontradoException("No existe un deposito con id " + id));
    }


    private Ahorrista buscarAhorrista(Long id){
        return ahorristaRepo.findById(id)
        .orElseThrow(()-> new RecursoNoEncontradoException("No existe un Ahorrista con id "+ id));
    }
}
