package com.ifba.topicosbd.collect.core.service;

import com.ifba.topicosbd.collect.api.dto.ColetaCreateDto;
import com.ifba.topicosbd.collect.api.mapper.ColetaMapper;
import com.ifba.topicosbd.collect.core.entities.Coleta;
import com.ifba.topicosbd.collect.core.entities.Endereco;
import com.ifba.topicosbd.collect.core.entities.EquipeColeta;
import com.ifba.topicosbd.collect.core.entities.PontoDeColeta;
import com.ifba.topicosbd.collect.core.exceptions.DatabaseException;
import com.ifba.topicosbd.collect.core.exceptions.EntityAlreadyExistsException;
import com.ifba.topicosbd.collect.core.exceptions.EntityNotFoundException;
import com.ifba.topicosbd.collect.core.repository.ColetaRepository;
import com.ifba.topicosbd.collect.core.repository.EnderecoRepository;
import com.ifba.topicosbd.collect.core.repository.EquipeColetaRepository;
import com.ifba.topicosbd.collect.core.repository.PontoDeColetaRepository;
import com.ifba.topicosbd.collect.core.repository.projection.ColetaProjection;
import com.ifba.topicosbd.collect.core.repository.projection.EnderecoProjection;
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
public class ColetaService {

    private final ColetaRepository coletaRepository;
    private final PontoDeColetaRepository pontoDeColetaRepository;
    private final EquipeColetaRepository equipeColetaRepository;

    @Transactional
    public Coleta create(ColetaCreateDto createDto) {
        PontoDeColeta pontoColeta = pontoDeColetaRepository.findById(createDto.getPontoColeta())
                .orElseThrow(() -> new EntityNotFoundException("Ponto de coleta não encontrado. Cadastre o ponto de coleta antes de criar uma coleta."));

        EquipeColeta equipeColeta = equipeColetaRepository.findById(createDto.getEquipeColeta())
                .orElseThrow(() -> new EntityNotFoundException("Equipe de coleta não encontrada. Cadastre a equipe de coleta antes de criar uma coleta."));

        Coleta coleta = ColetaMapper.toEntity(createDto, pontoColeta, equipeColeta);

        coleta = coletaRepository.save(coleta);
        return coleta;
    }

    @Transactional(readOnly= true)
    public Coleta findById(Long id) {
        log.info("Procurando coleta com ID: {}", id);
        return coletaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Nenhuma coleta encontrada com o ID: {}", id);
                    return new EntityNotFoundException("Não existe nenhuma entrega com o ID: " + id);
                });
    }

    @Transactional(readOnly = true)
    public Page<ColetaProjection> findAll(Pageable pageable) {
        log.info("Buscando todas as coletas cadastradas.");

        Page<ColetaProjection> coletas = coletaRepository.findAllProjectedBy(pageable);

        log.info("Encontrados {} endereços.", coletas.getTotalElements());

        return coletas;
    }

    @Transactional(readOnly = true)
    public Page<ColetaProjection> findByPontoColeta(Long pontoColetaId, Pageable pageable) {
        return coletaRepository.findByPontoColetaId(pontoColetaId, pageable);
    }

    @Transactional
    public void update(Long id, Double frete, Long pontoColetaId, Long equipeColetaId) {
        Coleta coleta = coletaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Coleta não encontrada com o ID: " + id));

        PontoDeColeta pontoColeta = pontoDeColetaRepository.findById(pontoColetaId)
                .orElseThrow(() -> new EntityNotFoundException("Ponto de coleta não encontrado com o ID: " + pontoColetaId));

        EquipeColeta equipeColeta = equipeColetaRepository.findById(equipeColetaId)
                .orElseThrow(() -> new EntityNotFoundException("Equipe de coleta não encontrada com o ID: " + equipeColetaId));

        coleta.setFrete(frete);
        coleta.setPontoColeta(pontoColeta);
        coleta.setEquipeColeta(equipeColeta);

        coletaRepository.save(coleta);
    }

    @Transactional
    public void delete(Long id) {
        Coleta coleta = findById(id);
        try {
            coletaRepository.delete(coleta);
            log.info("Coleta com ID: {} excluído com sucesso.", id);
        } catch (DataIntegrityViolationException e) {
            log.error("Erro de integridade ao tentar excluir a coleta com ID {}: {}", id, e.getMessage());
            throw new DatabaseException("Violação de integridade.");
        }
    }

}
