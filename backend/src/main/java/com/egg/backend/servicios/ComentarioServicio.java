package com.egg.backend.servicios;

import com.egg.backend.repositorios.ComentarioRepositorio;
import com.egg.backend.repositorios.PublicacionRepositorio;
import com.egg.backend.entidades.Comentario;
import com.egg.backend.entidades.Usuario;
import com.egg.backend.entidades.Publicacion;
import com.egg.backend.excepciones.MiException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComentarioServicio {

    @Autowired
    private ComentarioRepositorio comentarioRepositorio;
    
    @Autowired
    private PublicacionRepositorio publicacionRepositorio;

    @Transactional
    public void crearComentario(Usuario usuario, Publicacion publicacion, String contenido) throws MiException {

        validar(contenido);

        Comentario comentario = new Comentario();

        comentario.setUsuario(usuario);
        comentario.setPublicacion(publicacion);
        comentario.setContenido(contenido);
        comentario.setFechaComentario(new Date());
        comentario.setDarBaja(Boolean.FALSE);

        comentarioRepositorio.save(comentario);
    }

    @Transactional
    public void eliminarComentario(String id) throws MiException {

        Comentario comentario = comentarioRepositorio.getById(id);

        comentarioRepositorio.delete(comentario);

    }

    private void validar(String contenido) throws MiException {

        if (contenido == null || contenido.isEmpty()) {
            throw new MiException("El comentario no puede estar vacío ni ser nulo");
        }
    }
    
    @Transactional
    public void darBaja(String id) throws MiException {

        Optional<Comentario> respuesta = comentarioRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Comentario comentario = respuesta.get();

            if (!comentario.getDarBaja()) {
                comentario.setDarBaja(Boolean.TRUE);
            } else {
                comentario.setDarBaja(Boolean.FALSE);
            }
        }
    }
    
    public Comentario getOne(String id) {
        
        return comentarioRepositorio.getOne(id);
    }
    
    public List<Comentario> listarComentarios() {

        List<Comentario> comentarios = new ArrayList();

        comentarios = comentarioRepositorio.findAll();
        
        return comentarios;
    }
    
    public List<Comentario> listarComentariosPublicacion(String publicacionId) {
        
        Publicacion publicacion = publicacionRepositorio.getOne(publicacionId);
        List<Comentario> comentarios = new ArrayList();
        comentarios = comentarioRepositorio.findAll();
        List<Comentario> comentariosPub = new ArrayList();
        
        for (Comentario c : comentarios) {
            if (c.getPublicacion() == publicacion) {
                
                comentariosPub.add(c);
            }
        }        
        return comentariosPub;
    }
    
    public int contadorComentariosPublicacion(String publicacionId) throws MiException {

        Publicacion publicacion = publicacionRepositorio.getOne(publicacionId);
        List<Comentario> comentarios = listarComentarios();
        int contador = 0;

        for (Comentario c : comentarios) {
            if (c.getPublicacion() == publicacion) {
                
                contador ++;
            }
        }
        return contador;
    }  
}
