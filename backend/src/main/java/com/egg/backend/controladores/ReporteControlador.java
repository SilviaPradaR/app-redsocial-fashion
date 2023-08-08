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
import com.egg.backend.servicios.ComentarioServicio;
import com.egg.backend.servicios.ReporteServicio;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
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
    
    @Autowired
    private ComentarioServicio comentarioServicio;
    
    @Autowired
    private HttpServletRequest request;

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
            Optional<Comentario>respuestaComentario= comentarioRepositorio.findById(id);
            Optional<Publicacion> respuestaPublicacion = publicacionRepositorio.findById(id); 
            String Url = request.getRequestURI();             

            if (respuestaUsuario.isPresent()) {
                reporteServicio.registrarReporte(id, null, null, categoria, descripcion);
                    modelo.put("exito", "reporte enviado correctamente");     
               String idUsuario= respuestaUsuario.get().getId();
            return "redirect:../perfil/"+idUsuario;
            }else if (respuestaComentario.isPresent()) {
                reporteServicio.registrarReporte(null, null,id,categoria,descripcion);
                String idPublicacion = respuestaComentario.get().getPublicacion().getId();
                    modelo.put("exito", "reporte enviado correctamente");             
            return "redirect:../disenador/ver/"+idPublicacion;
            }else if ((respuestaPublicacion.isPresent())&&(Url.contains("/inicio"))) {
                Boolean b=Url.contains("/inicio");
                System.out.println(b);
                reporteServicio.registrarReporte(null, id, null, categoria, descripcion);
                modelo.put("exito", "reporte enviado correctamente");             
            return "redirect:../inicio";
            }else{ 
                reporteServicio.registrarReporte(null, id, null, categoria, descripcion);
                String idUsuario = respuestaPublicacion.get().getUsuario().getId();
               return "redirect:../perfil/"+idUsuario;
            }            

        } catch (MiException e) {
            modelo.put("error", e.getMessage());        
            return "redirect:/registroReporte";
        }
        

    }
    
    

}
