package br.com.selecao.desafiojavajr.service;

import br.com.selecao.desafiojavajr.model.Autor;
import br.com.selecao.desafiojavajr.model.Obra;
import br.com.selecao.desafiojavajr.repository.AutorRepository;
import br.com.selecao.desafiojavajr.repository.ObraRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ObraService {
    @Autowired
    private ObraRepository obraRepository;
    @Autowired
    private AutorRepository autorRepository;

    public Page<Obra> listar(Pageable pageable) {
        return obraRepository.findAll(pageable);
    }

    public Optional<Obra> buscarPorId(Long id) {
        return obraRepository.findById(id);
    }

    @Transactional
    public Obra salvar(Obra obra, Set<Long> autoresIds) {
        if (!StringUtils.hasText(obra.getNome())) {
            throw new RuntimeException("Nome da obra é obrigatório");
        }
        if (!StringUtils.hasText(obra.getDescricao()) || obra.getDescricao().length() > 240) {
            throw new RuntimeException("Descrição obrigatória e deve ter até 240 caracteres");
        }
        if ((obra.getDataPublicacao() == null) && (obra.getDataExposicao() == null)) {
            throw new RuntimeException("Deve informar data de publicação ou data de exposição");
        }
        if (autoresIds == null || autoresIds.isEmpty()) {
            throw new RuntimeException("Obra deve ter pelo menos um autor");
        }
        Set<Autor> autores = new HashSet<>(autorRepository.findAllById(autoresIds));
        if (autores.isEmpty()) {
            throw new RuntimeException("Autores informados não encontrados");
        }
        obra.setAutores(autores);
        return obraRepository.save(obra);
    }

    @Transactional
    public Obra atualizar(Long id, Obra obraAtualizada, Set<Long> autoresIds) {
        Obra obra = obraRepository.findById(id).orElseThrow(() -> new RuntimeException("Obra não encontrada"));
        obra.setNome(obraAtualizada.getNome());
        obra.setDescricao(obraAtualizada.getDescricao());
        obra.setDataPublicacao(obraAtualizada.getDataPublicacao());
        obra.setDataExposicao(obraAtualizada.getDataExposicao());
        if (autoresIds != null && !autoresIds.isEmpty()) {
            Set<Autor> autores = new HashSet<>(autorRepository.findAllById(autoresIds));
            obra.setAutores(autores);
        }
        return obraRepository.save(obra);
    }

    @Transactional
    public void excluir(Long id) {
        obraRepository.deleteById(id);
    }

    public Page<Obra> filtrar(String nome, String descricao, Pageable pageable) {
        if (StringUtils.hasText(nome) && StringUtils.hasText(descricao)) {
            return obraRepository.findByNomeContainingIgnoreCaseAndDescricaoContainingIgnoreCase(nome, descricao,
                    pageable);
        } else if (StringUtils.hasText(nome)) {
            return obraRepository.findByNomeContainingIgnoreCase(nome, pageable);
        } else if (StringUtils.hasText(descricao)) {
            return obraRepository.findByDescricaoContainingIgnoreCase(descricao, pageable);
        } else {
            return obraRepository.findAll(pageable);
        }
    }
}