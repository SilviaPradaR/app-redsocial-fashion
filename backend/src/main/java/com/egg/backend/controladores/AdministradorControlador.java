package com.egg.backend.controladores;

import com.egg.backend.entidades.Comentario;
import com.egg.backend.entidades.Publicacion;
import com.egg.backend.entidades.Reporte;
import com.egg.backend.entidades.Usuario;
import com.egg.backend.excepciones.MiException;
import com.egg.backend.servicios.ComentarioServicio;
import com.egg.backend.servicios.PublicacionServicio;
import com.egg.backend.servicios.ReporteServicio;
import com.egg.backend.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @GetMapping("/dashboard")
    public String administrador(){
        
        return "dashboard.html";        
    }

    @GetMapping("/reportes")
    public String listarReportes(ModelMap modelo) {

        List<Reporte> reportes = reporteServicio.listarReportes();
        modelo.addAttribute("reportes", reportes);

        return "reportes_lista.html";
    }

    @GetMapping("/usuarios")
    public String listarUsuarios(ModelMap modelo) {

        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);

        return "usuarios_lista.html";
    }
    
    @GetMapping("/publicaciones")
    public String listarPublicaciones(ModelMap modelo) {

        List<Publicacion> publicaciones = publicacionServicio.listarPublicaciones();
        modelo.addAttribute("publicaciones", publicaciones);

        return "publicaciones_lista.html";
    }
    
    @GetMapping("/comentarios")
    public String listarComentarios(ModelMap modelo) {

        List<Comentario> comentarios = comentarioServicio.listarComentarios();
        modelo.addAttribute("comentarios", comentarios);

        return "comentarios_lista.html";
    }
    
    @GetMapping("/darBajaUsuario/{id}")
    public String darBajaUsuario(@PathVariable String id) { // le falta controlar la excepcion en el servicio si es que va

        usuarioServicio.cambiarEstado(id);

        return "redirect:/admin/reportes";
    }
    
    @GetMapping("/darBajaComentario/{id}")
    public String darBajaComentario(@PathVariable String id, ModelMap modelo) {

        try {

            comentarioServicio.darBaja(id);
            modelo.put("Éxito", "El comentario fue dado de baja correctamente");
            return "redirect:/admin/reportes";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            return "redirect:/admin/reportes";
        }

    }
    
    @GetMapping("/darBajaPublicacion/{id}")
    public String darBajaPublicacion(@PathVariable String id, ModelMap modelo) {

        try {

            publicacionServicio.darBaja(id);;
            modelo.put("Éxito", "El publicación fue dada de baja correctamente");
            return "redirect:/admin/reportes";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            return "redirect:/admin/reportes";
        }

    }
    
    @GetMapping("/publicacion/eliminar/{id}")
    public String eliminarPublicacion(@PathVariable String id, ModelMap modelo) {

        try {

            publicacionServicio.eliminar(id);;
            modelo.put("Éxito", "La publicación fue eliminada correctamente");

            return "reportes_lista.html";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "reportes_lista.html";
        }
    }

    @GetMapping("/comentario/eliminar/{id}")
    public String eliminarComentario(@PathVariable String id, ModelMap modelo) {

        try {

            comentarioServicio.eliminarComentario(id);
            modelo.put("Éxito", "El comentario fue eliminado correctamente");

            return "reportes_lista.html";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "reportes_lista.html";
        }
    }

    @GetMapping("/usuario/eliminar/{id}")
    public String eliminarUsuario(@PathVariable String id, ModelMap modelo) {

        try {

            usuarioServicio.eliminar(id);
            modelo.put("Éxito", "El usuario fue eliminado correctamente");

            return "reportes_lista.html";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "reportes_lista.html";
        }
    }

    @GetMapping("/reportes/eliminar/{id}")
    public String eliminarReporte(@PathVariable String id, ModelMap modelo) {

        try {

            reporteServicio.eliminarReporte(id);
            modelo.put("Éxito", "El reporte fue eliminado correctamente");

            // List<Reporte> reportes = reporteServicio.listarReportes();
            // modelo.addAttribute("reportes", reportes);
            return "reportes_lista.html";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            // List<Reporte> reportes = reporteServicio.listarReportes();
            // modelo.addAttribute("reportes", reportes);
            return "reportes_lista";
        }
    }

}