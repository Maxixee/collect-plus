package com.ifba.topicosbd.collect.core.service;

import com.ifba.topicosbd.collect.core.entities.Trabalhador;
import com.ifba.topicosbd.collect.core.exceptions.DatabaseException;
import com.ifba.topicosbd.collect.core.exceptions.EntityAlreadyExistsException;
import com.ifba.topicosbd.collect.core.exceptions.EntityNotFoundException;
import com.ifba.topicosbd.collect.core.repository.TrabalhadorRepository;
import com.ifba.topicosbd.collect.core.repository.projection.TrabalhadorProjection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrabalhadorService {

    private final TrabalhadorRepository repository;

    @Transactional
    public Trabalhador create(Trabalhador trabalhador) {
        log.info("Tentando salvar o trabalhador com CPF: {}", trabalhador.getCPF());
        try {
            trabalhador = repository.save(trabalhador);
            log.info("Trabalhador salvo com sucesso. ID: {}", trabalhador.getId());
            return trabalhador;
        } catch (DataIntegrityViolationException e) {
            log.error("Erro ao salvar trabalhador com CPF: {}. Exceção: {}", trabalhador.getCPF(), e.getMessage());
            throw new EntityAlreadyExistsException("Já tem um trbalhador com esse CPF;");
        }
    }

    @Transactional(readOnly = true)
    public Trabalhador findById(Long id) {
        log.info("Procurando trabalhador com ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Nenhum trabalhador encontrado com o ID: {}", id);
                    return new EntityNotFoundException("Não existe nenhum trabalhador com o ID: " + id);
                });
    }

    @Transactional(readOnly = true)
    public Trabalhador findByCPF(String cpf) {
        log.info("Procurando trabalhador com CPF: {}", cpf);
        return repository.findByCPF(cpf)
                .orElseThrow(() -> {
                    log.warn("Nenhum trabalhador encontrado com o CPF: {}", cpf);
                    return new EntityNotFoundException("Não existe nenhum trabalhador com o CPF: " + cpf);
                });
    }

    @Transactional(readOnly = true)
    public Page<TrabalhadorProjection> findAll(Pageable pageable){
        log.info("Buscando todos os trabalhadores.");
        Page<TrabalhadorProjection> trabalhadores = repository.findAllPageable(pageable);

        return trabalhadores;
    }

    @Transactional(readOnly = true)
    public Page<TrabalhadorProjection> findByEquipeColeta(Long equipeId, Pageable pageable) {
        log.info("Buscando trabalhadores para a equipe de coleta com ID: {}", equipeId);

        Page<TrabalhadorProjection> trabalhadores = repository.findByEquipesId(equipeId, pageable);

        log.info("Encontrados {} trabalhadores para a equipe de coleta com ID: {}", trabalhadores.getTotalElements(), equipeId);

        return trabalhadores;
    }

    @Transactional
    public void update(Long id, String salario, String nome) {
        log.info("Atualizando trabalhador com ID: {}", id);
        Trabalhador trabalhador = findById(id);

        log.info("Dados atuais do trabalhador: Nome: {}, Salário: {}", trabalhador.getNome(), trabalhador.getSalario());
        trabalhador.setSalario(salario);
        trabalhador.setNome(nome);

        log.info("Novos dados do trabalhador: Nome: {}, Salário: {}", nome, salario);
        repository.save(trabalhador);
        log.info("Trabalhador com ID: {} atualizado com sucesso.", id);
    }


    @Transactional
    public void delete(Long id){
        Trabalhador trabalhador = findById(id);
        try {
            repository.delete(trabalhador);
        }catch (DataIntegrityViolationException e){
            log.error("Erro de integridade ao tentar excluir o trabalhador com ID {}: {}", id, e.getMessage());
            throw new DatabaseException("Violação de integridade.");
        }
    }

}
