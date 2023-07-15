
package com.egg.backend.entidades;

import com.egg.backend.enumeraciones.Rol;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class Usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombreCompleto;
    private String nombreUsuario;
    private String email;
    private String password;
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;
    private Boolean darBaja;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    @OneToOne
    private Imagen imagen;
//    @OneToMany
//    private Publicacion publicacion;
//    @OneToMany
//    private Like like;
//    @OneToMany
//    private Comentario comentario;
//    @OneToMany
//    private Reporte reporte;

}
