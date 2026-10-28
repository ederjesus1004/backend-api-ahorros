package dev.eder.ahorros.dto;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record AhorristaResponse(
    Long id,
    String descripcion,
    String email,
    String celular,
    LocalDateTime fechaRegistro
) {

}
