package dev.eder.ahorros.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.eder.ahorros.dto.AhorristaRequest;
import dev.eder.ahorros.dto.AhorristaResponse;
import dev.eder.ahorros.exception.RecursoDuplicadoException;
import dev.eder.ahorros.exception.RecursoNoEncontradoException;
import dev.eder.ahorros.exception.ReglaNegocioException;
import dev.eder.ahorros.mapper.AhorristaMapper;
import dev.eder.ahorros.model.Ahorrista;
import dev.eder.ahorros.repository.AhorristaRepo;
import dev.eder.ahorros.repository.DepositoRepo;
import dev.eder.ahorros.service.AhorristaService;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor
public class AhorristaServiceImpl implements AhorristaService{

    private final AhorristaRepo ahorristaRepo;
    private final DepositoRepo depositoRepo;


    @Override
    @Transactional(readOnly = true)
    public List<AhorristaResponse> listar(){
        return ahorristaRepo.findAll()
        .stream()
        .map(AhorristaMapper::toResponse)
        .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AhorristaResponse buscarPorId(Long id){
        return AhorristaMapper.toResponse(buscarAhorrista(id));
    }

    @Override 
    @Transactional
    public AhorristaResponse crear(AhorristaRequest request){
        String email = AhorristaMapper.normalizarEmail(request.email());

        if (ahorristaRepo.existsByDni(request.dni())) {
            throw new RecursoDuplicadoException("Ya existe un ahorrista con el DNI " + request.dni());
        }
        if (ahorristaRepo.existsByEmail(email)) {
            throw new RecursoDuplicadoException("Ya existe un ahorrista con el email " + email);
        }
        if (ahorristaRepo.existsByCelular(request.celular())) {
            throw new RecursoDuplicadoException("Ya existe un ahorrista con el celular " + request.celular());
        }

        Ahorrista ahorrista = AhorristaMapper.toEntity(request);
        return AhorristaMapper.toResponse(ahorristaRepo.save(ahorrista));
    }

     @Override 
    @Transactional
    public AhorristaResponse actualizar (Long id , AhorristaRequest request){
        Ahorrista ahorrista = buscarAhorrista(id);

        String email = AhorristaMapper.normalizarEmail(request.email());

        if (ahorristaRepo.existsByDniAndIdNot(request.dni(), id)) {
            throw new RecursoDuplicadoException("Ya existe un ahorrista con el DNI " + request.dni());
        }
        if (ahorristaRepo.existsByEmailAndIdNot(email, id)) {
            throw new RecursoDuplicadoException("Ya existe un ahorrista con el email " + email);
        }
        if (ahorristaRepo.existsByCelularAndIdNot(request.celular(), id)) {
            throw new RecursoDuplicadoException("Ya existe un ahorrista con el celular " + request.celular());
        }

        AhorristaMapper.actualizarEntidad(request, ahorrista);
        return AhorristaMapper.toResponse(ahorristaRepo.save(ahorrista));
    }

    @Override 
    @Transactional
    public void eliminar(Long id){
        Ahorrista ahorrista = buscarAhorrista(id);
        if (depositoRepo.existsByAhorristaId(id)) {
            throw new ReglaNegocioException("No se puede eliminar al ahorrista porque tiene depósitos registrados");
        }
        ahorristaRepo.delete(ahorrista);
    }

    private Ahorrista buscarAhorrista(Long id){
        return ahorristaRepo.findById(id)
        .orElseThrow(()-> new RecursoNoEncontradoException("No existe el ahorrista con el id " + id));
    }
}
