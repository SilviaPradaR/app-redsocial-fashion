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
    public String administrador(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        List<Publicacion> publicaciones = publicacionServicio.listarPublicaciones();
        List<Reporte> reportes = reporteServicio.listarReportes();
        List<Comentario> comentarios = comentarioServicio.listarComentarios();
        
        modelo.addAttribute("usuarios", usuarios);
        modelo.addAttribute("publicaciones", publicaciones);
        modelo.addAttribute("reportes", reportes);
        modelo.addAttribute("comentarios", comentarios);

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
            return "redirect:/admin/reportes";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            return "redirect:/admin/reportes";
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

            return "reportes_lista.html";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "reportes_lista.html";
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
            return "reportes_lista.html";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            // List<Reporte> reportes = reporteServicio.listarReportes();
            // modelo.addAttribute("reportes", reportes);
            return "reportes_lista";
        }
    }

//    @GetMapping("/usuario_reporte_contador/{id}")
//    public String contadorUsuario(@PathVariable String id, ModelMap modelo) {
//        
//        try {
//            reporteServicio.contadorReporteUsuario(id);
//            
//            return "dashboard.html";
//
//        } catch (MiException ex) {
//            
//            modelo.put("error", ex.getMessage());
//
//            return "dashboard.html";
//        }
//    }
    
    @GetMapping("/usuario_reporte_contador/{id}")
    public String conteoReportesUsuario(@PathVariable String id, ModelMap modelo) throws MiException {

        modelo.put("cantidad", reporteServicio.contadorReporteUsuario(id));

        return "dashboard.html";
    }


    @GetMapping("/comentario_reporte_contador/{id}")
    public String conteoReportesComentario(@PathVariable String id, ModelMap modelo) throws MiException {

        modelo.put("cantidad", reporteServicio.contadorReporteComentario(id));

        return "dashboard.html";
    }
    
    @GetMapping("/publicacion_reporte_contador/{id}")
    public String conteoReportesPublicacion(@PathVariable String id, ModelMap modelo) throws MiException {

        modelo.put("cantidad", reporteServicio.contadorReportePublicacion(id));

        return "dashboard.html";
    }
    
}
