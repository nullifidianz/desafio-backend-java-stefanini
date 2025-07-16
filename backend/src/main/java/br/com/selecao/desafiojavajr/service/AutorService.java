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
public class AutorService {
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private ObraRepository obraRepository;

    public Page<Autor> listar(Pageable pageable) {
        return autorRepository.findAll(pageable);
    }

    public Optional<Autor> buscarPorId(Long id) {
        return autorRepository.findById(id);
    }

    @Transactional
    public Autor salvar(Autor autor, Set<Long> obrasIds) {
        if (autor.getEmail() != null && autorRepository.existsByEmail(autor.getEmail())) {
            throw new RuntimeException("E-mail já cadastrado");
        }
        if (StringUtils.hasText(autor.getPaisOrigem()) && autor.getPaisOrigem().equalsIgnoreCase("Brasil")) {
            if (!StringUtils.hasText(autor.getCpf())) {
                throw new RuntimeException("CPF obrigatório para autores do Brasil");
            }
            if (autorRepository.existsByCpf(autor.getCpf())) {
                throw new RuntimeException("CPF já cadastrado");
            }
            // TODO: Adicionar validação de formato de CPF
        } else {
            autor.setCpf(null);
        }
        if (!StringUtils.hasText(autor.getPaisOrigem())) {
            throw new RuntimeException("País de origem obrigatório");
        }
        if (autor.getDataNascimento() == null) {
            throw new RuntimeException("Data de nascimento obrigatória");
        }
        if (obrasIds != null && !obrasIds.isEmpty()) {
            Set<Obra> obras = new HashSet<>(obraRepository.findAllById(obrasIds));
            autor.setObras(obras);
        }
        return autorRepository.save(autor);
    }

    @Transactional
    public Autor atualizar(Long id, Autor autorAtualizado, Set<Long> obrasIds) {
        Autor autor = autorRepository.findById(id).orElseThrow(() -> new RuntimeException("Autor não encontrado"));
        autor.setNome(autorAtualizado.getNome());
        autor.setSexo(autorAtualizado.getSexo());
        autor.setEmail(autorAtualizado.getEmail());
        autor.setDataNascimento(autorAtualizado.getDataNascimento());
        autor.setPaisOrigem(autorAtualizado.getPaisOrigem());
        autor.setCpf(autorAtualizado.getCpf());
        if (obrasIds != null) {
            Set<Obra> obras = new HashSet<>(obraRepository.findAllById(obrasIds));
            autor.setObras(obras);
        }
        return autorRepository.save(autor);
    }

    @Transactional
    public void excluir(Long id) {
        Autor autor = autorRepository.findById(id).orElseThrow(() -> new RuntimeException("Autor não encontrado"));
        if (autor.getObras() != null && !autor.getObras().isEmpty()) {
            throw new RuntimeException("Autor não pode ser excluído pois possui obras associadas");
        }
        autorRepository.deleteById(id);
    }
}