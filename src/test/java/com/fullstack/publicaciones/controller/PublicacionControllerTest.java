package com.fullstack.publicaciones.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import com.fullstack.publicaciones.model.Autor;
import com.fullstack.publicaciones.model.Publicacion;
import com.fullstack.publicaciones.model.ResponseDTO;
import com.fullstack.publicaciones.services.interfaces.IAutorService;
import com.fullstack.publicaciones.services.interfaces.IPublicacionService;

@ExtendWith(MockitoExtension.class)
class PublicacionControllerTest {

    @InjectMocks
    PublicacionController controller;

    @Mock
    IPublicacionService publicacionService;
    @Mock
    IAutorService autorService;

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
    void testGetPublicacionesList() {
        // when
        when(publicacionService.getAllPublicacions()).thenReturn(pubs);
        var result = controller.getPublicacionesList();

        // Full List
        assertEquals(CollectionModel.class, result.getBody().getClass());
        assertEquals(pubs.size(), ((CollectionModel) result.getBody()).getContent().size());

        // Empty List
        when(publicacionService.getAllPublicacions()).thenReturn(new ArrayList<>());
        result = controller.getPublicacionesList();
        assertEquals(ResponseDTO.class, result.getBody().getClass());
        assertEquals("No hay publicaciones ingresadas", ((ResponseDTO) result.getBody()).getMessage());
    }

    @Test
    void testGetPublicacion() {
        // when
        when(publicacionService.getPublicacionById(1L)).thenReturn(Optional.of(pub1));
        when(publicacionService.getPublicacionById(2L)).thenReturn(Optional.empty());

        var result = controller.getPublicacion(1L);
        var badResult = controller.getPublicacion(2L);

        // Result OK
        assertEquals(EntityModel.class, result.getBody().getClass());
        assertEquals(Publicacion.class, ((EntityModel) result.getBody()).getContent().getClass());

        // Result Empty
        assertEquals(ResponseDTO.class, badResult.getBody().getClass());
        assertEquals("No existe la publicacion con el id 2", ((ResponseDTO) badResult.getBody()).getMessage());
    }

    @Test
    void testCreatePublicacion() throws Exception {
        // when
        when(autorService.getAutorById(1L)).thenReturn(Optional.of(autor));
        when(publicacionService.createPublicacion(pub1)).thenReturn(pub1);
        when(publicacionService.getPublicacionById(1L)).thenReturn(Optional.of(pub1));

        var result = controller.createPublicacion(pub1);
        var badResult1 = controller.createPublicacion(pub2);
        var badResult2 = controller.createPublicacion(new Publicacion());

        // Result OK
        assertEquals(EntityModel.class, result.getBody().getClass());
        assertEquals(Publicacion.class, ((EntityModel) result.getBody()).getContent().getClass());

        // Result Error
        assertEquals(ResponseDTO.class, badResult1.getBody().getClass());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, badResult1.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, badResult2.getStatusCode());
    }

    @Test
    void testUpdatePublicacion() throws Exception {
        // when
        when(autorService.getAutorById(1L)).thenReturn(Optional.of(autor));
        when(publicacionService.updatePublicacion(1L, pub1)).thenReturn(pub1);
        when(publicacionService.getPublicacionById(1L)).thenReturn(Optional.of(pub1));

        var result = controller.updatePublicacion(1L, pub1);
        var badResult1 = controller.updatePublicacion(2L, pub2);
        var badResult2 = controller.updatePublicacion(100L, new Publicacion());

        // Result OK
        assertEquals(EntityModel.class, result.getBody().getClass());
        assertEquals(Publicacion.class, ((EntityModel) result.getBody()).getContent().getClass());

        // Result Error
        assertEquals(ResponseDTO.class, badResult1.getBody().getClass());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, badResult1.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, badResult2.getStatusCode());
    }

    @Test
    void testDeletePublicacion() {
        // when
        var result = controller.deletePublicacion(1L);

        // Result OK
        assertEquals(ResponseDTO.class, result.getBody().getClass());
        assertEquals("Publicacion eliminada.", ((ResponseDTO) result.getBody()).getMessage());
    }

}
