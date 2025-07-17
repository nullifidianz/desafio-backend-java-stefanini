package br.com.selecao.desafiojavajr.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "obras")
public class Obra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome da obra é obrigatório")
    private String nome;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(max = 240, message = "Descrição deve ter no máximo 240 caracteres")
    private String descricao;

    private LocalDate dataPublicacao;
    private LocalDate dataExposicao;

    @ManyToMany(mappedBy = "obras")
    private Set<Autor> autores = new HashSet<>();
}
