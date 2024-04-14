package com.fullstack.publicaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fullstack.publicaciones.model.Publicacion;
import java.util.List;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {

    List<Publicacion> findByReferencia(long referencia);

}
