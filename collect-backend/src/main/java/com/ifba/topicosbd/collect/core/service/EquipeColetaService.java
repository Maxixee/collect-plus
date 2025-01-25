package com.ifba.topicosbd.collect.core.service;

import com.ifba.topicosbd.collect.core.entities.EquipeColeta;
import com.ifba.topicosbd.collect.core.exceptions.DatabaseException;
import com.ifba.topicosbd.collect.core.exceptions.EntityAlreadyExistsException;
import com.ifba.topicosbd.collect.core.exceptions.EntityNotFoundException;
import com.ifba.topicosbd.collect.core.exceptions.InvalidRegistrationInformationException;
import com.ifba.topicosbd.collect.core.repository.EquipeColetaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EquipeColetaService {

    private final EquipeColetaRepository repository;

    @Transactional
    public EquipeColeta create(EquipeColeta equipeColeta) {
        log.info("Tentando salvar a equipe de coleta com Placa do carro: {}", equipeColeta.getPlacaDoCarro());
        try {
            equipeColeta = repository.save(equipeColeta);
            log.info("Equipe de coleta salva com sucesso. ID: {}", equipeColeta.getId());
            return equipeColeta;
        } catch (DataIntegrityViolationException e) {
            log.error("Erro ao salvar equipe de coleta com plca do carro: {}. Exceção: {}", equipeColeta.getPlacaDoCarro(), e.getMessage());
            throw new EntityAlreadyExistsException("Esse carro já é usado por outra equipe.");
        }
    }

    @Transactional(readOnly = true)
    public EquipeColeta findById(Long id) {
        log.info("Procurando equipe de coleta com ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Nenhuma equipe de coleta encontrada com o ID: {}", id);
                    return new EntityNotFoundException("Não existe nenhuma equipe de coleta com o ID: " + id);
                });
    }

    @Transactional(readOnly = true)
    public EquipeColeta findByPlaca(String placa) {
        log.info("Procurando equipe de coleta com placa: {}", placa);
        return repository.findByPlacaDoCarro(placa)
                .orElseThrow(() -> {
                    log.warn("Nenhuma equipe de coleta encontrada com a placa: {}", placa);
                    return new EntityNotFoundException("Não existe nenhuma equipe de coleta com a placa: " + placa);
                });
    }

    @Transactional
    public void update(Long id, String novaPlaca) {
        log.info("Atualizando equipe de coleta com ID: {}", id);
        EquipeColeta equipeColeta = findById(id);

        log.info("Dados atuais da equipe: Placa do carro: {}", equipeColeta.getPlacaDoCarro());

        if (novaPlaca.equals(equipeColeta.getPlacaDoCarro())) {
            log.error("Essa equipe já usa o carro com placa: {}", novaPlaca);
            throw new InvalidRegistrationInformationException("Essa equipe já usa esse carro.");
        }

        if (repository.existsByPlacaDoCarro(novaPlaca)) {
            log.error("Erro: A placa do carro: {} já está associada a outra equipe.", novaPlaca);
            throw new EntityAlreadyExistsException("Esse carro já é usado por outra equipe.");
        }

        equipeColeta.setPlacaDoCarro(novaPlaca);

        log.info("Novos dados da equipe de coleta: placa: {}", equipeColeta.getPlacaDoCarro());

        try {
            repository.save(equipeColeta);
            log.info("Equipe de coleta com ID: {} atualizada com sucesso.", id);
        } catch (DataIntegrityViolationException e) {
            log.error("Erro ao salvar equipe de coleta com placa do carro: {}. Exceção: {}", equipeColeta.getPlacaDoCarro(), e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }


    @Transactional
    public void delete(Long id){
        EquipeColeta equipeColeta = findById(id);
        try {
            repository.delete(equipeColeta);
        }catch (DataIntegrityViolationException e){
            log.error("Erro de integridade ao tentar excluir a equipe de coleta com ID {}: {}", id, e.getMessage());
            throw new DatabaseException("Violação de integridade.");
        }
    }

}
