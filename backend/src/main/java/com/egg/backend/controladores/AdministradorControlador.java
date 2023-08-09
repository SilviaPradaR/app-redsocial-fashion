package com.egg.backend.controladores;

import com.egg.backend.entidades.Categoria;
import com.egg.backend.entidades.Comentario;
import com.egg.backend.entidades.Publicacion;
import com.egg.backend.entidades.Reporte;
import com.egg.backend.entidades.Usuario;
import com.egg.backend.excepciones.MiException;
import com.egg.backend.servicios.CategoriaServicio;
import com.egg.backend.servicios.ComentarioServicio;
import com.egg.backend.servicios.PublicacionServicio;
import com.egg.backend.servicios.ReporteServicio;
import com.egg.backend.servicios.UsuarioServicio;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrador")
public class AdministradorControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ReporteServicio reporteServicio;

    @Autowired
    private PublicacionServicio publicacionServicio;

    @Autowired
    private ComentarioServicio comentarioServicio;
    @Autowired
    private CategoriaServicio categoriaServicio;

    @GetMapping("/dashboard")
    public String administrador(ModelMap modelo) throws MiException {
        
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        
        Map<String, Integer> conteoRepUsuario = new HashMap<>();
        for (Usuario u : usuarios) {

            int conteo = reporteServicio.contadorReporteUsuario(u.getId());
            conteoRepUsuario.put(u.getId(), conteo);
        }
        
        List<Publicacion > publicaciones = publicacionServicio.listarPublicaciones();
        
        Map<String, Integer> conteoRepPublicacion = new HashMap<>();
        for (Publicacion p : publicaciones) {

            int conteo = reporteServicio.contadorReportePublicacion(p.getId());
            conteoRepPublicacion.put(p.getId(), conteo);
        }
        
        List<Comentario> comentarios = comentarioServicio.listarComentarios(); 
        
        Map<String, Integer> conteoRepComentario = new HashMap<>();
        for (Comentario c : comentarios) {

            int conteo = reporteServicio.contadorReporteComentario(c.getId());
            conteoRepComentario.put(c.getId(), conteo);
        }
        
        List<Reporte> reportes = reporteServicio.listarReportes();
        List<Categoria> categorias = categoriaServicio.listarCategoria();

        modelo.addAttribute("usuarios", usuarios);
        modelo.addAttribute("publicaciones", publicaciones);
        modelo.addAttribute("comentarios", comentarios);
        modelo.addAttribute("reportes", reportes);
        modelo.addAttribute("categorias", categorias);
        modelo.addAttribute("conteoUsuario", conteoRepUsuario);
        modelo.addAttribute("conteoPublicacion", conteoRepPublicacion);
        modelo.addAttribute("conteoComentario", conteoRepComentario);

        return "dashboard.html";
    }

    @GetMapping("/darBajaUsuario/{id}")
    public String darBajaUsuario(@PathVariable String id) { // le falta controlar la excepcion en el servicio si es que va

        usuarioServicio.cambiarEstado(id);

        return "redirect:/administrador/dashboard";
    }

    @GetMapping("/darBajaComentario/{id}")
    public String darBajaComentario(@PathVariable String id, ModelMap modelo) {

        try {

            comentarioServicio.darBaja(id);
            modelo.put("Éxito", "El comentario fue dado de baja correctamente");
            return "redirect:/administrador/dashboard";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            return "redirect:/administrador/dashboard";
        }

    }

    @GetMapping("/darBajaPublicacion/{id}")
    public String darBajaPublicacion(@PathVariable String id, ModelMap modelo) {

        try {

            publicacionServicio.darBaja(id);
            modelo.put("Éxito", "El publicación fue dada de baja correctamente");
            return "redirect:/administrador/dashboard";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            return "redirect:/administrador/dashboard";
        }

    }
    
    @GetMapping("/darBajaReporte/{id}")
    public String darBajaReporte(@PathVariable String id, ModelMap modelo) {

        try {

            reporteServicio.darBaja(id);
            modelo.put("Éxito", "El reporte fue dado de baja correctamente");
            return "redirect:/administrador/dashboard";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            return "redirect:/administrador/dashboard";
        }

    }

    @GetMapping("/publicacion_eliminar/{id}")
    public String eliminarPublicacion(@PathVariable String id, ModelMap modelo) {

        try {

            publicacionServicio.eliminar(id);
            modelo.put("Éxito", "La publicación fue eliminada correctamente");

            return "redirect:/administrador/dashboard";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "redirect:/administrador/dashboard";
        }
    }

    @GetMapping("/comentario_eliminar/{id}")
    public String eliminarComentario(@PathVariable String id, ModelMap modelo) {

        try {

            comentarioServicio.eliminarComentario(id);
            modelo.put("Éxito", "El comentario fue eliminado correctamente");

            return "redirect:/administrador/dashboard";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "redirect:/administrador/dashboard";
        }
    }

    @GetMapping("/usuario_eliminar/{id}")
    public String eliminarUsuario(@PathVariable String id, ModelMap modelo) {

        try {

            usuarioServicio.eliminar(id);
            modelo.put("Éxito", "El usuario fue eliminado correctamente");

            return "redirect:/administrador/dashboard";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "redirect:/administrador/dashboard";
        }
    }

    @GetMapping("/reportes_eliminar/{id}")
    public String eliminarReporte(@PathVariable String id, ModelMap modelo) {
        
        try {

            reporteServicio.eliminarReporte(id);
            modelo.put("Éxito", "El reporte fue eliminado correctamente");

            // List<Reporte> reportes = reporteServicio.listarReportes();
            // modelo.addAttribute("reportes", reportes);
            return "redirect:/administrador/dashboard";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            // List<Reporte> reportes = reporteServicio.listarReportes();
            // modelo.addAttribute("reportes", reportes);
           return "redirect:/administrador/dashboard";
        }
    }
       @GetMapping("/crearCategoria")
    public String crearCategoria(){
        
        

        
        return "redirect:/administrador/dashboard";
    }
    @PostMapping("/crearCategoria")
    public String crearCategoria(String nombre, ModelMap modelo){
        
          try {

            categoriaServicio.crearCategoria(nombre);
            modelo.put("Éxito", "La categoria fue creada correctamente");

            return "redirect:/administrador/dashboard";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            return "redirect:/administrador/dashboard";
        }
        
    }
    @GetMapping("/categoria_eliminar/{id}")
    public String eliminarCategoria(@PathVariable String id, ModelMap modelo) {

        try {

            categoriaServicio.eliminarCategoria(id);
            modelo.put("Éxito", "La categoria fue eliminada correctamente");

            return "redirect:/administrador/dashboard";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "redirect:/administrador/dashboard";
        }
    }
}
