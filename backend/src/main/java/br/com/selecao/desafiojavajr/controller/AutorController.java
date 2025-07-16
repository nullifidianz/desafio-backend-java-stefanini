package br.com.selecao.desafiojavajr.controller;

import br.com.selecao.desafiojavajr.dto.AutorDTO;
import br.com.selecao.desafiojavajr.model.Autor;
import br.com.selecao.desafiojavajr.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/autores")
public class AutorController {
    @Autowired
    private AutorService autorService;

    @GetMapping
    public Page<AutorDTO> listar(Pageable pageable) {
        return autorService.listar(pageable).map(this::toDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> buscarPorId(@PathVariable Long id) {
        return autorService.buscarPorId(id)
                .map(this::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AutorDTO> criar(@RequestBody AutorDTO dto) {
        Autor autor = toEntity(dto);
        Autor salvo = autorService.salvar(autor, dto.getObrasIds());
        return ResponseEntity.created(URI.create("/api/autores/" + salvo.getId())).body(toDTO(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutorDTO> atualizar(@PathVariable Long id, @RequestBody AutorDTO dto) {
        Autor autor = toEntity(dto);
        Autor atualizado = autorService.atualizar(id, autor, dto.getObrasIds());
        return ResponseEntity.ok(toDTO(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        autorService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    private AutorDTO toDTO(Autor autor) {
        AutorDTO dto = new AutorDTO();
        dto.setId(autor.getId());
        dto.setNome(autor.getNome());
        dto.setSexo(autor.getSexo());
        dto.setEmail(autor.getEmail());
        dto.setDataNascimento(autor.getDataNascimento());
        dto.setPaisOrigem(autor.getPaisOrigem());
        dto.setCpf(autor.getCpf());
        if (autor.getObras() != null) {
            dto.setObrasIds(autor.getObras().stream().map(o -> o.getId()).collect(Collectors.toSet()));
        }
        return dto;
    }

    private Autor toEntity(AutorDTO dto) {
        Autor autor = new Autor();
        autor.setId(dto.getId());
        autor.setNome(dto.getNome());
        autor.setSexo(dto.getSexo());
        autor.setEmail(dto.getEmail());
        autor.setDataNascimento(dto.getDataNascimento());
        autor.setPaisOrigem(dto.getPaisOrigem());
        autor.setCpf(dto.getCpf());
        return autor;
    }
}