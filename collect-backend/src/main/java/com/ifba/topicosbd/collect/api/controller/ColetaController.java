package com.ifba.topicosbd.collect.api.controller;

import com.ifba.topicosbd.collect.api.dto.*;
import com.ifba.topicosbd.collect.api.exceptionHandlers.ErrorMessage;
import com.ifba.topicosbd.collect.api.mapper.ColetaMapper;
import com.ifba.topicosbd.collect.api.mapper.EnderecoMapper;
import com.ifba.topicosbd.collect.api.mapper.PageableMapper;
import com.ifba.topicosbd.collect.core.entities.Coleta;
import com.ifba.topicosbd.collect.core.entities.Endereco;
import com.ifba.topicosbd.collect.core.repository.projection.ColetaProjection;
import com.ifba.topicosbd.collect.core.repository.projection.EnderecoProjection;
import com.ifba.topicosbd.collect.core.service.ColetaService;
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

@Tag(name = "Coletas", description = "Gerenciamento de coletas no sistema.")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("collect-plus/v1/coletas")
public class ColetaController {

    private final ColetaService coletaService;

    @Operation(
            summary = "Criar uma nova coleta",
            description = "Cria uma nova coleta no sistema.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Coleta criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PostMapping
    public ResponseEntity<ColetaResponseDto> create(@Valid @RequestBody ColetaCreateDto createDto) {
        Coleta coleta = coletaService.create(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ColetaMapper.toDto(coleta));
    }

    @Operation(
            summary = "Buscar coleta por ID",
            description = "Busca uma coleta pelo ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Coleta encontrada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ColetaResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Coleta não encontrada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping(value = "/find-by-id", params = "id")
    public ResponseEntity<ColetaResponseDto> findById(@RequestParam("id") Long id) {
        Coleta coleta = coletaService.findById(id);
        return ResponseEntity.ok(ColetaMapper.toDto(coleta));
    }

    @Operation(
            summary = "Buscar coletas por ponto de coleta",
            description = "Recupera uma lista paginada de coletas associadas a um ponto de coleta específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de coletas do ponto de coleta encontrada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageableDto.class)))
            }
    )
    @GetMapping(value = "/find-by-ponto-coleta", params = "pontoColetaId")
    public ResponseEntity<PageableDto> findByPontoColeta(@RequestParam("pontoColetaId") Long pontoColetaId, Pageable pageable) {
        Page<ColetaProjection> coletas = coletaService.findByPontoColeta(pontoColetaId, pageable);
        return ResponseEntity.ok(PageableMapper.toDto(coletas));
    }

    @Operation(
            summary = "Atualizar coleta",
            description = "Atualiza os dados de uma coleta pelo ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Coleta atualizada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Coleta não encontrada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PatchMapping(value = "/update", params = "id")
    public ResponseEntity<Void> update(@RequestParam("id") Long id, @Valid @RequestBody ColetaUpdateDto updateDto) {
        coletaService.update(id, updateDto.getFrete(), updateDto.getPontoColetaId(), updateDto.getEquipeColetaId());
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Deletar coleta",
            description = "Remove uma coleta pelo ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Coleta removida com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Coleta não encontrada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "500", description = "Erro de integridade de dados",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @DeleteMapping(value = "/delete", params = "id")
    public ResponseEntity<Void> delete(@RequestParam("id") Long id) {
        coletaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
