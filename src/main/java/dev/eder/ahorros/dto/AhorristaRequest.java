package dev.eder.ahorros.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AhorristaRequest(
    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "^\\d{8}$", message = "El DNI tiene que tener el formato de 8 digitos")
    String dni,

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre solo puede tener maximo de 100 caracteres")
    String nombres,
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato debe de ser email")
    @Size(max = 100, message = "El email solo puede tener maximo de 100 caracteres")
    String email,

    @NotBlank(message = "El celular es obligatorio")
    @Pattern(regexp = "^9\\d{8}$", message = "El celular tiene que comenzar en 9 y tener 8 digito")
    String celular

) {

}
