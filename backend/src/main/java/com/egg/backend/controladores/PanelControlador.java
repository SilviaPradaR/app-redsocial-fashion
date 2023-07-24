
package com.egg.backend.controladores;

import com.egg.backend.entidades.Usuario;
import com.egg.backend.enumeraciones.Rol;
import com.egg.backend.excepciones.MiException;
import com.egg.backend.servicios.UsuarioServicio;
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

@Controller
@RequestMapping("/")
public class PanelControlador {
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @GetMapping("/")
    public String index(){
        
        return "index.html";        
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/dashboard")
    public String panelAdministrador() {

        return "dashboard.html";//Tengo la duda si dejarlo en inicio o como dashboard.html
    }
    
    @GetMapping("/registrar")
    public String registrar(){
        
        return "signup.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombreCompleto, @RequestParam String nombreUsuario,
            @RequestParam String email, @RequestParam String password, @RequestParam String password2, @RequestParam Rol rol, ModelMap modelo, MultipartFile archivo) {
      
        try {
            usuarioServicio.registrar(archivo, nombreCompleto, nombreUsuario, email, password, password2, rol);
            
            modelo.put("exito", "Usuario registrado correctamente!!!");
            
            return "index.html";
        } catch (MiException ex) {
            
            modelo.put("error", ex.getMessage());
            
            return "signup.html";
        }
    } 
    
    @GetMapping("/login")
    public String login(@RequestParam(required=false) String error, ModelMap modelo){
        
        if (error !=null) {
            
            modelo.put("error", "Usuario o contraseña invalido");            
        }
        
        return "login.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_DISENIADOR', 'ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session){
        
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        
        if (logueado.getRol().toString().equals("ADMIN")) {
            
            return "redirect:/dashboard";
            
        }
        
        return "home.html";
    }
            
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_DISENIADOR', 'ROLE_ADMIN')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo,HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
         modelo.put("usuario", usuario);
        return "actualizar_usuario.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_DISENIADOR', 'ROLE_ADMIN')")
    @PostMapping("/perfil/{id}")
    public String actualizar(@PathVariable String id, @RequestParam String nombreCompleto, @RequestParam String nombreUsuario,
            @RequestParam String email, @RequestParam String password, @RequestParam String password2,  Rol rol, ModelMap modelo, MultipartFile archivo) throws Exception {

        try {
            usuarioServicio.actualizar(archivo, id, nombreCompleto, nombreUsuario, email, password, password2, rol);

            modelo.put("éxito", "Usuario actualizado correctamente!");

            return "inicio.html";//preguntar si el html de pagina inicio logueado se llamará asi
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombreUsuario);
            modelo.put("email", email);

            return "actualizar_usuario.html";
        }
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listausuarios")
    public String listarUsuarios(ModelMap modelo) {

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
    
}
 