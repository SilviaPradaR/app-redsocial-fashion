import React from "react";
import { ReactDOM } from "react";
import Navbar from "./Navbar";
import "./Usuario.css";
// import Carrusel from "./Carrusel";
const Usuario = () => {
  return (
    <div class="contenedor">
      <Navbar />
      <div class="Descripcion_perfil">
        <div class="foto">
          <img ></img>
          <div class="informacion_perfil">
            <h3>Nombre usuario</h3>


            <div class="contenedor_etiqueta">
              <h3>Diseñador/Usuario</h3>
              </div>
            <p>Descripcion del perfil</p>
          </div>
        </div>


        <div class="seccion_destacados_usuario">
          <h4> Mira las Publicaciones mas destacadas de mi perfil </h4>
          {/* el nombre deberia ser una variable que se adapte al usuario  y agregar una separacion  */}
          <div class="publicaciones_detacadas"></div>



          <div class="contenedor_publicaciones_usuario">
            <div class="publicaciones_usuario"></div>
          </div>
        </div>
      </div>
    </div>
  );
};


export default Usuario;
