import React from "react";
import { ReactDOM } from "react";
import Slider from "react-slick";
import Navbar from "./Navbar";
import "./Home.css";


const Home = () => {
  return (
    <div class="contenedor">
      <Navbar />
      <div class="primera_parte">
        <h1>Bienvenidos a la App de moda</h1>
      </div>


      <div class="seccion_mas_populares">
        <h3> Lo mas destacado de la app </h3>
        <Slider />
      </div>


      <div class="publicaciones_diseñadores">
        <h3>Otras publicaciones que podrian interesarte ..</h3>
      </div>
    </div>
  );
};


export default Home;


