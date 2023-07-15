
package com.egg.backend.entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
public class Like {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idLike;   
    @Temporal(TemporalType.DATE)
    private Date fechaLike;
    @OneToOne
    private Usuario usuario;
//    @ManyToOne
//    private Publicacion publicacion;
}
