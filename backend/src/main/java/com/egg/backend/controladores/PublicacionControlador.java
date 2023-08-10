package com.egg.backend.controladores;

import com.egg.backend.entidades.Publicacion;
import com.egg.backend.entidades.Usuario;
import com.egg.backend.entidades.Categoria;
import com.egg.backend.entidades.Comentario;
import com.egg.backend.excepciones.MiException;
import com.egg.backend.servicios.CategoriaServicio;
import com.egg.backend.servicios.ComentarioServicio;
import com.egg.backend.servicios.LikeServicio;
import com.egg.backend.servicios.PublicacionServicio;
import com.egg.backend.servicios.UsuarioServicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/disenador")
public class PublicacionControlador {

    @Autowired
    private CategoriaServicio categoriaServicio;
    @Autowired
    private PublicacionServicio publicacionServicio;
    @Autowired
    private LikeServicio likeServicio;
    @Autowired
    private ComentarioServicio comentarioServicio;

    @GetMapping("/crear")
    public String registrar(ModelMap modelo) {
        List<Categoria> categorias = categoriaServicio.listarCategoria();

        modelo.addAttribute("categorias", categorias);
        return "form_crearPost.html";
    }

    @GetMapping("/ver/{id}")
    public String registrar(@PathVariable String id, ModelMap modelo,HttpSession session) throws MiException {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        Publicacion publicacion = publicacionServicio.getOne(id);
        List<Comentario> comentariosPublicacion = comentarioServicio.listarComentariosPublicacion(id);                                
        int conteo = likeServicio.contadorLike(publicacion.getId());
        int conteoComentarios = comentarioServicio.contadorComentariosPublicacion(id);
        Map<String, Boolean> usuarioDioLikeMap = new HashMap<>();  
        boolean usuarioDioLike = likeServicio.usuarioDioLike(usuario, publicacion);
        usuarioDioLikeMap.put(publicacion.getId(), usuarioDioLike);  
        
        modelo.addAttribute("publicacion", publicacion);
        modelo.addAttribute("comentarios", comentariosPublicacion);
        modelo.addAttribute("conteoLike", conteo);      
        modelo.addAttribute("conteoComentarios", conteoComentarios);              
        modelo.addAttribute("usuarioDioLikeMap", usuarioDioLikeMap);
        
        return "publicacion_detalle.html";
    }

    @PostMapping("/publicacion")
    public String publicacion(HttpSession session, String contenido,
            @RequestParam MultipartFile imagen, ModelMap modelo, String idCategoria) {

        try {
            Usuario logueado = (Usuario) session.getAttribute("usuariosession");
            publicacionServicio.crearPublicacion(logueado, contenido, imagen, idCategoria);

            modelo.put("exito", "La publicación fue cargada exitosamente!!!");
            return "redirect:../perfil/"+ logueado.getId();

        } catch (MiException ex) {
            Logger.getLogger(PublicacionControlador.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("error", ex.getMessage());

            return "home.html";
        }
        
        
    }

    @GetMapping("/listar")
    public String listar(ModelMap modelo) {

        List<Publicacion> publicaciones = publicacionServicio.listarPublicaciones();

        modelo.addAttribute("publicaciones", publicaciones);

        return "listarPublicaciones.html";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) throws MiException {
        String idUsuario = publicacionServicio.getOne(id).getUsuario().getId();
        publicacionServicio.eliminar(id);
        return "redirect:../../perfil/"+ idUsuario;
    }
}
