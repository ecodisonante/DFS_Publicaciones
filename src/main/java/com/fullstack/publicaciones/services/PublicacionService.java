package com.fullstack.publicaciones.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fullstack.publicaciones.model.Publicacion;
import com.fullstack.publicaciones.repository.PublicacionRepository;

@Service
public class PublicacionService implements IPublicacionService {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private EvaluacionService evaluacionService;

    @Override
    public List<Publicacion> getAllPublicacions() {
        var pubs = publicacionRepository.findAll();
        for (Publicacion p : pubs) {
            var coms = getComentariosById(p.getId()).stream().map(Publicacion::toDto).toList();
            p.setComentarios(coms);
            p.setEvaluaciones(evaluacionService.getByPublicacionId(p.getId()));
        }
        return pubs;
    }

    @Override
    public Optional<Publicacion> getPublicacionById(Long id) {
        var pub = publicacionRepository.findById(id);
        if (pub.isPresent()) {
            pub.get().setEvaluaciones(evaluacionService.getByPublicacionId(id));
        }
        return pub;
    }

    @Override
    public List<Publicacion> getComentariosById(Long id) {
        return publicacionRepository.findByReferenciaId(id);
    }

    @Override
    public Publicacion createPublicacion(Publicacion publicacion) throws Exception {
        return publicacionRepository.save(publicacion);
    }

    @Override
    public Publicacion updatePublicacion(Long id, Publicacion publicacion) throws Exception {
        if (publicacionRepository.existsById(id)) {
            publicacion.setId(id);
            return publicacionRepository.save(publicacion);
        } else {
            throw new Exception("No se encontró ID de Publicacion.");
        }
    }

    @Override
    public void deletePublicacion(Long id) throws Exception {
        if (publicacionRepository.existsById(id)) {
            publicacionRepository.deleteById(id);
        } else {
            throw new Exception("No se encontró ID de Publicacion.");
        }
    }

}
