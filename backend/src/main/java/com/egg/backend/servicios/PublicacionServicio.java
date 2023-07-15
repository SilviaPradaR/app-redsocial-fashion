package com.egg.backend.servicios;

import com.egg.backend.entidades.Imagen;
import com.egg.backend.enumeraciones.Rol;
import com.egg.backend.exepciones.MiException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * @author Nahuel
 */
@Service
public class PublicacionServicio {
    
    @Autowired
    private PublicacionRepositorio publicacionRepositorio;
    
    @Transactional
    public void crearPublicacion(Imagen imagen,Usuario usuario,Categoria categoria,String contenido) throws MiException{
        
        validar(imagen,categoria);
        
        Publicacion publicacion= new Publicacion();
        
        publicacion.setUsuario(usuario);
        publicacion.setCategoria(categoria);
        publicacion.setContenido(contenido);
        publicacion.setImagen(imagen);
        publicacion.setFechaPublicacion(new Date());
        publicacion.setDarBaja(false);
        publicacionRepositorio.save(publicacion);
    }
   
    private void validar(Imagen imagen,Categoria categoria ) throws MiException {
                
        if (imagen == null) {
            throw new MiException("imagen no puede ser nula");
        }
        if (categoria == null) {
            throw new MiException("imagen no puede ser nula");
        }
        
    }
    
    @Transactional
    public void eliminar(String id) throws MiException {

        Publicacion publicacion= publicacionRepositorio.getById(id);

        publicacionRepositorio.delete(publicacion);

    }
    
    public Publicacion getOne(Long id) {
        return publicRepositorio.getOne(id);
    }
    
    
}
