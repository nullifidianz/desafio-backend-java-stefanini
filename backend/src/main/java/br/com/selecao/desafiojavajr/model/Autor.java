package br.com.selecao.desafiojavajr.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import br.com.selecao.desafiojavajr.enums.Sexo;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pessoas", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "cpf")
})
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "deve colocar um nome")
    private String nome;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Email(message = "email invaido")
    private String email;
    @NotNull(message = "Data de nascimento obrigatória")
    private LocalDate dataNascimento;

    @NotBlank(message = "País de origem obrigatório")
    private String paisOrigem;
    private String cpf;

    @ManyToMany
    @JoinTable(name = "autor_obra", joinColumns = @JoinColumn(name = "autor_id"), inverseJoinColumns = @JoinColumn(name = "obra_id"))
    private Set<Obra> obras = new HashSet<>();

}
