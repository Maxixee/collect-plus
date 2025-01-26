package com.ifba.topicosbd.collect.api.controller;

import com.ifba.topicosbd.collect.api.dto.PageableDto;
import com.ifba.topicosbd.collect.api.dto.TrabalhadorCreateDto;
import com.ifba.topicosbd.collect.api.dto.TrabalhadorResponseDto;
import com.ifba.topicosbd.collect.api.dto.TrabalhadorUpdateDto;
import com.ifba.topicosbd.collect.api.exceptionHandlers.ErrorMessage;
import com.ifba.topicosbd.collect.api.mapper.PageableMapper;
import com.ifba.topicosbd.collect.api.mapper.TrabalhadorMapper;
import com.ifba.topicosbd.collect.core.entities.Trabalhador;
import com.ifba.topicosbd.collect.core.repository.projection.TrabalhadorProjection;
import com.ifba.topicosbd.collect.core.service.TrabalhadorService;
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

@Tag(name = "Trabalhadores", description = "Gerenciamento de trabalhadores no sistema de coleta de resíduos sólidos.")
@RequiredArgsConstructor
@RestController
@RequestMapping("collect-plus/v1/trabalhadores")
public class TrabalhadorController {

    private final TrabalhadorService trabalhadorService;

    @Operation(
            summary = "Criar um novo trabalhador",
            description = "Cria um novo trabalhador no sistema.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Trabalhador criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TrabalhadorResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "CPF já cadastrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PostMapping
    public ResponseEntity<TrabalhadorResponseDto> create(@Valid @RequestBody TrabalhadorCreateDto createDto) {
        Trabalhador trabalhador = trabalhadorService.create(TrabalhadorMapper.toEntity(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(TrabalhadorMapper.toDto(trabalhador));
    }

    @Operation(
            summary = "Buscar trabalhador por ID",
            description = "Busca um trabalhador pelo ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Trabalhador encontrado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TrabalhadorResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Trabalhador não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping(value = "find-by-id", params = "id")
    public ResponseEntity<TrabalhadorResponseDto> findById(@RequestParam("id") Long id) {
        Trabalhador trabalhador = trabalhadorService.findById(id);
        return ResponseEntity.ok(TrabalhadorMapper.toDto(trabalhador));
    }

    @Operation(
            summary = "Buscar trabalhador por CPF",
            description = "Busca um trabalhador pelo CPF fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Trabalhador encontrado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TrabalhadorResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Trabalhador não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping(value = "find-by-cpf", params = "cpf")
    public ResponseEntity<TrabalhadorResponseDto> findByCPF(@RequestParam("cpf") String cpf) {
        Trabalhador trabalhador = trabalhadorService.findByCPF(cpf);
        return ResponseEntity.ok(TrabalhadorMapper.toDto(trabalhador));
    }

    @Operation(
            summary = "Buscar trabalhadores por ID da equipe de coleta",
            description = "Recupera uma lista paginada de trabalhadores associados a uma equipe de coleta específica.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de trabalhadores da equipe de coleta encontrada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageableDto.class))
                    )
            }
    )
    @GetMapping(value = "find-by-equipe-coleta", params = "id")
    public ResponseEntity<PageableDto> findByEquipeColeta(@RequestParam("id") Long equipeId, Pageable pageable){
        Page<TrabalhadorProjection> trabalhadores = trabalhadorService.findByEquipeColeta(equipeId, pageable);
        return ResponseEntity.ok(PageableMapper.toDto(trabalhadores));
    }

    @Operation(
            summary = "Atualizar trabalhador",
            description = "Atualiza o salário e o nome de um trabalhador pelo ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Trabalhador atualizado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Trabalhador não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PatchMapping(value = "update", params = "id")
    public ResponseEntity<Void> update(@RequestParam("id") Long id, @Valid @RequestBody TrabalhadorUpdateDto updateDto) {
        trabalhadorService.update(id, updateDto.getSalario(), updateDto.getNome());
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Deletar trabalhador",
            description = "Remove um trabalhador pelo ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Trabalhador removido com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Trabalhador não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "500", description = "Erro de integridade de dados",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @DeleteMapping(value = "delete", params = "id")
    public ResponseEntity<Void> delete(@RequestParam("id") Long id) {
        trabalhadorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}