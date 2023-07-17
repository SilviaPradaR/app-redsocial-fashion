import React, { Component } from 'react'
import imgBanner from '../../img/imgBanner.png';
import Navbar from './Navbar';

export class Main extends Component {
    render() {
        return (
            <main>
                <div class="card banner-container">
                    <Navbar />
                    <div class="row g-0 info-container py-5">
                        <div class="col-md-5 d-flex justify-content-center align-items-center">
                            <img src={imgBanner} alt='imgBanner' id='imgBanner'></img>
                        </div>
                        <div class="col-md-7 d-flex justify-content-center align-items-center">
                            <div class="card-body">
                                <h1 id='intro'>Conecta con la moda en FashionNet</h1>
                                <p>¿Quieres estar al tanto de las últimas tendencias de moda? Únete a nuestra comunidad en FashionNet y comparte tu estilo con otros amantes de la moda</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="about" className="aboutUs-container px-4 py-3">
                    <h2 class="pb-2 border-bottom">Sobre nosotros</h2>
                    <div class="row row-cols-1 row-cols-md-2 align-items-md-center g-5 py-3">
                        <div class="col col-md-5 d-flex flex-column align-items-start gap-2">
                            <h2 class="fw-bold text-body-emphasis">¿Quienes somos?</h2>
                            <p class="text-body-secondary" id='info-app'>
                                Somos una red social dedicada exclusivamente a la moda. Nuestra plataforma está diseñada para conectar a amantes de la moda de todo el mundo en un espacio único y vibrante. Nos apasiona la moda y nos esforzamos por ser un lugar de inspiración, creatividad y comunidad para todos los entusiastas de este apasionante mundo.<br></br>
                                <br></br>
                                Valoramos la autenticidad, la inclusión y la colaboración. Creemos en la importancia de expresar tu estilo único y encontrar tu voz en el universo de la moda</p>
                            <a href="#" class="btn btn-primary">
                                Únete ahora!
                            </a>
                        </div>
                        <div class="col col-md-7">
                            <div class="row row-cols-1 row-cols-sm-2 g-4">
                                <div class="col d-flex flex-column gap-2">
                                    <div class="feature-icon-small d-inline-flex align-items-center justify-content-center text-bg-primary bg-gradient fs-4 rounded-3">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor" class="bi" viewBox="0 0 16 16">
                                            <path d="M2 6a6 6 0 1 1 10.174 4.31c-.203.196-.359.4-.453.619l-.762 1.769A.5.5 0 0 1 10.5 13a.5.5 0 0 1 0 1 .5.5 0 0 1 0 1l-.224.447a1 1 0 0 1-.894.553H6.618a1 1 0 0 1-.894-.553L5.5 15a.5.5 0 0 1 0-1 .5.5 0 0 1 0-1 .5.5 0 0 1-.46-.302l-.761-1.77a1.964 1.964 0 0 0-.453-.618A5.984 5.984 0 0 1 2 6zm6-5a5 5 0 0 0-3.479 8.592c.263.254.514.564.676.941L5.83 12h4.342l.632-1.467c.162-.377.413-.687.676-.941A5 5 0 0 0 8 1z" />
                                        </svg>
                                    </div>
                                    <h5 class="fw-semibold mb-0 text-body-emphasis">Inspírate con las últimas tendencias</h5>
                                    <p class="text-body-secondary">FashionNet te permite descubrir las últimas tendencias de moda y obtener inspiración de otros usuarios con estilo.</p>
                                </div>

                                <div class="col d-flex flex-column gap-2">
                                    <div class="feature-icon-small d-inline-flex align-items-center justify-content-center text-bg-primary bg-gradient fs-4 rounded-3">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor" class="bi" viewBox="0 0 16 16">
                                            <path fill-rule="evenodd" d="M6 3.5A1.5 1.5 0 0 1 7.5 2h1A1.5 1.5 0 0 1 10 3.5v1A1.5 1.5 0 0 1 8.5 6v1H14a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-1 0V8h-5v.5a.5.5 0 0 1-1 0V8h-5v.5a.5.5 0 0 1-1 0v-1A.5.5 0 0 1 2 7h5.5V6A1.5 1.5 0 0 1 6 4.5v-1zM8.5 5a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1zM0 11.5A1.5 1.5 0 0 1 1.5 10h1A1.5 1.5 0 0 1 4 11.5v1A1.5 1.5 0 0 1 2.5 14h-1A1.5 1.5 0 0 1 0 12.5v-1zm1.5-.5a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5h-1zm4.5.5A1.5 1.5 0 0 1 7.5 10h1a1.5 1.5 0 0 1 1.5 1.5v1A1.5 1.5 0 0 1 8.5 14h-1A1.5 1.5 0 0 1 6 12.5v-1zm1.5-.5a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5h-1zm4.5.5a1.5 1.5 0 0 1 1.5-1.5h1a1.5 1.5 0 0 1 1.5 1.5v1a1.5 1.5 0 0 1-1.5 1.5h-1a1.5 1.5 0 0 1-1.5-1.5v-1zm1.5-.5a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5h-1z" />
                                        </svg>
                                    </div>
                                    <h5 class="fw-semibold mb-0 text-body-emphasis">Conecta con otros amantes de la moda</h5>
                                    <p class="text-body-secondary">Únete a una comunidad de personas apasionadas por la moda y comparte tus ideas, consejos y opiniones sobre estilo.</p>
                                </div>

                                <div class="col d-flex flex-column gap-2">
                                    <div class="feature-icon-small d-inline-flex align-items-center justify-content-center text-bg-primary bg-gradient fs-4 rounded-3">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor" class="bi" viewBox="0 0 16 16">
                                            <path d="M14 1a1 1 0 0 1 1 1v8a1 1 0 0 1-1 1h-2.5a2 2 0 0 0-1.6.8L8 14.333 6.1 11.8a2 2 0 0 0-1.6-.8H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h12ZM2 0a2 2 0 0 0-2 2v8a2 2 0 0 0 2 2h2.5a1 1 0 0 1 .8.4l1.9 2.533a1 1 0 0 0 1.6 0l1.9-2.533a1 1 0 0 1 .8-.4H14a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2Z" />
                                            <path d="M8 3.993c1.664-1.711 5.825 1.283 0 5.132-5.825-3.85-1.664-6.843 0-5.132Z" />
                                        </svg>
                                    </div>
                                    <h5 class="fw-semibold mb-0 text-body-emphasis">Descubre diseñadores emergentes</h5>
                                    <p class="text-body-secondary">En FashionNet podrás descubrir y apoyar a talentosos diseñadores que están marcando tendencia en el mundo de la moda.</p>
                                </div>

                                <div class="col d-flex flex-column gap-2">
                                    <div class="feature-icon-small d-inline-flex align-items-center justify-content-center text-bg-primary bg-gradient fs-4 rounded-3">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor" class="bi" viewBox="0 0 16 16">
                                            <path d="M15 12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V6a1 1 0 0 1 1-1h1.172a3 3 0 0 0 2.12-.879l.83-.828A1 1 0 0 1 6.827 3h2.344a1 1 0 0 1 .707.293l.828.828A3 3 0 0 0 12.828 5H14a1 1 0 0 1 1 1v6zM2 4a2 2 0 0 0-2 2v6a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V6a2 2 0 0 0-2-2h-1.172a2 2 0 0 1-1.414-.586l-.828-.828A2 2 0 0 0 9.172 2H6.828a2 2 0 0 0-1.414.586l-.828.828A2 2 0 0 1 3.172 4H2z" />
                                            <path d="M8 11a2.5 2.5 0 1 1 0-5 2.5 2.5 0 0 1 0 5zm0 1a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7zM3 6.5a.5.5 0 1 1-1 0 .5.5 0 0 1 1 0z" />
                                        </svg>
                                    </div>
                                    <h5 class="fw-semibold mb-0 text-body-emphasis">Comparte tu estilo único</h5>
                                    <p class="text-body-secondary">En Fashion.Net, te animamos a expresar tu estilo único y personalidad a través de tus creaciones. Comparte y se fuente de inspiración. </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="faq" className='faq-container px-4 py-3'>
                    <h2>Preguntas frecuentes</h2>
                    <div class="row row-cols-1 row-cols-md-2 g-4 py-3 px-4">
                        <div class="col">
                            <div class="card h-100">
                                <div class="card-body">
                                    <h5 class="card-title">¿Cómo creo una cuenta?</h5>
                                    <p class="card-text">¡Crear una cuenta es súper fácil! Simplemente haga clic en el botón "Registrarse" en nuestra página de inicio, complete un formulario de registro rápido y prepárese para explorar la comunidad Social Fashionista.</p>
                                </div>
                            </div>
                        </div>
                        <div class="col">
                            <div class="card h-100">
                                <div class="card-body">
                                    <h5 class="card-title">¿Qué puedo hacer una vez que tengo una cuenta?</h5>
                                    <p class="card-text">Publicar y compartir artículos de moda, dar "me gusta" y comentar en publicaciones de otros usuarios, explorar y descubrir nuevas tendencias.</p>
                                </div>
                            </div>
                        </div>
                        <div class="col">
                            <div class="card h-100">
                                <div class="card-body">
                                    <h5 class="card-title">¿Cómo puedo interactuar con otros usuarios?</h5>
                                    <p class="card-text">¡Nuestra comunidad está llena de personas influyentes que sienten tanta pasión por la moda como tú! Simplemente busque sus íconos de moda favoritos o descubra nuevos e interactúa mediante likes o comentarios.</p>
                                </div>

                            </div>
                        </div>
                        <div class="col">
                            <div class="card h-100">
                                <div class="card-body">
                                    <h5 class="card-title">¿Qué debo hacer si encuentro contenido inapropiado?</h5>
                                    <p class="card-text">Utilizar las herramientas de denuncia o contacto con el equipo de soporte para abordar estos problemas y asegurar que la plataforma se mantenga segura y respetuosa.</p>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </main>
        )
    }
}

export default Main
