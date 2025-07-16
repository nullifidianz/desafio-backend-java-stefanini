package br.com.selecao.desafiojavajr.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

@Data
public class ObraDTO {
    private Long id;
    private String nome;
    private String descricao;
    private LocalDate dataPublicacao;
    private LocalDate dataExposicao;
    private Set<Long> autoresIds;
}