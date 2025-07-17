package br.com.selecao.desafiojavajr.controller;

import br.com.selecao.desafiojavajr.dto.ObraDTO;
import br.com.selecao.desafiojavajr.model.Obra;
import br.com.selecao.desafiojavajr.service.ObraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/obras")
public class ObraController {
    @Autowired
    private ObraService obraService;

    @GetMapping
    public Page<ObraDTO> listar(@RequestParam(required = false) String nome,
            @RequestParam(required = false) String descricao,
            Pageable pageable) {
        return obraService.filtrar(nome, descricao, pageable).map(this::toDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObraDTO> buscarPorId(@PathVariable Long id) {
        return obraService.buscarPorId(id)
                .map(this::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ObraDTO> criar(@RequestBody ObraDTO dto) {
        Obra obra = toEntity(dto);
        Obra salvo = obraService.salvar(obra, dto.getAutoresIds());
        return ResponseEntity.created(URI.create("/api/obras/" + salvo.getId())).body(toDTO(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObraDTO> atualizar(@PathVariable Long id, @RequestBody ObraDTO dto) {
        Obra obra = toEntity(dto);
        Obra atualizado = obraService.atualizar(id, obra, dto.getAutoresIds());
        return ResponseEntity.ok(toDTO(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        obraService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    private ObraDTO toDTO(Obra obra) {
        ObraDTO dto = new ObraDTO();
        dto.setId(obra.getId());
        dto.setNome(obra.getNome());
        dto.setDescricao(obra.getDescricao());
        dto.setDataPublicacao(obra.getDataPublicacao());
        dto.setDataExposicao(obra.getDataExposicao());
        if (obra.getAutores() != null) {
            dto.setAutoresIds(obra.getAutores().stream().map(a -> a.getId()).collect(Collectors.toSet()));
        }
        return dto;
    }

    private Obra toEntity(ObraDTO dto) {
        Obra obra = new Obra();
        obra.setId(dto.getId());
        obra.setNome(dto.getNome());
        obra.setDescricao(dto.getDescricao());
        obra.setDataPublicacao(dto.getDataPublicacao());
        obra.setDataExposicao(dto.getDataExposicao());
        return obra;
    }
}