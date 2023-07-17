
package com.egg.backend.repositorios;

import com.egg.backend.entidades.Usuario;
import com.egg.backend.enumeraciones.Rol;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    public Usuario buscarPorEmail(@Param("email")String email);

    public List<Usuario> findAllByRol(Rol rol);
}
