package com.egg.backend.controladores;

import com.egg.backend.servicios.ComentarioServicio;
import com.egg.backend.entidades.Usuario;
import com.egg.backend.entidades.Publicacion;
import com.egg.backend.excepciones.MiException;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/comentario")
public class ComentarioControlador {

    @Autowired
    private ComentarioServicio comentarioServicio;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_DISENIADOR')")
    @GetMapping("/comentar")
    public String comentar() {

        return "comentario_form.html";
    }

    @PostMapping("/crearComentario")
    public String edicion(@RequestParam String contenido, Publicacion publicacion, ModelMap modelo, HttpSession session) {
        System.out.println("Entro a crear comentario");
        try {
            Usuario usuario = (Usuario) session.getAttribute("usuariosession");

            comentarioServicio.crearComentario(usuario, publicacion, contenido);

            modelo.put("Éxito", "El comentario fue cargado correctamente");

        } catch (MiException ex) {

            modelo.put("Error", ex.getMessage());

            return "comentario_form.html";

        }
        return "index.html"; //CONTROLAR-----------------------------------------------------------------
    }

    @GetMapping("/eliminarComentario/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {

        try {

            comentarioServicio.eliminarComentario(id);
            modelo.put("Éxito", "El comentario fue eliminado correctamente");

            return ".html"; // COMPLETAR-----------------------------------------------------------------

        } catch (MiException ex) {

            modelo.put("Error", ex.getMessage());

            return ".html"; // COMPLETAR-----------------------------------------------------------------
        }
    }

}
