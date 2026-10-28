package dev.eder.ahorros.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DepositoRequest(
    @NotNull(message = "El id de ahorrista es olbigatorio")
    @Positive(message = "El id de ahorrista debe ser positivo")
    Long ahorristaId,

    @NotNull(message = "La capital es obligatorio")
    @DecimalMin(value ="1000.00", message = "La capital minmo es de S/ 1000.00" )
    @DecimalMax(value = "200000.00", message = "La capital minmo es de S/ 200000.00")
    @Digits(integer = 12, fraction = 2, message ="La capital debe tener como máximo 12 dígitos enteros y 2 decimale")
    BigDecimal capital,

    @NotNull(message = "El plazo de meses es obligatorio")
    @DecimalMin(value = "3", message = "El plazo minimo es de 3 meses")
    @DecimalMax(value = "36", message = "El plazo maximo es de 36 meses")
    Integer plazoMeses
) {

}
