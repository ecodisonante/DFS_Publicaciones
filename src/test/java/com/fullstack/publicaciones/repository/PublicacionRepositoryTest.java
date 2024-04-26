package com.fullstack.publicaciones.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fullstack.publicaciones.model.Autor;
import com.fullstack.publicaciones.model.Publicacion;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PublicacionRepositoryTest {

    @Autowired
    private PublicacionRepository repository;

    @Test
    void testFindByReferenciaId() {
        // given
        var autor = new Autor(1L, "correo@test.com", "Nombre Test", 0, 0);
        var pub1 = new Publicacion(1L, autor, LocalDateTime.now(), "Contenido 1", null, null, null);
        var pub2 = new Publicacion(2L, autor, LocalDateTime.now(), "Contenido 2", pub1, null, null);
        var pub3 = new Publicacion(3L, autor, LocalDateTime.now(), "Contenido 3", pub1, null, null);
        repository.saveAllAndFlush(List.of(pub1, pub2, pub3));

        // when
        var result = repository.findByReferenciaId(1L);

        // then
        assertEquals(2, result.size());
    }
}
