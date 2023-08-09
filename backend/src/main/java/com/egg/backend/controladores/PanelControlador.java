package com.egg.backend.controladores;

import com.egg.backend.entidades.Categoria;
import com.egg.backend.entidades.Publicacion;
import com.egg.backend.entidades.Usuario;
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
import com.egg.backend.servicios.CategoriaServicio;
import com.egg.backend.servicios.LikeServicio;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
<<<<<<< HEAD
    public String inicio(ModelMap modelo, HttpSession session, @RequestParam(required = false) String nombre,
            String orden, String idDiseniador, String idCategoria) throws MiException {

        List<Categoria> categorias = categoriaServicio.listarCategoria();
        List<Usuario> diseniadores = usuarioServicio.listarDiseniadores();
        List<Publicacion> publicaciones = publicacionServicio.listarPublicaciones();
        List<Publicacion> publicacionesFiltradas = new ArrayList();
        List<Publicacion> publicacionesOrdenadadas = publicaciones;

        //ORDENA recibe orden        
        if (null != orden) {
            switch (orden) {
                case "descendente":
                    publicacionesOrdenadadas = publicacionServicio.getByFechaDesc();
                    break;
                case "ascendente":
                    publicacionesOrdenadadas = publicacionServicio.getByFechaAsc();
                    break;
                case "likes":
                    publicacionesOrdenadadas = publicacionServicio.getByMasLikes();
                    break;
                case "alfabetico":
                    publicacionesOrdenadadas = publicacionServicio.orderByAuthor();
                    break;
                default:
                    break;
            }
        }

        //FILTRA recibe nombre: null - "autor" + idDiseniador - "categoria" + idCategoria
        if (null == nombre) {
            publicacionesFiltradas = publicacionesOrdenadadas;

        } else {
            switch (nombre) {
                case "autor":
                    Usuario diseniador = usuarioServicio.getOne(idDiseniador);
                    publicacionesOrdenadadas.stream().filter(p -> p.getUsuario() == diseniador).forEach(publicacionesFiltradas::add);
                    break;
                case "categoria":
                    Categoria categoria = categoriaServicio.getOne(idCategoria);
                    publicacionesOrdenadadas.stream().filter(p -> p.getCategoria() == categoria).forEach(publicacionesFiltradas::add);
                    break;
                default:
                    break;
=======
    public String inicio(ModelMap modelo, HttpSession session, @RequestParam(required = false) String nombre, String idDiseniador) throws MiException {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        List<Categoria> categorias = categoriaServicio.listarCategoria();
        List<Usuario> diseniadores = usuarioServicio.listarDiseniadores();
        List<Publicacion> publicaciones= publicacionServicio.listarPublicaciones();
        List<Publicacion> publicacionesFiltradas;     
        Map<String, Integer> conteoComentariosPub = new HashMap<>();
        Map<String, Integer> conteoLike = new HashMap<>();
        Map<String, Boolean> usuarioDioLikeMap = new HashMap<>();    

        if (nombre == null) {
            
            publicacionesFiltradas= publicaciones;
        }else if(nombre == "descendente"){
            
           publicacionesFiltradas=publicacionServicio.getByFechaDesc();
                
        }else if(nombre =="ascendente"){
            
            publicacionesFiltradas=publicacionServicio.getByFechaAsc();
                
        }else if(nombre == "likes"){
            publicacionesFiltradas=publicacionServicio.getByMasLikes();
        
        }else if(nombre == "autor"){
            Usuario diseniador = usuarioServicio.getOne(idDiseniador);
            publicacionesFiltradas = publicacionServicio.getByAuthor(diseniador);            
                  
        }else if(nombre == "alfabetico"){
            publicacionesFiltradas = publicacionServicio.orderByAuthor();            
                  
        }else{
            
            publicacionesFiltradas=publicacionServicio.getOneCategoria(nombre);
        }        
       
        List<List<Publicacion>> publicacionesChunked = new ArrayList<>();
            for (int i = 0; i < publicaciones.size(); i += 3) {
                publicacionesChunked.add(publicaciones.subList(i, Math.min(i + 3, publicaciones.size())));
>>>>>>> developer
            }
        }

<<<<<<< HEAD
//        if (nombre == null) {
//            
//            publicacionesFiltradas= publicaciones;
//        }else if(nombre == "descendente"){
//            
//           publicacionesFiltradas=publicacionServicio.getByFechaDesc();
//                
//        }else if(nombre =="ascendente"){
//            
//            publicacionesFiltradas=publicacionServicio.getByFechaAsc();
//                
//        }else if(nombre == "likes"){
//            publicacionesFiltradas=publicacionServicio.getByMasLikes();
//        
//        }else if(nombre == "autor"){
//            Usuario diseniador = usuarioServicio.getOne(idDiseniador);
//            publicacionesFiltradas = publicacionServicio.getByAuthor(diseniador);            
//                  
//        }else if(nombre == "alfabetico"){
//            publicacionesFiltradas = publicacionServicio.orderByAuthor();            
//                  
//        }else{
//            
//            publicacionesFiltradas=publicacionServicio.getOneCategoria(nombre);
//        }
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

=======
            for (Publicacion p : publicaciones) {
                int conteoLikes = likeServicio.contadorLike(p.getId());
                conteoLike.put(p.getId(), conteoLikes);
                int conteoComent = comentarioServicio.contadorComentariosPublicacion(p.getId());
                conteoComentariosPub.put(p.getId(), conteoComent);
                boolean usuarioDioLike = likeServicio.usuarioDioLike(usuario, p);
                usuarioDioLikeMap.put(p.getId(), usuarioDioLike);
            }
       
>>>>>>> developer
        modelo.addAttribute("publicacionesChunked", publicacionesChunked);
        modelo.addAttribute("conteoLike", conteoLike);
        modelo.addAttribute("conteoComentariosPub", conteoComentariosPub);
        modelo.addAttribute("categorias", categorias);
        modelo.addAttribute("usuarios", diseniadores);
        modelo.addAttribute("publicaciones", publicaciones);
        modelo.addAttribute("publicacionesFiltradas", publicacionesFiltradas);
        modelo.addAttribute("usuarioDioLikeMap", usuarioDioLikeMap);

        return "home.html";

    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_DISENIADOR','ROLE_ADMIN')")
<<<<<<< HEAD
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) throws MiException {

        Usuario usuario = (Usuario) session.getAttribute("usuariosession");

        List<Publicacion> publicaciones = usuarioServicio.getPublicacionesPorUsuario(usuario);
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();

=======
    @GetMapping("/perfil/{idUsuario}")
    public String perfil(ModelMap modelo, @PathVariable String idUsuario) throws MiException {       
        Usuario usuario = usuarioServicio.getOne(idUsuario);
        List<Publicacion> publicaciones = usuarioServicio.getPublicacionesPorUsuario(usuario);      
>>>>>>> developer
        modelo.addAttribute("publicaciones", publicaciones);
        
        int sumatoriaComentarios = 0;
        int sumatoriaLikes = 0;
        Map<String, Integer> conteoLike = new HashMap<>();
<<<<<<< HEAD
        for (Publicacion p : publicaciones) {

            int conteo = likeServicio.contadorLike(p.getId());
            conteoLike.put(p.getId(), conteo);
            sumatoriaLikes += conteo;
            modelo.addAttribute("conteoLike", conteoLike);
            modelo.addAttribute("sumatoriaLikes", sumatoriaLikes);
        }

        int sumatoriaComentarios = 0;
=======
>>>>>>> developer
        Map<String, Integer> conteoComentariosPub = new HashMap<>();
        Map<String, Boolean> usuarioDioLikeMap = new HashMap<>();   
    
        for (Publicacion p : publicaciones) {
<<<<<<< HEAD

            int conteo = comentarioServicio.contadorComentariosPublicacion(p.getId());
            conteoComentariosPub.put(p.getId(), conteo);
            sumatoriaComentarios += conteo;
            modelo.addAttribute("conteoComentariosPub", conteoComentariosPub);
            modelo.addAttribute("sumatoriaComentarios", sumatoriaComentarios);
        }
=======
            int conteoLikes = likeServicio.contadorLike(p.getId());
            conteoLike.put(p.getId(), conteoLikes);
            sumatoriaLikes += conteoLikes; 
             int conteo = comentarioServicio.contadorComentariosPublicacion(p.getId());
            conteoComentariosPub.put(p.getId(), conteo);            
            sumatoriaComentarios += conteo;
            boolean usuarioDioLike = likeServicio.usuarioDioLike(usuario, p);
            usuarioDioLikeMap.put(p.getId(), usuarioDioLike);                      
            
        }        
        
        modelo.addAttribute("conteoLike", conteoLike);
        modelo.addAttribute("sumatoriaLikes", sumatoriaLikes);
        modelo.addAttribute("conteoComentariosPub", conteoComentariosPub);
        modelo.addAttribute("sumatoriaComentarios", sumatoriaComentarios); 
        modelo.addAttribute("usuarioDioLikeMap", usuarioDioLikeMap);
        modelo.addAttribute("usuario", usuario);
>>>>>>> developer

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

    @GetMapping("/editar_perfil/{id}")
    public String modificarUsuario(ModelMap modelo, @PathVariable String id) {
        modelo.put("usuario", usuarioServicio.getOne(id));

        return "usuario_modificar.html";
    }

}
