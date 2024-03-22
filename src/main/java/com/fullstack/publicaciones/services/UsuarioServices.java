package com.fullstack.publicaciones.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.fullstack.publicaciones.data.TestingData;
import com.fullstack.publicaciones.model.Usuario;
import com.fullstack.publicaciones.model.UsuarioDTO;

@Service
public class UsuarioServices {

    private TestingData data;

    public UsuarioServices(TestingData data) {
        this.data = data;
    }

    /**
     * Retorna el usuario por su ID.
     * Si no existe retorna null
     */
    public UsuarioDTO getById(String id) {
        for (Usuario usr : data.usuarios) {
            if (usr.getId().equals(id))
                return toUsuarioDTO(usr);
        }
        return null;
    }

    public List<UsuarioDTO> getUsuarios() {
        List<UsuarioDTO> usuarios = new ArrayList<>();
        for (Usuario usr : data.usuarios) {
            usuarios.add(toUsuarioDTO(usr));
        }
        return usuarios;
    }

    public UsuarioDTO toUsuarioDTO(Usuario user) {
        int publicaciones = data.publicaciones.stream().filter(x -> x.getAutor().equals(user)).toList().size();
        int comentarios = data.comentarios.stream().filter(x -> x.getAutor().equals(user)).toList().size();
        return new UsuarioDTO(user.getId(), user.getName(), user.getEmail(), publicaciones, comentarios);
    }
}
