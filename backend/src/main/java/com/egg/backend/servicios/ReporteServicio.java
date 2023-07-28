package com.egg.backend.servicios;

import com.egg.backend.entidades.Publicacion;
import com.egg.backend.entidades.Reporte;
import com.egg.backend.entidades.Usuario;
import com.egg.backend.enumeraciones.Categoria;
import com.egg.backend.excepciones.MiException;
import com.egg.backend.repositorios.ComentarioRepositorio;
import com.egg.backend.repositorios.PublicacionRepositorio;
import com.egg.backend.repositorios.ReporteRepositorio;
import com.egg.backend.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReporteServicio {

    @Autowired
    private ReporteRepositorio reporteRepositorio;
    @Autowired
    private ComentarioRepositorio comentarioRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private PublicacionRepositorio publicacionRepositorio;
    @Autowired
    private PublicacionServicio publicacionServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @Transactional
    public void registrarReporte(String usuarioId, String publicacionId, String comentarioId, Categoria categoria, String descripcion) throws MiException {
        validar(usuarioId, publicacionId, comentarioId, categoria, descripcion);

        Reporte reporte = new Reporte();
        if (usuarioId != null) {
            reporte.setUsuario(usuarioServicio.getOne(usuarioId));
        }
        if (publicacionId != null) {
            reporte.setPublicacion(publicacionServicio.getOne(publicacionId));
        }
        if (comentarioId != null) {
            //reporte.setComentario(comentarioServicio.getOne(comentarioId));
        }
        reporte.setCategoria(categoria);
        reporte.setDescripcion(descripcion);

        reporteRepositorio.save(reporte);
    }

    public Reporte getOne(String id) {
        return reporteRepositorio.getOne(id);
    }

    @Transactional
    public void actualizar(String id, String usuarioId, String publicacionId, String comentarioId, Categoria categoria, String descripcion) throws MiException {

        Optional<Reporte> respuesta = reporteRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Reporte reporte = new Reporte();
            if (usuarioId != null) {
                reporte.setUsuario(usuarioServicio.getOne(usuarioId));
            }
            if (publicacionId != null) {
                reporte.setPublicacion(publicacionServicio.getOne(publicacionId));
            }
            if (comentarioId != null) {
                //reporte.setComentario(comentarioServicio.getOne(comentarioId));
            }

            reporte.setCategoria(categoria);
            reporte.setDescripcion(descripcion);

            reporteRepositorio.save(reporte);
        }

    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Reporte> listarReportes() {

        List<Reporte> reportes = new ArrayList();

        reportes = reporteRepositorio.findAll();

        return reportes;
    }

    @Transactional
    public void eliminarReporte(String id) throws MiException {

        Reporte reporte = getOne(id);

        reporteRepositorio.delete(reporte);

    }

    public Long ContadorReporte() throws MiException {

        return reporteRepositorio.count();

    }

    private void validar(String usuarioId, String publicacionId, String comentarioId, Categoria categoria, String descripcion) throws MiException {

        if (usuarioId.isEmpty() || usuarioId == null && publicacionId.isEmpty() || publicacionId == null && comentarioId.isEmpty() || comentarioId == null) {
            throw new MiException("La id no puede estar vacia");
        }

        if (categoria == null) {
            throw new MiException("La categoria no puede estar vacia");
        }
        if (descripcion.isEmpty() || descripcion == null) {
            throw new MiException("La id no puede estar vacia");
        }

    }
    public int ContadorReporteUsuario(String usuarioId) throws MiException {

        Usuario usuario = usuarioRepositorio.getOne(usuarioId);
        List<Reporte> reportes = listarReportes();
        int contador = 1;

        for (Reporte x : reportes) {
            if (x.getUsuario() == usuario) {
                
                contador ++;
            }
        }

        return contador;
    }         
}
