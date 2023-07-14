/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.backend.repositorios;


import com.egg.backend.entidades.Like;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Anny
 */
public interface LikeRepositorio extends JpaRepository<Like, String>{
    
}
