
package com.egg.backend.servicios;

import com.egg.backend.entidades.Like;
import com.egg.backend.entidades.Publicacion;
import com.egg.backend.entidades.Usuario;
import com.egg.backend.excepciones.MiException;
import com.egg.backend.repositorios.LikeRepositorio;
import java.util.Date;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServicio {
    
    @Autowired
    private LikeRepositorio likeRepositorio;
    
    @Transactional
    public void darLike (Usuario usuario, Publicacion publicacion) throws MiException {
        
        Like like = new Like();
        
        like.setUsuario(usuario);
        like.setPublicacion(publicacion);
        like.setFechaLike(new Date());
        
        likeRepositorio.save(like);            
    }
    
    @Transactional
    public void eliminarLike(String id) throws MiException {

        Like like = likeRepositorio.getById(id);

        likeRepositorio.delete(like);
    }       
}