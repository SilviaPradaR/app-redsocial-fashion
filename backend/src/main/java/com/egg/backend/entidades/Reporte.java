
package com.egg.backend.entidades;

import com.egg.backend.enumeraciones.Categoria;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class Reporte {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idReporte;
    
    @OneToOne
    private Usuario usuario;
    
    @OneToOne
    private Publicacion publicacion;
    
    @OneToOne
    private Comentario comentario;
    
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    
    private String descripcion;

}
