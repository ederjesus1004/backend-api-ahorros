package dev.eder.ahorros.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.eder.ahorros.dto.AhorristaRequest;
import dev.eder.ahorros.dto.AhorristaResponse;
import dev.eder.ahorros.service.AhorristaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ahorrista")
@RequiredArgsConstructor 
public class AhorristaController {

    private final AhorristaService ahorristaService;
    

    @GetMapping
    public List<AhorristaResponse>listar(){
        return ahorristaService.listar();
    }

    @GetMapping("/{id}")
    public AhorristaResponse buscarPorId(@PathVariable Long id){
        return ahorristaService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AhorristaResponse crear(@Valid @RequestBody AhorristaRequest request){
        return ahorristaService.crear(request);
    }

    @PutMapping("/{id}")
    public AhorristaResponse actualizar(@PathVariable Long id, @Valid @RequestBody AhorristaRequest request){
        return ahorristaService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id){
        ahorristaService.eliminar(id);
    }

}
