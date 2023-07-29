
package com.egg.backend.repositorios;


import com.egg.backend.entidades.Like;
import com.egg.backend.entidades.Publicacion;
import com.egg.backend.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepositorio extends JpaRepository<Like, String>{
    
}
