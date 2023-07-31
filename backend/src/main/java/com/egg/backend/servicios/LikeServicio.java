
package com.egg.backend.servicios;

import com.egg.backend.entidades.Categoria;
import com.egg.backend.entidades.Like;
import com.egg.backend.entidades.Publicacion;
import com.egg.backend.entidades.Usuario;
import com.egg.backend.excepciones.MiException;
import com.egg.backend.repositorios.LikeRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServicio {
    
    @Autowired
    private LikeRepositorio likeRepositorio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Transactional
    public void darLike (Usuario usuario, Publicacion publicacion) throws MiException {
        
        if (!validarLike(publicacion, usuario)) {
            
            Like like = new Like();
        
            like.setUsuario(usuario);
            like.setPublicacion(publicacion);
            like.setFechaLike(new Date());

            likeRepositorio.save(like); 
        }else{
            quitarLike(usuario, publicacion);
                    
                    
        }
        
                   
    }
    
    @Transactional
    public void eliminarLike(String id) throws MiException {

        Like like = likeRepositorio.getById(id);

        likeRepositorio.delete(like);
    }  
    @Transactional
    public void quitarLike(Usuario usuario, Publicacion publicacion) throws MiException {
        List<Like> likes = listarLikes();
        
         for (Like x : likes) {
            if (x.getUsuario() == usuario && x.getPublicacion() == publicacion ) {
                likeRepositorio.delete(x);
                
            }
        }

        
    }  
        
    public Long ContadorLikes() throws MiException {

        return likeRepositorio.count();

    }    
     @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Like> listarLikes() {

        List<Like> likes = new ArrayList();

        likes = likeRepositorio.findAll();

        return likes;
    }
    
    public Like getOne(String id) {
        
        return likeRepositorio.getOne(id);
    }
    
    public boolean validarLike(Publicacion publicacion,Usuario usuario){
        
       
        return likeRepositorio.existsById(publicacion.getId())&& likeRepositorio.existsById(usuario.getId());
//        if (like.getUsuario()==null && like.getPublicacion()==null) {
//            
//            return true;
//        }else{
//            return false;
//        }
 
        
    }

}
