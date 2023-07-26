package com.egg.backend.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.egg.backend.entidades.Publicacion;

import com.egg.backend.servicios.PublicacionServicio;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {    
    @Autowired
    private PublicacionServicio publicacionServicio;
    @GetMapping("/publicacion/{id}")
    public ResponseEntity<byte[]> imagenPublicacion (@PathVariable String id){
     Publicacion publicacion = publicacionServicio.getOne(id);
        
       byte[] imagen= publicacion.getImagen().getContenido();
       
       HttpHeaders headers = new HttpHeaders();
       
       headers.setContentType(MediaType.IMAGE_JPEG);
       
        
        
       return new ResponseEntity<>(imagen,headers, HttpStatus.OK); 
    }
}
