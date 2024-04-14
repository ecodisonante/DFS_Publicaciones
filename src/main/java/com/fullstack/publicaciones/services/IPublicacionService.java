package com.fullstack.publicaciones.services;

import java.util.List;
import java.util.Optional;
import com.fullstack.publicaciones.model.Publicacion;

public interface IPublicacionService {

    List<Publicacion> getAllPublicacions();

    List<Publicacion> getReferencias(Long id);

    Optional<Publicacion> getPublicacionById(Long id);

    Publicacion createPublicacion(Publicacion publicacion) throws Exception;

    Publicacion updatePublicacion(Long id, Publicacion publicacion) throws Exception;

    Publicacion updateValoracion(Long id);

    void deletePublicacion(Long id) throws Exception;

}
