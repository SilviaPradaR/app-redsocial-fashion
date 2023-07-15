
package com.egg.backend.repositorios;


import com.egg.backend.entidades.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepositorio extends JpaRepository<Like, String>{
    
}
