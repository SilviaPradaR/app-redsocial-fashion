package com.egg.backend.servicios;

import com.egg.backend.entidades.Imagen;
import com.egg.backend.entidades.Usuario;
import com.egg.backend.enumeraciones.Rol;
import com.egg.backend.excepciones.MiException;
import com.egg.backend.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;
    
    @Transactional
    public void registrar(MultipartFile archivo, String nombreCompleto, String nombreUsuario, String email, String password, String password2, Rol rol) throws MiException {

        

        Usuario usuario = new Usuario();

        usuario.setNombreCompleto(nombreCompleto);
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setEmail(email);
        //usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setFechaAlta(new Date());
        usuario.setDarBaja(false);
        usuario.setRol(Rol.USER);
        Imagen imagen = imagenServicio.guardar(archivo);

        usuario.setImagen(imagen);

        usuarioRepositorio.save(usuario);
    }
    public Usuario getOne(String id) {
        return usuarioRepositorio.getOne(id);
    }
    
    @Transactional
    public void actualizar(MultipartFile archivo, String id, String nombreCompleto, String nombreUsuario, String email, String password, String password2, Rol rol) throws MiException {

        

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();
            usuario.setNombreCompleto(nombreCompleto);
            usuario.setNombreUsuario(nombreUsuario);
            usuario.setEmail(email);
            //usuario.setPassword(new BCryptPasswordEncoder().encode(password));
                        
            String idImagen = null;

            if (usuario.getImagen() != null) {
                idImagen = usuario.getImagen().getId();
            }

            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
            usuario.setImagen(imagen);

            usuarioRepositorio.save(usuario);
        }

    }
    
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {

        List<Usuario> usuarios = new ArrayList();

        usuarios = usuarioRepositorio.findAll();

        return usuarios;
    }
    
   @Transactional
    public void eliminar(String id) throws MiException {

        Usuario usuario= getOne(id);

        usuarioRepositorio.delete(usuario);

    }
    
}
