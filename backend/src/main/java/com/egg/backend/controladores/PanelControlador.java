package com.egg.backend.controladores;

import com.egg.backend.entidades.Categoria;
import com.egg.backend.entidades.Like;
import com.egg.backend.entidades.Publicacion;
import com.egg.backend.entidades.Usuario;
import com.egg.backend.enumeraciones.Rol;
import com.egg.backend.excepciones.MiException;
import com.egg.backend.servicios.PublicacionServicio;
import com.egg.backend.servicios.UsuarioServicio;
import com.egg.backend.servicios.ComentarioServicio;
import java.util.List;
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
import org.springframework.web.multipart.MultipartFile;
import com.egg.backend.enumeraciones.Rol;
import com.egg.backend.repositorios.UsuarioRepositorio;
import com.egg.backend.servicios.CategoriaServicio;
import com.egg.backend.servicios.LikeServicio;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class PanelControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private PublicacionServicio publicacionServicio;

    @Autowired
    private LikeServicio likeServicio;

    @Autowired
    private ComentarioServicio comentarioServicio;

    @Autowired
    private CategoriaServicio categoriaServicio;

    @GetMapping("/")
    public String index() {

        return "index.html";
    }

    @GetMapping("/registrar")
    public String registrar() {

        return "signup.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombreCompleto, @RequestParam String nombreUsuario,
            @RequestParam String email, @RequestParam String password, @RequestParam String password2, @RequestParam Rol rol, ModelMap modelo, MultipartFile archivo) {

        try {

            Usuario user = usuarioServicio.getOneEmail(email);

            if (user == null) {
                usuarioServicio.registrar(archivo, nombreCompleto, nombreUsuario, email, password, password2, rol);

                modelo.put("exito", "Usuario registrado correctamente!!!");
                modelo.put("nombre", nombreUsuario);
                modelo.put("email", email);
            } else {
                throw new MiException("El email ya esta registrado");
            }

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombreUsuario);
            modelo.put("email", email);
            return "signup.html";
        }
        return "index.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {

        if (error != null) {

            modelo.put("error", "Usuario y/o contraseña inválido");
        }

        return "login.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_DISENIADOR','ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(ModelMap modelo, HttpSession session, @RequestParam(required = false) String nombre) throws MiException {

        List<Categoria> categorias = categoriaServicio.listarCategoria();
        List<Publicacion> publicaciones= publicacionServicio.listarPublicaciones();
        List<Publicacion> publicacionesFiltradas;
        
        
        
        if (nombre == null) {
            
            publicacionesFiltradas= publicaciones;
        }else if(nombre == "descendente"){
            
           publicacionesFiltradas=publicacionServicio.getByFechaDesc();
                
        }else if(nombre =="ascendente"){
            
            publicacionesFiltradas=publicacionServicio.getByFechaAsc();
                
        /*}else if(nombre == "likes"){
            
                   
        }else if(nombre == "autor"){
            
                */   
        }else{
            publicacionesFiltradas=publicacionServicio.getOneCategoria(nombre);
        }
        
       
           List<List<Publicacion>> publicacionesChunked = new ArrayList<>();
            for (int i = 0; i < publicaciones.size(); i += 3) {
                publicacionesChunked.add(publicaciones.subList(i, Math.min(i + 3, publicaciones.size())));
            }

            Map<String, Integer> conteoLike = new HashMap<>();
            for (Publicacion p : publicaciones) {

                int conteo = likeServicio.contadorLike(p.getId());
                conteoLike.put(p.getId(), conteo);
            }

            Map<String, Integer> conteoComentariosPub = new HashMap<>();
            for (Publicacion p : publicaciones) {

                int conteo = comentarioServicio.contadorComentariosPublicacion(p.getId());
                conteoComentariosPub.put(p.getId(), conteo);
            }
        
        modelo.addAttribute("publicacionesChunked", publicacionesChunked);
        modelo.addAttribute("conteoLike", conteoLike);
        modelo.addAttribute("conteoComentariosPub", conteoComentariosPub);
        modelo.addAttribute("categorias", categorias);
        modelo.addAttribute("publicaciones", publicaciones);
        modelo.addAttribute("publicacionesFiltradas", publicacionesFiltradas);

        return "home.html";
        
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_DISENIADOR','ROLE_ADMIN')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) throws MiException {

        Usuario usuario = (Usuario) session.getAttribute("usuariosession");

        List<Publicacion> publicaciones = usuarioServicio.getPublicacionesPorUsuario(usuario);
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        
        modelo.addAttribute("publicaciones", publicaciones);

        Map<String, Integer> conteoLike = new HashMap<>();
        for (Publicacion p : publicaciones) {

            int conteo = likeServicio.contadorLike(p.getId());
            conteoLike.put(p.getId(), conteo);
            modelo.addAttribute("conteoLike", conteoLike);
        }

        Map<String, Integer> conteoComentariosPub = new HashMap<>();
        for (Publicacion p : publicaciones) {

            int conteo = comentarioServicio.contadorComentariosPublicacion(p.getId());
            conteoComentariosPub.put(p.getId(), conteo);
            modelo.addAttribute("conteoComentariosPub", conteoComentariosPub);
        }
        
        Map<String, Integer> conteoComentariosDiseniador = new HashMap<>();
        for (Usuario u : usuarios) {

            int conteo = comentarioServicio.contadorComentariosDiseniador(u.getId());
            conteoComentariosPub.put(u.getId(), conteo);
            modelo.addAttribute("sumatoriaComentarios", conteoComentariosDiseniador);            
        }

        return "perfil.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_DISENIADOR', 'ROLE_ADMIN')")
    @GetMapping("/editar_perfil")
    public String editarperfil(ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);
        return "usuario_modificar.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_DISENIADOR','ROLE_ADMIN')")
    @PostMapping("/perfil/{id}")
    public String actualizar(@PathVariable String id, @RequestParam String nombreCompleto, @RequestParam String nombreUsuario,
            @RequestParam String email, @RequestParam String password, @RequestParam String password2, Rol rol, ModelMap modelo, MultipartFile archivo) throws Exception {

        try {
            if (archivo.isEmpty()) {
                archivo = null;
            }
            usuarioServicio.actualizar(archivo, id, nombreCompleto, nombreUsuario, email, password, password2, rol);

            modelo.put("éxito", "Usuario actualizado correctamente!");

            return "redirect:../inicio";
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombreUsuario);
            modelo.put("email", email);

            return "usuario_modificar.html";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listausuarios")
    public String listarUsuarios(ModelMap modelo
    ) {

        List<Usuario> usuarios = usuarioServicio.listarUsuarios();

        modelo.addAttribute("usuarios", usuarios);

        return "listar_usuarios.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id) throws MiException {

        usuarioServicio.eliminar(id);

        return "redirect:../listarusuarios";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_DISENIADOR', 'ROLE_ADMIN')")
    @GetMapping("/dar_like")

    public String dar_like(ModelMap modelo, HttpSession session, String publicacionId) throws MiException {
        return "home.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_DISENIADOR', 'ROLE_ADMIN')")
    @PostMapping("/darLike/{id}")
    public String darLike(ModelMap modelo, HttpSession session, @PathVariable String id) {

        try {
            Usuario usuario = (Usuario) session.getAttribute("usuariosession");
            Publicacion publicacion = publicacionServicio.getOne(id);
            likeServicio.darLike(usuario, publicacion);
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
        }

        return "redirect:/inicio";
    }

    @GetMapping("/perfil/{id}")
    public String modificarUsuario(ModelMap modelo, @PathVariable String id) {
        modelo.put("usuario", usuarioServicio.getOne(id));

        return "usuario_modificar.html";
    }

}
