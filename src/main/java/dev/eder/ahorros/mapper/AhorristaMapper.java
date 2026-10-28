package dev.eder.ahorros.mapper;

import dev.eder.ahorros.dto.AhorristaRequest;
import dev.eder.ahorros.dto.AhorristaResponse;
import dev.eder.ahorros.model.Ahorrista;

public final class AhorristaMapper {

    public AhorristaMapper(){}

    public static Ahorrista toEntity(AhorristaRequest request){
        Ahorrista ahorrista = new  Ahorrista();
        actualizarEntidad(request, ahorrista);
        return ahorrista;
    }

    public static void actualizarEntidad(AhorristaRequest request, Ahorrista ahorrista){
        ahorrista.setDni(request.dni().trim());
        ahorrista.setNombres(request.nombres().trim());
        ahorrista.setEmail(normalizarEmail(request.email()));
        ahorrista.setCelular(request.celular());
    }


    public static AhorristaResponse toResponse(Ahorrista ahorrista){
        return AhorristaResponse.builder()
        .id(ahorrista.getId())
        .descripcion( " (" + ahorrista.getDni() + " " +  ahorrista.getNombres() + ")")
        .email(ahorrista.getEmail())
        .celular(ahorrista.getCelular())
        .fechaRegistro(ahorrista.getFechaRegistro())
        .build();
    }

    public static String normalizarEmail(String email){
        return email.trim().toLowerCase();
    }
}
