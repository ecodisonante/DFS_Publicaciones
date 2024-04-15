package com.fullstack.publicaciones.services;

import java.util.List;
import java.util.Optional;
import com.fullstack.publicaciones.model.Autor;

public interface IAutorService {

    List<Autor> getAllAutors();

    Optional<Autor> getAutorById(Long id);

    Autor createAutor(Autor autor) throws Exception;

    Autor updateAutor(Long id, Autor autor) throws Exception;

    void deleteAutor(Long id) throws Exception;

    Autor updateCantPub(Long id);

    Autor updateCantEval(Long id);

}
