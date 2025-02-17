package com.ifba.topicosbd.collect.core.service;

import com.ifba.topicosbd.collect.api.dto.PontoDeColetaCreateDto;
import com.ifba.topicosbd.collect.core.entities.Endereco;
import com.ifba.topicosbd.collect.core.entities.PontoDeColeta;
import com.ifba.topicosbd.collect.core.exceptions.DatabaseException;
import com.ifba.topicosbd.collect.core.exceptions.EntityAlreadyExistsException;
import com.ifba.topicosbd.collect.core.exceptions.EntityNotFoundException;
import com.ifba.topicosbd.collect.core.repository.EnderecoRepository;
import com.ifba.topicosbd.collect.core.repository.PontoDeColetaRepository;
import com.ifba.topicosbd.collect.core.repository.projection.EnderecoProjection;
import com.ifba.topicosbd.collect.core.repository.projection.PontoDeColetaProjection;
import jakarta.validation.Valid;
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
public class PontoDeColetaService {

    private final PontoDeColetaRepository repository;
    private final EnderecoRepository enderecoRepository;

    @Transactional
    public PontoDeColeta create(@Valid PontoDeColeta pontoDeColeta) {
        log.info("Tentando salvar o ponto de coleta no endereço ID: {}",
                pontoDeColeta.getEndereco() != null ? pontoDeColeta.getEndereco().getId() : "N/A");

        if (pontoDeColeta.getEndereco() == null || pontoDeColeta.getEndereco().getId() == null) {
            throw new IllegalArgumentException("O endereço do ponto de coleta deve ser informado e já deve existir.");
        }

        // Busca o endereço no banco de dados
        Endereco endereco = enderecoRepository.findById(pontoDeColeta.getEndereco().getId())
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado. Cadastre o endereço antes de criar um ponto de coleta."));

        pontoDeColeta.setEndereco(endereco);

        try {
            pontoDeColeta = repository.save(pontoDeColeta);
            log.info("Ponto de coleta salvo com sucesso. ID: {}", pontoDeColeta.getId());
            return pontoDeColeta;
        } catch (DataIntegrityViolationException e) {
            log.error("Erro ao salvar ponto de coleta. Exceção: {}", e.getMessage());
            throw new EntityAlreadyExistsException("Já existe um ponto de coleta nesse endereço.");
        }
    }

    @Transactional(readOnly = true)
    public PontoDeColeta findById(Long id) {
        log.info("Procurando ponto de coleta com ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Nenhum ponto de coleta encontrado com o ID: {}", id);
                    return new EntityNotFoundException("Não existe nenhum ponto de coleta com o ID: " + id);
                });
    }

    @Transactional(readOnly = true)
    public Page<PontoDeColetaProjection> findByTipoLixo(String tipoLixo, Pageable pageable) {
        log.info("Buscando pontos de coleta para o tipo de lixo: {}", tipoLixo);

        Page<PontoDeColetaProjection> pontosDeColeta = repository.findByTipoLixo(tipoLixo, pageable);

        log.info("Encontrados {} pontos de coleta para o tipo de lixo: {}", pontosDeColeta.getTotalElements(), tipoLixo);

        return pontosDeColeta;
    }

    @Transactional
    public void update(Long id, Long enderecoId, String tipoLixo) {
        log.info("Atualizando ponto de coleta com ID: {}", id);
        PontoDeColeta pontoDeColeta = findById(id);

        log.info("Dados atuais do ponto de coleta: Endereço ID: {}, Tipo de Lixo: {}",
                pontoDeColeta.getEndereco().getId(), pontoDeColeta.getTipoLixo());

        if (enderecoId != null) {
            Endereco endereco = enderecoRepository.findById(enderecoId)
                    .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado. Apenas endereços existentes podem ser vinculados."));
            pontoDeColeta.setEndereco(endereco);
        }

        if (tipoLixo != null && !tipoLixo.isBlank()) {
            pontoDeColeta.setTipoLixo(tipoLixo);
        }

        repository.save(pontoDeColeta);
        log.info("Ponto de coleta com ID: {} atualizado com sucesso.", id);
    }

    @Transactional
    public void delete(Long id) {
        PontoDeColeta pontoDeColeta = findById(id);
        try {
            repository.delete(pontoDeColeta);
            log.info("Ponto de coleta com ID: {} excluído com sucesso.", id);
        } catch (DataIntegrityViolationException e) {
            log.error("Erro de integridade ao tentar excluir o ponto de coleta com ID {}: {}", id, e.getMessage());
            throw new DatabaseException("Violação de integridade.");
        }
    }

    @Transactional(readOnly = true)
    public Page<PontoDeColetaProjection> findAll(Pageable pageable) {
        log.info("Buscando todos os endereços cadastrados.");

        Page<PontoDeColetaProjection> pontosDeColeta = repository.findAllProjectedBy(pageable);

        log.info("Encontrados {} endereços.", pontosDeColeta.getTotalElements());

        return pontosDeColeta;
    }
}
