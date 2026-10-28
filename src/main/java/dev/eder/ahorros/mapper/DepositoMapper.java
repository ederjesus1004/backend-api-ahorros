package dev.eder.ahorros.mapper;

import dev.eder.ahorros.calculo.CalculadoraDeposito;
import dev.eder.ahorros.dto.DepositoRequest;
import dev.eder.ahorros.dto.DepositoResponse;
import dev.eder.ahorros.model.Ahorrista;
import dev.eder.ahorros.model.Deposito;

public final class DepositoMapper {

    public DepositoMapper(){}

    public static Deposito toEntity(DepositoRequest request, Ahorrista ahorrista){
        Deposito deposito = new Deposito();
        deposito.setAhorrista(ahorrista);
        deposito.setCapital(request.capital());
        deposito.setPlazoMeses(request.plazoMeses());
        return deposito;
    }

    public static DepositoResponse toResponse(Deposito deposito){
        return DepositoResponse.builder()
        .id(deposito.getId())
        .ahorristaId(deposito.getAhorrista().getId())
        .ahorrista(deposito.getAhorrista().getNombres())
        .capital(deposito.getCapital())
        .plazoMeses(deposito.getPlazoMeses())
        .tasaMensual(deposito.getTasaMensual())
        .montoFinal(deposito.getMontoFinal())
        .interesGanado(deposito.getInteresGanado())
        .rendimientoPorcentaje(CalculadoraDeposito.calcularRendimiento(deposito.getInteresGanado(), deposito.getCapital()))
        .fechaRegistro(deposito.getFechaRegistro())
        .build();
    }
}
