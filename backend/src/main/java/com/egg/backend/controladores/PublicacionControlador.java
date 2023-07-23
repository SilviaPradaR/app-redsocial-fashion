
package com.egg.backend.controladores;

import com.egg.backend.entidades.Publicacion;
import com.egg.backend.entidades.Usuario;
import com.egg.backend.entidades.Categoria;
import com.egg.backend.entidades.Imagen;
import com.egg.backend.excepciones.MiException;
import com.egg.backend.repositorios.PublicacionRepositorio;
import com.egg.backend.servicios.PublicacionServicio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/disenador")
public class PublicacionControlador {
    @Autowired
    private PublicacionRepositorio publicacionRepositorio;
    @Autowired
    private PublicacionServicio publicacionServicio;
  
    @GetMapping("/crear")
    public String registrar(){
        return "form_crearPost.html";
    }

   @PostMapping("/publicacion")
   public String publicacion(Usuario usuario, Categoria categoria, String contenido,
           Imagen imagen, ModelMap modelo) {
     

       try {

           publicacionServicio.crearPublicacion(usuario, categoria, contenido, imagen);

           modelo.put("exito", "La publicación fue cargada exitosamente!!!");

       } catch (MiException ex) {

           modelo.put("error", ex.getMessage());

           return "publicacion.html";
       }

       return "inicio.html";
   }
      
   @GetMapping("/listar")
   public String listar(ModelMap modelo) {

       List<Publicacion> publicaciones = publicacionServicio.listarPublicaciones();

       modelo.addAttribute("publicaciones", publicaciones);

       return "listarPublicaciones.html";
   }
 
   @GetMapping("/eliminar/{id}")
   public String eliminar(@PathVariable String id, ModelMap modelo) throws MiException{
       publicacionServicio.eliminar(id);
       return "redirect:../listar";
   }
}
