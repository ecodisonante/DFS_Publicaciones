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

    @Override
    public List<Publicacion> getAllPublicacions() {

        var pubs = publicacionRepository.findAll();

        // Cargar lista de referencias
        for (Publicacion p : pubs) {
            p.setRefList(getReferencias(p.getId()));
        }

        return pubs;
    }

    @Override
    public Optional<Publicacion> getPublicacionById(Long id) {
        return publicacionRepository.findById(id);
    }
    
    @Override
    public List<Publicacion> getReferencias(Long id) {
        return publicacionRepository.findByReferencia(id);
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
    public Publicacion updateValoracion(Long id) {
        var pub = publicacionRepository.findById(id).get();
        var refs = publicacionRepository.findByReferencia(id);

        if (refs.size() > 0)
            pub.setValoracion(refs.stream().mapToDouble(Publicacion::getValoracion).average().getAsDouble());
        else
            pub.setValoracion(0);

        return publicacionRepository.save(pub);
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
