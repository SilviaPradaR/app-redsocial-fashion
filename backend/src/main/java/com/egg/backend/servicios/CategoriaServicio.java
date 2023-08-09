
package com.egg.backend.servicios;

import com.egg.backend.entidades.Categoria;
import com.egg.backend.excepciones.MiException;
import com.egg.backend.repositorios.CategoriaRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServicio {
    
    
    @Autowired
    private CategoriaRepositorio categoriaRepositorio;
    
    @Transactional
    public void crearCategoria(String nombre) throws MiException{
        
        validar(nombre);
        
        Categoria categoria = new Categoria();
        
        categoria.setNombre(nombre);
        
        categoriaRepositorio.save(categoria);
        
    }
    
    @Transactional
    public void eliminarCategoria(String id) throws MiException {
        
        Categoria categoria = categoriaRepositorio.getById(id);
        categoriaRepositorio.delete(categoria);
        
    }
    
    public void validar (String nombre) throws MiException{
        
        if (nombre == null || nombre.isEmpty()) {
            throw new MiException ("La categoria no puede ser nula o estar vacia");
        }    
    }
    
    @Transactional
    public void modificarCategoria(String id, String nombre) throws MiException{
        
        validar(nombre);
        
        Optional <Categoria> respuesta= categoriaRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            
            Categoria categoria = respuesta.get();
            
            categoria.setNombre(nombre);
            
            categoriaRepositorio.save(categoria);
        }
    }
    
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Categoria> listarCategoria() {

        List<Categoria> categorias = new ArrayList();

        categorias = categoriaRepositorio.findAll();

        return categorias;
    }
    
    public Categoria getOne(String id) {
        
        return categoriaRepositorio.getOne(id);
    }
    
}

