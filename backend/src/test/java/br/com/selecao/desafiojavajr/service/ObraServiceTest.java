package br.com.selecao.desafiojavajr.service;

import br.com.selecao.desafiojavajr.model.Obra;
import br.com.selecao.desafiojavajr.repository.AutorRepository;
import br.com.selecao.desafiojavajr.repository.ObraRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ObraServiceTest {
    @Mock
    private ObraRepository obraRepository;
    @Mock
    private AutorRepository autorRepository;
    @InjectMocks
    private ObraService obraService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExcluirObra() {
        Obra obra = new Obra();
        obra.setId(1L);
        when(obraRepository.findById(1L)).thenReturn(Optional.of(obra));
        doNothing().when(obraRepository).deleteById(1L);
        assertDoesNotThrow(() -> obraService.excluir(1L));
    }
}