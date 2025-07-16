package br.com.selecao.desafiojavajr.repository;

import br.com.selecao.desafiojavajr.model.Obra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObraRepository extends JpaRepository<Obra, Long> {
    Page<Obra> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<Obra> findByDescricaoContainingIgnoreCase(String descricao, Pageable pageable);

    Page<Obra> findByNomeContainingIgnoreCaseAndDescricaoContainingIgnoreCase(String nome, String descricao,
            Pageable pageable);
}