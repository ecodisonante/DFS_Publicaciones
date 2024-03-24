package com.fullstack.publicaciones.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.fullstack.publicaciones.data.TestingData;
import com.fullstack.publicaciones.model.Publicacion;
import com.fullstack.publicaciones.model.PublicacionDTO;

@Service
public class PublicacionServices {

    private TestingData data;

    public PublicacionServices(TestingData data){
        this.data = data;
        poblarData();
    }

    public Publicacion getById(int id) {
        for (Publicacion pub : data.publicaciones) {
            if (pub.getId() == id)
                return pub;
        }
        return null;
    }

    public List<PublicacionDTO> getPublicaciones() {
        List<PublicacionDTO> publicaciones = new ArrayList<>();

        for (Publicacion pub : data.publicaciones) {
            publicaciones.add(new PublicacionDTO(
                    pub.getId(),
                    pub.getnombreAutor(),
                    pub.getTitulo(),
                    pub.getResumen(),
                    pub.getCantidadComentarios(),
                    pub.getPuntaje()));
        }

        return publicaciones;
    }

    public void poblarData() {
        for (Publicacion pub : data.publicaciones) {
            pub.setComentarios(
                data.comentarios.stream().filter(x -> x.getPublicacionId() == pub.getId()).toList()
            );
        }
    }
}
