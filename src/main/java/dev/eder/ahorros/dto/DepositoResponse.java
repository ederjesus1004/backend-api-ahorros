package dev.eder.ahorros.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record DepositoResponse(

    Long id,
    Long ahorristaId,
    String ahorrista,
    BigDecimal capital,
    Integer plazoMeses,
    BigDecimal tasaMensual,
    BigDecimal montoFinal,
    BigDecimal interesGanado,
    BigDecimal rendimientoPorcentaje,
    LocalDateTime fechaRegistro
) {

}
