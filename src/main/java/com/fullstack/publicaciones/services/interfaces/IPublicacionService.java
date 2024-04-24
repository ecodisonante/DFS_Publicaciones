package com.fullstack.publicaciones.services.interfaces;

import java.util.List;
import java.util.Optional;
import com.fullstack.publicaciones.model.Publicacion;

public interface IPublicacionService {

    List<Publicacion> getAllPublicacions();

    Optional<Publicacion> getPublicacionById(Long id);

    List<Publicacion> getComentariosById(Long id);

    Publicacion createPublicacion(Publicacion publicacion) throws Exception;

    Publicacion updatePublicacion(Long id, Publicacion publicacion) throws Exception;

    void deletePublicacion(Long id) throws Exception;

}
