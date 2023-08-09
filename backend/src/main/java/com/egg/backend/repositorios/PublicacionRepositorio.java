package com.egg.backend.repositorios;

import com.egg.backend.entidades.Categoria;
import com.egg.backend.entidades.Publicacion;
import com.egg.backend.entidades.Usuario;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicacionRepositorio extends JpaRepository<Publicacion, String> {

    @Query("SELECT p FROM Publicacion p WHERE p.fechaPublicacion = :fechaPublicacion")
    public List<Publicacion> buscarPorFecha(@Param("fechaPublicacion") Date fechaPublicacion);

    public List<Publicacion> findByUsuario(Usuario usuario);

    @Query("Select p from Publicacion p where p.categoria.nombre = :nombre")
    public List<Publicacion> buscarPorCategoria(@Param("nombre") String nombre);
    
    @Query("Select p from Publicacion p order by p.fechaPublicacion desc")
    public List<Publicacion> FechaDesc();
    
    @Query("Select p from Publicacion p order by p.fechaPublicacion asc")
    public List<Publicacion> FechaAsc();

   @Query("SELECT p FROM Publicacion p LEFT JOIN Like l GROUP BY p.id, p.contenido, p.fechaPublicacion ORDER BY COUNT(l.id) DESC")
    public List<Publicacion> buscarPublicacionesConMasLikes();
    
    @Query("Select p from Publicacion p where p.usuario = :usuario")
    public List<Publicacion> buscarPorAutor(@Param("usuario") Usuario usuario);
    
    @Query("SELECT p FROM Publicacion p LEFT JOIN Usuario u ON p.usuario = u ORDER BY u.nombreUsuario")
    public List<Publicacion> ordenarAlfabeticamente();     
    
    @Query("SELECT p FROM publicacion p "
            + "LEFT JOIN (SELECT l.publicacion.id, COUNT(l.id) AS cantidad_likes FROM Like l "
            +               "WHERE fechaLike >= DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) "
            +               "GROUP BY l.publicacion.id) "
            + "l ON p.id = l.publicacion.id "
            + "LEFT JOIN (SELECT c.publicacion.id, COUNT(id) AS cantidad_comentarios FROM comentario c "
            +               "WHERE c.fechaComentario >= DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) "
            +               "GROUP BY c.publicacion.id) "
            + "c ON p.id = c.publicacion.id "
            + "ORDER BY IFNULL(cantidad_likes, 0) + IFNULL(cantidad_comentarios, 0) DESC;")
    public List<Publicacion> ordenarPorInteracciones();
    
}
