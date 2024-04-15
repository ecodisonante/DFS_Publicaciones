package com.fullstack.publicaciones.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fullstack.publicaciones.model.Autor;
import com.fullstack.publicaciones.repository.AutorRepository;

@Service
public class AutorService implements IAutorService {

    @Autowired
    private AutorRepository autorRepository;

    @Override
    public List<Autor> getAllAutors() {
        return autorRepository.findAll();
    }

    @Override
    public Optional<Autor> getAutorById(Long id) {
        return autorRepository.findById(id);
    }

    @Override
    public Autor createAutor(Autor autor) throws Exception {
        if (!autorRepository.findByCorreo(autor.getCorreo()).isPresent()) {
            return autorRepository.save(autor);
        } else {
            throw new Exception("Ya hay un autor registrado con ese email.");
        }
    }

    @Override
    public Autor updateAutor(Long id, Autor autor) throws Exception {
        if (autorRepository.existsById(id)) {
            autor.setId(id);
            return autorRepository.save(autor);
        } else {
            throw new Exception("No se encontró ID de Autor.");
        }
    }

    @Override
    public void deleteAutor(Long id) throws Exception {
        if (autorRepository.existsById(id)) {
            autorRepository.deleteById(id);
        } else {
            throw new Exception("No se encontró ID de Autor.");
        }
    }

    @Override
    public Autor updateCantPub(Long id) {
        var autor = autorRepository.findById(id).get();
        autor.setPublicaciones(autor.getPublicaciones() + 1);
        return autorRepository.save(autor);
    }


    @Override
    public Autor updateCantEval(Long id) {
        var autor = autorRepository.findById(id).get();
        autor.setEvaluaciones(autor.getEvaluaciones() + 1);
        return autorRepository.save(autor);
    }

}
