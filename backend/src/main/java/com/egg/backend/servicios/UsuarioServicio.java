package com.egg.backend.servicios;

import com.egg.backend.entidades.Imagen;
import com.egg.backend.entidades.Publicacion;
import com.egg.backend.entidades.Usuario;
import com.egg.backend.enumeraciones.Rol;
import com.egg.backend.excepciones.MiException;
import com.egg.backend.repositorios.PublicacionRepositorio;
import com.egg.backend.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio implements UserDetailsService {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;
    @Autowired
    private PublicacionRepositorio publicacionRepositorio;

    @Transactional
    public void registrar(MultipartFile archivo, String nombreCompleto, String nombreUsuario, String email,
            String password, String password2, Rol rol) throws MiException {

        validar(nombreCompleto, nombreUsuario, email, password, password2);

        Usuario usuario = new Usuario();

        usuario.setNombreCompleto(nombreCompleto);
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setFechaAlta(new Date());
        usuario.setDarBaja(false);
        usuario.setRol(rol);
        Imagen imagen = imagenServicio.guardar(archivo);

        usuario.setImagen(imagen);

        usuarioRepositorio.save(usuario);
    }

    public Usuario getOne(String id) {
        return usuarioRepositorio.getOne(id);
    }

    public Usuario getOneEmail(String email) {
        return usuarioRepositorio.buscarPorEmail(email);
    }

    @Transactional
    public void actualizar(MultipartFile archivo, String id, String nombreCompleto, String nombreUsuario, String email,
            String password, String password2, Rol rol) throws MiException {

        if (archivo != null) {
            validar(nombreCompleto, nombreUsuario, email, password, password2);

            Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
            if (respuesta.isPresent()) {

                Usuario usuario = respuesta.get();
                usuario.setNombreCompleto(nombreCompleto);
                usuario.setNombreUsuario(nombreUsuario);
                usuario.setEmail(email);

                usuario.setPassword(new BCryptPasswordEncoder().encode(password));
                usuario.setRol(rol);
                String idImagen = null;

                if (usuario.getImagen() != null) {
                    idImagen = usuario.getImagen().getId();
                }

                Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
                usuario.setImagen(imagen);

                usuarioRepositorio.save(usuario);
            }
        } else {

            validar(nombreCompleto, nombreUsuario, email, password, password2);
            Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
            if (respuesta.isPresent()) {
                Usuario usuario = respuesta.get();
                usuario.setNombreCompleto(nombreCompleto);
                usuario.setNombreUsuario(nombreUsuario);
                usuario.setEmail(email);

                usuario.setPassword(new BCryptPasswordEncoder().encode(password));
                usuario.setRol(rol);

                usuarioRepositorio.save(usuario);
            }
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

        Usuario usuario = getOne(id);

        usuarioRepositorio.delete(usuario);

    }

    private void validar(String nombreCompleto, String nombreUsuario, String email, String password, String password2)
            throws MiException {

        if (nombreCompleto.isEmpty()) {
            throw new MiException("El nombre no puede estar vacio");
        }
        if (nombreUsuario.isEmpty()) {
            throw new MiException("El nombre de usuario no puede estar vacio");
        }
        if (password.isEmpty() || password.length() < 5) {
            throw new MiException("La contraseña no puede ser menor a 5 caracteres");
        }
        if (!password.equals(password2)) {
            throw new MiException(" Las contraseñas no coinciden");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("El email no puede estar vacio");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario us = usuarioRepositorio.buscarPorEmail(email);

        if (us != null) {
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + us.getRol().toString());
            permisos.add(p);
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", us);
            return new User(us.getEmail(), us.getPassword(), permisos);
        } else {
            return null;
        }

    }

    @Transactional
    public void cambiarEstado(String id) {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();

            if (!usuario.getDarBaja()) {

                usuario.setDarBaja(Boolean.TRUE);

            } else {
                usuario.setDarBaja(Boolean.FALSE);
            }
        }
    }

    public List<Publicacion> getPublicacionesPorUsuario(Usuario usuario) {
        return publicacionRepositorio.findByUsuario(usuario);
    }

}
