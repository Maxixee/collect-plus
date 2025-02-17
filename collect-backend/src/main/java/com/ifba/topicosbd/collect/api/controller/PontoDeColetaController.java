package com.ifba.topicosbd.collect.api.controller;

import com.ifba.topicosbd.collect.api.dto.PageableDto;
import com.ifba.topicosbd.collect.api.dto.PontoDeColetaCreateDto;
import com.ifba.topicosbd.collect.api.dto.PontoDeColetaResponseDto;
import com.ifba.topicosbd.collect.api.exceptionHandlers.ErrorMessage;
import com.ifba.topicosbd.collect.api.mapper.PageableMapper;
import com.ifba.topicosbd.collect.api.mapper.PontoDeColetaMapper;
import com.ifba.topicosbd.collect.core.entities.Endereco;
import com.ifba.topicosbd.collect.core.entities.PontoDeColeta;
import com.ifba.topicosbd.collect.core.repository.projection.EnderecoProjection;
import com.ifba.topicosbd.collect.core.repository.projection.PontoDeColetaProjection;
import com.ifba.topicosbd.collect.core.service.EnderecoService;
import com.ifba.topicosbd.collect.core.service.PontoDeColetaService;
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

@Tag(name = "Pontos de Coleta", description = "Gerenciamento de pontos de coleta no sistema.")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("collect-plus/v1/pontos-de-coleta")
public class PontoDeColetaController {

    private final PontoDeColetaService pontoDeColetaService;
    private final EnderecoService enderecoService;

    @Operation(
            summary = "Criar um novo ponto de coleta",
            description = "Cria um novo ponto de coleta no sistema.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Ponto de coleta criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PontoDeColetaResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Endereço não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PostMapping
    public ResponseEntity<PontoDeColetaResponseDto> create(@Valid @RequestBody PontoDeColetaCreateDto createDto) {
        Endereco endereco = enderecoService.findById(createDto.getEndereco());
        PontoDeColeta pontoDeColeta = new PontoDeColeta();
        pontoDeColeta.setTipoLixo(createDto.getTipoLixo());
        pontoDeColeta.setEndereco(endereco);

        PontoDeColeta body = pontoDeColetaService.create(pontoDeColeta);
        return ResponseEntity.status(HttpStatus.CREATED).body(PontoDeColetaMapper.toDto(body));
    }

    @Operation(
            summary = "Buscar ponto de coleta por ID",
            description = "Busca um ponto de coleta pelo ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ponto de coleta encontrado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PontoDeColetaResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Ponto de coleta não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping(value = "find-by-id", params = "id")
    public ResponseEntity<PontoDeColetaResponseDto> findById(@RequestParam("id") Long id) {
        PontoDeColeta pontoDeColeta = pontoDeColetaService.findById(id);
        return ResponseEntity.ok(PontoDeColetaMapper.toDto(pontoDeColeta));
    }

    @Operation(
            summary = "Buscar pontos de coleta por tipo de lixo",
            description = "Recupera uma lista paginada de pontos de coleta associados a um tipo de lixo específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de pontos de coleta encontrada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageableDto.class)))
            }
    )
    @GetMapping(value = "find-by-tipo-lixo", params = "tipoLixo")
    public ResponseEntity<PageableDto> findByTipoLixo(@RequestParam("tipoLixo") String tipoLixo, Pageable pageable) {
        Page<PontoDeColetaProjection> pontosDeColeta = pontoDeColetaService.findByTipoLixo(tipoLixo, pageable);
        return ResponseEntity.ok(PageableMapper.toDto(pontosDeColeta));
    }

    @Operation(
            summary = "Atualizar ponto de coleta",
            description = "Atualiza os dados de um ponto de coleta pelo ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Ponto de coleta atualizado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Ponto de coleta ou endereço não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PatchMapping(value = "update", params = "id")
    public ResponseEntity<Void> update(@RequestParam("id") Long id, @Valid @RequestBody PontoDeColetaCreateDto updateDto) {
        pontoDeColetaService.update(id, updateDto.getEndereco(), updateDto.getTipoLixo());
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Deletar ponto de coleta",
            description = "Remove um ponto de coleta pelo ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Ponto de coleta removido com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Ponto de coleta não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "500", description = "Erro de integridade de dados",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @DeleteMapping(value = "delete", params = "id")
    public ResponseEntity<Void> delete(@RequestParam("id") Long id) {
        pontoDeColetaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Listar todos os pontos de coleta",
            description = "Recupera uma lista paginada de todos os pontos de coleta cadastrados no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de pontos de coleta recuperada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageableDto.class)))
            }
    )
    @GetMapping("find-all")
    public ResponseEntity<PageableDto> findAll(Pageable pageable) {
        Page<PontoDeColetaProjection> pontosDeColeta = pontoDeColetaService.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(pontosDeColeta));
    }
}

