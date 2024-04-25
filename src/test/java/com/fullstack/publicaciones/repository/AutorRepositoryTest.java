package com.fullstack.publicaciones.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fullstack.publicaciones.model.Autor;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AutorRepositoryTest {

    @Autowired
    private AutorRepository repository;

    @Test
    void testFindByCorreo() {
        // given
        repository.saveAllAndFlush(List.of(new Autor(1L, "correo@test.com", "Nombre Test", 0, 0 )));

        // then
        assertTrue(repository.findByCorreo("correo@test.com").isPresent());
        assertFalse(repository.findByCorreo("otro@test.com").isPresent());
    }
}
