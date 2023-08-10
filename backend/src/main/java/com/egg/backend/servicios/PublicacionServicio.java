package com.egg.backend.servicios;

import com.egg.backend.entidades.Publicacion;
import com.egg.backend.repositorios.CategoriaRepositorio;
import com.egg.backend.repositorios.PublicacionRepositorio;
import com.egg.backend.entidades.Categoria;
import com.egg.backend.entidades.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.egg.backend.entidades.Imagen;
import com.egg.backend.excepciones.MiException;

@Service
public class PublicacionServicio {

    @Autowired
    private PublicacionRepositorio publicacionRepositorio;
    @Autowired
    private CategoriaRepositorio categoriaRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void crearPublicacion(Usuario usuario, String contenido,
            MultipartFile imagen, String idCategoria) throws MiException {

        validar(imagen, idCategoria);
        Optional<Categoria> respuesta = categoriaRepositorio.findById(idCategoria);
        Categoria categoria = new Categoria();
        if (respuesta.isPresent()) {
            categoria = respuesta.get();
        }

        Publicacion publicacion = new Publicacion();
        publicacion.setUsuario(usuario);
        publicacion.setCategoria(categoria);
        publicacion.setContenido(contenido);
        publicacion.setFechaPublicacion(new Date());
        publicacion.setDarBaja(Boolean.FALSE);
        Imagen img = imagenServicio.guardar(imagen);
        publicacion.setImagen(img);
        publicacionRepositorio.save(publicacion);
    }

    @Transactional
    public void modificarPublicacion(String idPublicacion, String contenido, MultipartFile archivo, String idCategoria) throws MiException {

        Optional<Categoria> respuesta = categoriaRepositorio.findById(idCategoria);
        Categoria categoria = new Categoria();
        if (respuesta.isPresent()) {
            categoria = respuesta.get();
        }

        Optional<Publicacion> respuesta2 = publicacionRepositorio.findById(idPublicacion);
        if (respuesta.isPresent()) {
            Publicacion publicacion = respuesta2.get();

            if (archivo != null) {
                validar(archivo, idCategoria);

                publicacion.setCategoria(categoria);
                publicacion.setContenido(contenido);

                String idImagen = null;
                if (publicacion.getImagen() != null) {
                    idImagen = publicacion.getImagen().getId();
                }
                Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
                publicacion.setImagen(imagen);

                publicacionRepositorio.save(publicacion);

            } else {
                validar(idCategoria);

                publicacion.setCategoria(categoria);
                publicacion.setContenido(contenido);

                publicacionRepositorio.save(publicacion);
            }
        }
    }
    private void validar(MultipartFile imagen, String idCategoria) throws MiException {

        if (imagen == null) {
            throw new MiException("imagen no puede ser nula");
        }
        if (idCategoria.isEmpty() || idCategoria == null) {
            throw new MiException("el categoría no puede ser nulo o estar vacio");
        }
    }

    private void validar(String idCategoria) throws MiException {

        if (idCategoria.isEmpty()) {
            throw new MiException("el categoría no puede ser nulo o estar vacio");
        }
    }

    public List<Publicacion> listarPublicaciones() {

        List<Publicacion> publicaciones = new ArrayList();

        publicaciones = publicacionRepositorio.findAll();

        return publicaciones;
    }

    @Transactional
    public void darBaja(String id) throws MiException {

        Optional<Publicacion> respuesta = publicacionRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Publicacion publicacion = respuesta.get();

            if (!publicacion.getDarBaja()) {
                publicacion.setDarBaja(Boolean.TRUE);
            } else {
                publicacion.setDarBaja(Boolean.FALSE);
            }
        }
    }

    @Transactional
    public void eliminar(String id) throws MiException {

        Publicacion publicacion = publicacionRepositorio.getById(id);

        publicacionRepositorio.delete(publicacion);

    }

    public Publicacion getOne(String id) {
        return publicacionRepositorio.getOne(id);
    }

    public List<Publicacion> getOneCategoria(String nombre) {
        return publicacionRepositorio.buscarPorCategoria(nombre);
    }

    public List<Publicacion> getByFechaDesc() {
        return publicacionRepositorio.FechaDesc();
    }

    public List<Publicacion> getByFechaAsc() {
        return publicacionRepositorio.FechaAsc();
    }

    public List<Publicacion> getByMasLikes() {
        return publicacionRepositorio.buscarPublicacionesConMasLikes();
    }

    public List<Publicacion> getByAuthor(Usuario usuario) {
        return publicacionRepositorio.buscarPorAutor(usuario);
    }

    public List<Publicacion> orderByAuthor() {
        return publicacionRepositorio.ordenarAlfabeticamente();
    }

    public List<Publicacion> orderByInteraction() {
        return publicacionRepositorio.buscarPublicacionesConMasInteracciones();
    }
}
