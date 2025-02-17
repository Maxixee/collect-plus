package com.ifba.topicosbd.collect.api.controller;

import com.ifba.topicosbd.collect.api.dto.EquipeColetaCreateDto;
import com.ifba.topicosbd.collect.api.dto.EquipeColetaResponseDto;
import com.ifba.topicosbd.collect.api.dto.PageableDto;
import com.ifba.topicosbd.collect.api.dto.TrabalhadorEquipeDto;
import com.ifba.topicosbd.collect.api.exceptionHandlers.ErrorMessage;
import com.ifba.topicosbd.collect.api.mapper.EquipeColetaMapper;
import com.ifba.topicosbd.collect.api.mapper.PageableMapper;
import com.ifba.topicosbd.collect.core.entities.EquipeColeta;
import com.ifba.topicosbd.collect.core.repository.projection.EquipeColetaProjection;
import com.ifba.topicosbd.collect.core.repository.projection.EquipeProjection;
import com.ifba.topicosbd.collect.core.service.EquipeColetaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Equipes de Coleta", description = "Gerenciamento de equipes de coleta no sistema de resíduos sólidos.")
@RequiredArgsConstructor
@RestController
@RequestMapping("collect-plus/v1/equipes-coleta")
public class EquipeColetaController {

    private final EquipeColetaService equipeColetaService;

    @Operation(
            summary = "Criar uma nova equipe de coleta",
            description = "Cria uma nova equipe de coleta no sistema.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Equipe criada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EquipeColetaResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Placa do carro já cadastrada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PostMapping
    public ResponseEntity<EquipeColetaResponseDto> create(@Valid @RequestBody EquipeColetaCreateDto createDto) {
        EquipeColeta equipe = equipeColetaService.create(EquipeColetaMapper.toEntity(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(EquipeColetaMapper.toDto(equipe));
    }

    @Operation(
            summary = "Buscar todas as Equipes de Coleta",
            description = "Recupera uma lista paginada de todas as equipes.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de equipes encontrada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageableDto.class))
                    )
            }
    )
    @GetMapping(value = "find-all")
    public ResponseEntity<PageableDto> findAll(Pageable pageable) {
        Page<EquipeProjection> equipes = equipeColetaService.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(equipes));
    }

    @Operation(
            summary = "Buscar equipe por ID",
            description = "Busca uma equipe de coleta pelo ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Equipe encontrada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EquipeColetaResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Equipe não encontrada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping(value = "find-by-id", params = "id")
    public ResponseEntity<EquipeColetaResponseDto> findById(@RequestParam("id") Long id) {
        EquipeColeta equipe = equipeColetaService.findById(id);
        return ResponseEntity.ok(EquipeColetaMapper.toDto(equipe));
    }

    @Operation(
            summary = "Buscar equipe por placa",
            description = "Busca uma equipe de coleta pela placa do carro fornecida.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Equipe encontrada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EquipeColetaResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Equipe não encontrada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping(value = "find-by-placa", params = "placa")
    public ResponseEntity<EquipeColetaResponseDto> findByPlaca(@RequestParam("placa") String placa) {
        EquipeColeta equipe = equipeColetaService.findByPlaca(placa);
        return ResponseEntity.ok(EquipeColetaMapper.toDto(equipe));
    }

    @Operation(
            summary = "Buscar equipes de coleta por ID do trabalhador",
            description = "Recupera uma lista paginada de equipes de coleta associadas a um trabalhador específico.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de equipes de coleta do trabalhador encontrada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageableDto.class))
                    )
            }
    )
    @GetMapping(value = "find-by-trabalhador", params = "id")
    public ResponseEntity<PageableDto> findByTrabalhador(@RequestParam("id") Long trabalhadorId, Pageable pageable) {
        Page<EquipeColetaProjection> equipes = equipeColetaService.findByTrabalhador(trabalhadorId, pageable);
        return ResponseEntity.ok(PageableMapper.toDto(equipes));
    }

    @Operation(
            summary = "Adicionar um trabalhador à equipe",
            description = "Associa um trabalhador a uma equipe de coleta.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Trabalhador adicionado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Equipe ou trabalhador não encontrados",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PostMapping(value = "adicionar-trabalhador")
    public ResponseEntity<Void> adicionarTrabalhador(@RequestBody @Valid TrabalhadorEquipeDto dto){
        equipeColetaService.adicionarTrabalhador(dto.getEquipeColetaId(), dto.getTrabalhadorId());
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Remover um trabalhador da equipe",
            description = "Desassocia um trabalhador de uma equipe de coleta.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Trabalhador removido com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Equipe ou trabalhador não encontrados",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "Erro ao tentar remover trabalhador",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @DeleteMapping(value = "/remover-trabalhador")
    public ResponseEntity<Void> removerTrabalhador(@RequestBody @Valid TrabalhadorEquipeDto dto){
        equipeColetaService.removerTrabalhador(dto.getEquipeColetaId(), dto.getTrabalhadorId());
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Atualizar placa de equipe",
            description = "Atualiza a placa do carro de uma equipe de coleta pelo ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Equipe atualizada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Equipe não encontrada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "409", description = "Placa já em uso por outra equipe",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PatchMapping(value = "update", params = "id")
    public ResponseEntity<Void> update(@RequestParam("id") Long id, @Valid @RequestBody EquipeColetaCreateDto updateDto) {
        equipeColetaService.update(id, updateDto.getPlacaDoCarro());
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Deletar equipe",
            description = "Remove uma equipe de coleta pelo ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Equipe removida com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Equipe não encontrada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "500", description = "Erro de integridade de dados",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @DeleteMapping(value = "delete", params = "id")
    public ResponseEntity<Void> delete(@RequestParam("id") Long id) {
        equipeColetaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
