
package com.egg.backend.repositorios;

import com.egg.backend.entidades.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepositorio extends JpaRepository<Comentario, String> {
    
}
