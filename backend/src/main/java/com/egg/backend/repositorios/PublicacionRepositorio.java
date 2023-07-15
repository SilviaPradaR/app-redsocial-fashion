/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.backend.repositorios;

import com.egg.backend.entidades.Publicacion;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Fer
 */
@Repository
public interface PublicacionRepositorio extends JpaRepository<Publicacion, String> {

    @Query("SELECT p FROM Publicacion p WHERE p.fechaPublicacion = :fechaPublicacion")
    public List<Publicacion> buscarPorFecha(@Param("fechaPublicacion")Date fechaPublicacion);
}
