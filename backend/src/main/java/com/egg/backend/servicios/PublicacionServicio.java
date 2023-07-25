package com.egg.backend.servicios;

import com.egg.backend.entidades.Publicacion;
import com.egg.backend.repositorios.PublicacionRepositorio;
import com.egg.backend.entidades.Categoria;
import com.egg.backend.entidades.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.egg.backend.entidades.Imagen;
import com.egg.backend.enumeraciones.Rol;
import com.egg.backend.excepciones.MiException;

@Service
public class PublicacionServicio {
    
    @Autowired
    private PublicacionRepositorio publicacionRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;
    
    @Transactional
    public void crearPublicacion(Usuario usuario, String contenido,
    MultipartFile imagen) throws MiException{ //configurar categoria
                         
//        validar(imagen, categoria);
        
        Publicacion publicacion = new Publicacion();
        
        publicacion.setUsuario(usuario);
        // publicacion.setCategoria(categoria); al configurar categoria descomentar        
        publicacion.setContenido(contenido);              
        publicacion.setFechaPublicacion(new Date());
        publicacion.setDarBaja(Boolean.FALSE);
        Imagen img = imagenServicio.guardar(imagen);
        publicacion.setImagen(img);
        publicacionRepositorio.save(publicacion); 
    }

//        private void validar(Imagen imagen,Categoria categoria ) throws MiException {
//                
//        if (imagen == null) {
//            throw new MiException("imagen no puede ser nula");
//        }
//        if (categoria == null) {
//            throw new MiException("La categoria no puede ser nula");
//        }
//        
//    }

    public List<Publicacion> listarPublicaciones(){
        
        List<Publicacion> publicaciones = new ArrayList();
        
        publicaciones = publicacionRepositorio.findAll();
        
        return publicaciones;
    }     

     @Transactional
    public void eliminar(String id) throws MiException {

        Publicacion publicacion= publicacionRepositorio.getById(id);

        publicacionRepositorio.delete(publicacion);

    }
    
    public Publicacion getOne(String id) {
        return publicacionRepositorio.getOne(id);
    }
}