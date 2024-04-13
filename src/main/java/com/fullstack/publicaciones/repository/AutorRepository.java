package com.fullstack.publicaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fullstack.publicaciones.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long> {

}
