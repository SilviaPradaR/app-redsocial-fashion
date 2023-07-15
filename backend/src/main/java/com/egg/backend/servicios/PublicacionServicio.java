
package com.egg.backend.servicios;

import com.egg.backend.entidades.Publicacion;
import com.egg.backend.repositorios.PublicacionRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PublicacionServicio {
    
    @Autowired
    private PublicacionRepositorio publicacionRepositorio;
    
      @Transactional
    public void crearPublicacion(Usuario usuario, Categoria categoria, String contenido,
            Imagen imagen) throws MiException{
                         
        validar(categoria, imagen);
        
        Publicacion publicacion = new Publicacion();
        
        publicacion.setUsuario(usuario);
        
        publicacion.setCategoria(categoria);
        
        publicacion.setContenido(contenido);    
               
        publicacion.setFechaPublicacion(new Date());               
                
        publicacion.setDarBaja(Boolean.FALSE);
        
        publicacion.setImagen(imagen);
       
        publicacionRepositorio.save(publicacion);        

    }
    
      public List<Publicacion> listarPublicaciones(){
        
        List<Publicacion> publicaciones = new ArrayList();
        
        publicaciones = publicacionRepositorio.findAll();
        
        return publicaciones;
    }     
    
}
