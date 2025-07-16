package br.com.selecao.desafiojavajr.service;

import br.com.selecao.desafiojavajr.model.Autor;
import br.com.selecao.desafiojavajr.repository.AutorRepository;
import br.com.selecao.desafiojavajr.repository.ObraRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutorServiceTest {
    @Mock
    private AutorRepository autorRepository;
    @Mock
    private ObraRepository obraRepository;
    @InjectMocks
    private AutorService autorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvarAutorComEmailUnico() {
        Autor autor = new Autor();
        autor.setNome("João");
        autor.setEmail("joao@email.com");
        autor.setDataNascimento(LocalDate.of(1990, 1, 1));
        autor.setPaisOrigem("Brasil");
        autor.setCpf("12345678901");
        when(autorRepository.existsByEmail("joao@email.com")).thenReturn(false);
        when(autorRepository.existsByCpf("12345678901")).thenReturn(false);
        when(autorRepository.save(any(Autor.class))).thenReturn(autor);
        Autor salvo = autorService.salvar(autor, null);
        assertEquals("João", salvo.getNome());
    }

    @Test
    void testExcluirAutorSemObras() {
        Autor autor = new Autor();
        autor.setId(1L);
        autor.setObras(null);
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        doNothing().when(autorRepository).deleteById(1L);
        assertDoesNotThrow(() -> autorService.excluir(1L));
    }
}