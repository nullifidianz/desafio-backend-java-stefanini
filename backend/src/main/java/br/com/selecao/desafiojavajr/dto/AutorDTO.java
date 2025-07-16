package br.com.selecao.desafiojavajr.dto;

import br.com.selecao.desafiojavajr.enums.Sexo;
import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

@Data
public class AutorDTO {
    private Long id;
    private String nome;
    private Sexo sexo;
    private String email;
    private LocalDate dataNascimento;
    private String paisOrigem;
    private String cpf;
    private Set<Long> obrasIds;
}