package com.fullstack.publicaciones.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
import com.fullstack.publicaciones.model.ResponseDTO;
import com.fullstack.publicaciones.services.interfaces.IAutorService;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings({ "null", "rawtypes" })

class AutorControllerTest {

    @InjectMocks
    AutorController controller;

    @Mock
    IAutorService service;

    private Autor autor1, autor2, autor3;

    @BeforeEach
    public void setup() {
        autor1 = new Autor(1L, "autor1@test.com", "Autor 1", 0, 0);
        autor2 = new Autor(2L, "autor2@test.com", "Autor 2", 0, 0);
        autor3 = new Autor(3L, "autor3@test.com", "Autor 3", 0, 0);
    }

    @Test
    void testGetAutoresList() {
        // given
        var autores = List.of(autor1, autor2, autor3);

        // when
        when(service.getAllAutors()).thenReturn(autores);
        var result = controller.getAutoresList();

        // Full List
        assertEquals(CollectionModel.class, result.getBody().getClass());
        assertEquals(autores.size(), ((CollectionModel) result.getBody()).getContent().size());

        // Empty List
        when(service.getAllAutors()).thenReturn(new ArrayList<>());
        result = controller.getAutoresList();
        assertEquals(ResponseDTO.class, result.getBody().getClass());
        assertEquals("No hay autores ingresados", ((ResponseDTO) result.getBody()).getMessage());
    }

    @Test
    void testGetAutoresListIsEmpty() {
        // when
        when(service.getAllAutors()).thenReturn(new ArrayList<Autor>());
        var result = controller.getAutoresList();

        // then
        assertEquals(ResponseDTO.class, result.getBody().getClass());
        assertEquals("No hay autores ingresados", ((ResponseDTO) result.getBody()).getMessage());
    }

    @Test
    void testGetAutor() {
        // when
        when(service.getAutorById(1L)).thenReturn(Optional.of(autor1));
        when(service.getAutorById(999L)).thenReturn(Optional.empty());

        var result = controller.getAutor(1L);
        var badResult = controller.getAutor(999L);

        // Result OK
        assertEquals(EntityModel.class, result.getBody().getClass());
        assertEquals(Autor.class, ((EntityModel) result.getBody()).getContent().getClass());

        // Result Empty
        assertEquals(ResponseDTO.class, badResult.getBody().getClass());
        assertEquals("No existe el autor con el id 999", ((ResponseDTO) badResult.getBody()).getMessage());
    }

    @Test
    void testCreateAutor() throws Exception {
        // when
        when(service.createAutor(autor1)).thenReturn(autor1);

        var result = controller.createAutor(autor1);
        var badResult1 = controller.createAutor(new Autor());
        var badResult2 = controller.createAutor(autor2);

        // Result OK
        assertEquals(EntityModel.class, result.getBody().getClass());
        assertEquals(Autor.class, ((EntityModel) result.getBody()).getContent().getClass());

        // Result Error
        assertEquals(ResponseDTO.class, badResult1.getBody().getClass());
        assertEquals(HttpStatus.BAD_REQUEST, badResult1.getStatusCode());
        assertEquals("Debe indicar Nombre y Correo de Autor", ((ResponseDTO) badResult1.getBody()).getMessage());

        assertEquals(ResponseDTO.class, badResult2.getBody().getClass());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, badResult2.getStatusCode());
    }

    @Test
    void testPutMethodName() throws Exception {
        // when
        when(service.updateAutor(1L, autor1)).thenReturn(autor1);

        var result = controller.updateAutor(1L, autor1);
        var badResult1 = controller.updateAutor(2L, autor2);
        var badResult2 = controller.updateAutor(100L, new Autor());

        // Result OK
        assertEquals(EntityModel.class, result.getBody().getClass());
        assertEquals(Autor.class, ((EntityModel) result.getBody()).getContent().getClass());

        // Result Error
        assertEquals(ResponseDTO.class, badResult1.getBody().getClass());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, badResult1.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, badResult2.getStatusCode());
    }

    @Test
    void testDeleteAutor() {
        // when
        var result = controller.deleteAutor(1L);

        // Result OK
        assertEquals(ResponseDTO.class, result.getBody().getClass());
        assertEquals("Autor eliminado.", ((ResponseDTO) result.getBody()).getMessage());

    }

}
