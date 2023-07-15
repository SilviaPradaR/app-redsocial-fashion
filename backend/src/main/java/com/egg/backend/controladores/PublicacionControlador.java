/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.backend.controladores;

import com.egg.backend.entidades.Publicacion;
import com.egg.backend.entidades.Usuario;
import com.egg.backend.servicios.PublicacionServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author INGENIERO
 */
public class PublicacionControlador {
    
@Controller
@RequestMapping("/diseñador")
public class panelDisenador {

    @Autowired
    private PublicacionServicio publicacionServicio;
  

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
    public String eliminar(@PathVariable Long id, ModelMap modelo) throws MiException{
        publicacionServicio.eliminar(id);
        return "redirect:../listar";
    }
}
