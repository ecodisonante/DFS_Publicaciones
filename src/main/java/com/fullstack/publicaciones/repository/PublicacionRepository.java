package com.fullstack.publicaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fullstack.publicaciones.model.Publicacion;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {

}
