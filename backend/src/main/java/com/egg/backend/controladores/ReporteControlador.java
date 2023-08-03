package com.egg.backend.controladores;

import com.egg.backend.entidades.Comentario;
import com.egg.backend.entidades.Publicacion;
import com.egg.backend.enumeraciones.Categoria;
import com.egg.backend.entidades.Usuario;
import com.egg.backend.excepciones.MiException;
import com.egg.backend.repositorios.ComentarioRepositorio;
import com.egg.backend.repositorios.PublicacionRepositorio;
import com.egg.backend.repositorios.ReporteRepositorio;
import com.egg.backend.repositorios.UsuarioRepositorio;
import com.egg.backend.servicios.ReporteServicio;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reporte")
public class ReporteControlador {

    
    @Autowired
    private ReporteServicio reporteServicio;

    @Autowired
    private ReporteRepositorio reporteRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ComentarioRepositorio comentarioRepositorio;

    @Autowired
    private PublicacionRepositorio publicacionRepositorio;

//    @GetMapping("/registrarReporte")
//    public String registrarReporte() {
//
//        return "reporte_form.html";
//
//    }

    @PostMapping("/registroReporte")
    public String registroReporte(@RequestParam String id, @RequestParam Categoria categoria, String descripcion, ModelMap modelo) {

        try {
            
            Optional<Usuario> respuestaUsuario = usuarioRepositorio.findById(id);

            if (respuestaUsuario.isPresent()) {
                reporteServicio.registrarReporte(id, null, null, categoria, descripcion);
            }

            Optional<Comentario>respuestaComentario= comentarioRepositorio.findById(id);
        
            if (respuestaComentario.isPresent()) {
                reporteServicio.registrarReporte(null, null,id,categoria,descripcion);
            }
            
            Optional<Publicacion> respuestaPublicacion = publicacionRepositorio.findById(id);

            if (respuestaPublicacion.isPresent()) {
                reporteServicio.registrarReporte(null, id, null, categoria, descripcion);
            }

            modelo.put("exito", "reporte enviado correctamente");
             System.out.println(id);
             System.out.println(categoria);
             System.out.println(descripcion);
            return "redirect:../../inicio";

        } catch (MiException e) {
            modelo.put("error al encontrar id", e.getMessage());
        System.out.println("Error al registrar reporte: " + e.getMessage());
            return "redirect:/reporte/registroReporte";
        }
        

    }
    
    

}
