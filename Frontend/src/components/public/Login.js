import React from 'react';
import logo from '../../img/logo.png';

const Login = () => {
    return (
        <div>
            <section class="vista-login">
                <div class="card">
                    <div class="row g-0">
                        <div class="col-md-6 img-container">
                            <img id="foto-login" src="/img/login_imgC.jpg" class="img-fluid rounded-start" alt="..." />
                            <div class="text-container">
                                <h2 id="roomly-login"></h2>
                                <h4></h4>
                            </div>
                        </div>

                        <div class="col-md-6 login-container">
                            <div class="card-body">
                                <div class="form-header text-center">
                                    <img src={logo} alt="logo" style={{ width: "80px" }} />
                                    <h4>Bienvenido de vuelta</h4>
                                </div>
                                <form>
                                    <div class="mb-3">
                                        <label for="correo" class="form-label">Correo</label>
                                        <input type="email" class="form-control" id="correo" aria-describedby="emailHelp" required />

                                    </div>
                                    <div class="mb-3">
                                        <label for="clave" class="form-label">Contraseña</label>
                                        <input type="password" class="form-control" id="clave" required />
                                    </div>
                                    <button type="submit" class="btn btn-login d-block" style={{ width: "100%" }}>Login</button>
                                </form>
                                <div class="links-footer text-center">
                                    <a href="#">¿Olvidaste tu contraseña?</a>
                                    <hr />
                                    <p>No tienes cuenta aún?<a href="/registro"> Regístrate!</a></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    )
}

export default Login

