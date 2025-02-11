package com.ifba.topicosbd.collect.api.controller;

import com.ifba.topicosbd.collect.api.dto.EnderecoCreateDto;
import com.ifba.topicosbd.collect.api.dto.EnderecoResponseDto;
import com.ifba.topicosbd.collect.api.dto.PageableDto;
import com.ifba.topicosbd.collect.api.exceptionHandlers.ErrorMessage;
import com.ifba.topicosbd.collect.api.mapper.EnderecoMapper;
import com.ifba.topicosbd.collect.api.mapper.PageableMapper;
import com.ifba.topicosbd.collect.core.entities.Endereco;
import com.ifba.topicosbd.collect.core.repository.projection.EnderecoProjection;
import com.ifba.topicosbd.collect.core.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Endereços", description = "Gerenciamento de endereços no sistema.")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("collect-plus/v1/enderecos")
public class EnderecoController {

    private final EnderecoService enderecoService;

    @Operation(
            summary = "Criar um novo endereço",
            description = "Cria um novo endereço no sistema.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Endereço já cadastrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PostMapping
    public ResponseEntity<EnderecoResponseDto> create(@Valid @RequestBody EnderecoCreateDto createDto) {
        Endereco endereco = enderecoService.create(EnderecoMapper.toEntity(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(EnderecoMapper.toDto(endereco));
    }

    @Operation(
            summary = "Buscar endereço por ID",
            description = "Busca um endereço pelo ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Endereço não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping(value = "find-by-id", params = "id")
    public ResponseEntity<EnderecoResponseDto> findById(@RequestParam("id") Long id) {
        Endereco endereco = enderecoService.findById(id);
        return ResponseEntity.ok(EnderecoMapper.toDto(endereco));
    }

    @Operation(
            summary = "Buscar endereço por CEP",
            description = "Busca um endereço pelo CEP fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Endereço não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping(value = "find-by-cep", params = "cep")
    public ResponseEntity<EnderecoResponseDto> findByCep(@RequestParam("cep") String cep) {
        Endereco endereco = enderecoService.findByCep(cep);
        return ResponseEntity.ok(EnderecoMapper.toDto(endereco));
    }

    @Operation(
            summary = "Buscar endereços por cidade",
            description = "Recupera uma lista paginada de endereços associados a uma cidade específica.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de endereços da cidade encontrada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageableDto.class))
                    )
            }
    )
    @GetMapping(value = "find-by-cidade", params = "cidade")
    public ResponseEntity<PageableDto> findByCidade(@RequestParam("cidade") String cidade, Pageable pageable) {
        Page<EnderecoProjection> enderecos = enderecoService.findByCidade(cidade, pageable);
        return ResponseEntity.ok(PageableMapper.toDto(enderecos));
    }

    @Operation(
            summary = "Atualizar endereço",
            description = "Atualiza os dados de um endereço pelo ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Endereço atualizado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Endereço não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PatchMapping(value = "update", params = "id")
    public ResponseEntity<Void> update(@RequestParam("id") Long id, @Valid @RequestBody EnderecoCreateDto updateDto) {
        enderecoService.update(id, updateDto.getCep(), updateDto.getCidade(), updateDto.getRua(), updateDto.getNumero(), updateDto.getComplemento());
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Deletar endereço",
            description = "Remove um endereço pelo ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Endereço removido com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Endereço não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "500", description = "Erro de integridade de dados",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @DeleteMapping(value = "delete", params = "id")
    public ResponseEntity<Void> delete(@RequestParam("id") Long id) {
        enderecoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
