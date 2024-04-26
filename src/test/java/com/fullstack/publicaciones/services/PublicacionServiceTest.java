package com.fullstack.publicaciones.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fullstack.publicaciones.model.Autor;
import com.fullstack.publicaciones.model.Publicacion;
import com.fullstack.publicaciones.repository.PublicacionRepository;

@ExtendWith(MockitoExtension.class)
class PublicacionServiceTest {

    @InjectMocks
    PublicacionService service;

    @Mock
    PublicacionRepository repository;
    @Mock
    EvaluacionService evaluacionService;

    private Autor autor;
    private Publicacion pub1, pub2, pub3;
    private List<Publicacion> pubs;

    @BeforeEach
    public void setup() {
        // given
        autor = new Autor(1L, "correo@test.com", "Nombre Test", 0, 0);
        pub1 = new Publicacion(1L, autor, LocalDateTime.now(), "Contenido 1", null, null, null);
        pub2 = new Publicacion(2L, autor, LocalDateTime.now(), "Contenido 2", pub1, null, null);
        pub3 = new Publicacion(3L, autor, LocalDateTime.now(), "Contenido 3", pub1, null, null);
        pubs = List.of(pub1, pub2, pub3);
    }

    @Test
    void testGetAllPublicacions() {
        // when
        when(repository.findAll()).thenReturn(pubs);
        var result = service.getAllPublicacions();

        // then
        assertEquals(pubs.size(), result.size());
        assertEquals(autor, result.getFirst().getAutor());
    }

    @Test
    void testGetPublicacionById() {
        // when
        when(repository.findById(1L)).thenReturn(Optional.of(pub1));

        // then
        assertTrue(service.getPublicacionById(1L).isPresent());
        assertEquals("Contenido 1", service.getPublicacionById(1L).get().getContenido());
        assertFalse(service.getPublicacionById(2L).isPresent());
    }

    @Test
    void testGetComentariosById() {
        // when
        when(repository.findByReferenciaId(1L)).thenReturn(List.of(pub2, pub3));

        // then
        assertEquals(2, service.getComentariosById(1L).size());
    }

    @Test
    void testCreatePublicacion() throws Exception {
        // when
        when(repository.save(pub1)).thenReturn(pub1);

        // then
        assertEquals(pub1, service.createPublicacion(pub1));
    }

    @Test
    void testUpdatePublicacion() throws Exception {
        // when
        when(repository.existsById(1L)).thenReturn(true);
        when(repository.save(pub1)).thenReturn(pub1);

        // then
        assertEquals(pub1, service.updatePublicacion(1L, pub1));
        assertThrows(Exception.class, () -> service.updatePublicacion(100L, pub1));
    }

    @Test
    void testDeletePublicacion() throws Exception {
        // when
        when(repository.existsById(1L)).thenReturn(true);
        service.deletePublicacion(1L);

        // then
        assertThrows(Exception.class, () -> service.deletePublicacion(100L));
        verify(repository, times(1)).deleteById(any());
    }

}
