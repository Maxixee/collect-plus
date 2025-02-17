package com.ifba.topicosbd.collect.core.service;

import com.ifba.topicosbd.collect.core.entities.Endereco;
import com.ifba.topicosbd.collect.core.exceptions.DatabaseException;
import com.ifba.topicosbd.collect.core.exceptions.EntityAlreadyExistsException;
import com.ifba.topicosbd.collect.core.exceptions.EntityNotFoundException;
import com.ifba.topicosbd.collect.core.repository.EnderecoRepository;
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
public class EnderecoService {

    private final EnderecoRepository repository;

    @Transactional
    public Endereco create(Endereco endereco) {
        log.info("Tentando salvar o endereço com CEP: {}", endereco.getCep());
        try {
            endereco = repository.save(endereco);
            log.info("Endereço salvo com sucesso. ID: {}", endereco.getId());
            return endereco;
        } catch (DataIntegrityViolationException e) {
            log.error("Erro ao salvar endereço com CEP: {}. Exceção: {}", endereco.getCep(), e.getMessage());
            throw new EntityAlreadyExistsException("Já existe um endereço com esse CEP.");
        }
    }

    @Transactional(readOnly= true)
    public Endereco findById(Long id) {
        log.info("Procurando endereço com ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Nenhum endereço encontrado com o ID: {}", id);
                    return new EntityNotFoundException("Não existe nenhum endereço com o ID: " + id);
                });
    }

    @Transactional(readOnly = true)
    public Endereco findByCep(String cep) {
        log.info("Procurando endereço com CEP: {}", cep);
        return repository.findByCep(cep)
                .orElseThrow(() -> {
                    log.warn("Nenhum endereço encontrado com o CEP: {}", cep);
                    return new EntityNotFoundException("Não existe nenhum endereço com o CEP: " + cep);
                });
    }

    @Transactional(readOnly = true)
    public Page<EnderecoProjection> findByCidade(String cidade, Pageable pageable) {
        log.info("Buscando endereços na cidade: {}", cidade);

        Page<EnderecoProjection> enderecos = repository.findByCidade(cidade, pageable);

        log.info("Encontrados {} endereços na cidade: {}", enderecos.getTotalElements(), cidade);

        return enderecos;
    }

    @Transactional
    public void update(Long id, String cep, String cidade, String rua, Integer numero, String complemento) {
        log.info("Atualizando endereço com ID: {}", id);
        Endereco endereco = findById(id);

        log.info("Dados atuais do endereço: CEP: {}, Cidade: {}, Rua: {}, Número: {}, Complemento: {}",
                endereco.getCep(), endereco.getCidade(), endereco.getRua(), endereco.getNumero(), endereco.getComplemento());

        endereco.setCep(cep);
        endereco.setCidade(cidade);
        endereco.setRua(rua);
        endereco.setNumero(numero);
        endereco.setComplemento(complemento);

        log.info("Novos dados do endereço: CEP: {}, Cidade: {}, Rua: {}, Número: {}, Complemento: {}",
                cep, cidade, rua, numero, complemento);

        repository.save(endereco);
        log.info("Endereço com ID: {} atualizado com sucesso.", id);
    }

    @Transactional
    public void delete(Long id) {
        Endereco endereco = findById(id);
        try {
            repository.delete(endereco);
            log.info("Endereço com ID: {} excluído com sucesso.", id);
        } catch (DataIntegrityViolationException e) {
            log.error("Erro de integridade ao tentar excluir o endereço com ID {}: {}", id, e.getMessage());
            throw new DatabaseException("Violação de integridade.");
        }
    }

    @Transactional(readOnly = true)
    public Page<EnderecoProjection> findAll(Pageable pageable) {
        log.info("Buscando todos os endereços cadastrados.");

        Page<EnderecoProjection> enderecos = repository.findAllProjectedBy(pageable);

        log.info("Encontrados {} endereços.", enderecos.getTotalElements());

        return enderecos;
    }

}
