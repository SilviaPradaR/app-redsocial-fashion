package com.egg.backend.entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class Publicacion {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String contenido;
    @Temporal(TemporalType.DATE)
    private Date fechaPublicacion;
    private Integer comentario;
    private Boolean darBaja;
    @OneToOne
    private Imagen imagen;
    @OneToMany
    private Like like;
    @ManyToOne
    private Usuario usuario;    
//    @OneToMany
//    private Reporte reporte;
    @ManyToOne
    private Categoria categoria;
//    @OneToMany
//    private Comentario comentario;
}
