package dev.eder.ahorros.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.eder.ahorros.dto.DepositoRequest;
import dev.eder.ahorros.dto.DepositoResponse;
import dev.eder.ahorros.service.DepositoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/api/deposito")
@RequiredArgsConstructor 
public class DepositoController {

    private final DepositoService depositoService;

    @GetMapping
    public List<DepositoResponse> listar(){
        return depositoService.listar();
    }
    
    @GetMapping("/{id}")
    public DepositoResponse buscarPorId(@PathVariable Long id){
        return depositoService.buscarPorId(id);
    }

    @GetMapping("/ahorrista/{ahorristaId}")
    public List<DepositoResponse> buscarPorAhorrista(@PathVariable Long ahorristaId){
        return depositoService.buscarPorAhorrista(ahorristaId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepositoResponse crear(@Valid  @RequestBody DepositoRequest request){
        return depositoService.crear(request);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        depositoService.eliminar(id);
    }
}
