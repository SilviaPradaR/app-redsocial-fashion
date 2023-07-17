import React from 'react'
import logo from '../../img/logo.png';
const Navbar = () => {
  return (
    <div>
      <nav class="navbar navbar-expand-lg" id="nav-scrolled" aria-label="Eleventh navbar example">
        <div class="container-fluid">
          <a class="navbar-brand d-flex align-items-center" href="#">
            <img src={logo} alt="Logo" width="45" height="40" class="d-inline-block" />
            <span>Fashion.Net</span>
          </a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarsExample09" aria-controls="navbarsExample09" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>

          <div class="collapse navbar-collapse" id="navbarsExample09" >
            <ul class="navbar-nav mx-auto mb-2 mb-lg-0">
              <li class="nav-item me-3">
                <a class="nav-link active" aria-current="page" href="#">Home</a>
              </li>
              <li class="nav-item me-3">
                <a class="nav-link" href="#explorar">Explorar</a>
              </li>
              <li class="nav-item me-3">
                <a class="nav-link" href="#about">About us</a>
              </li>
              <li class="nav-item me-3">
                <a class="nav-link" href="#faq">FAQ</a>
              </li>
            </ul>

            <ul class="nav justify-content-end nav-izq">

              <li class="nav-item">
                <a class="nav-link text-center-vert nav-btns" href="/login">LogIn</a>
              </li>

              <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle nav-btns" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  Regístrate!
                </a>
                <ul class="dropdown-menu dropdown-menu-end">
                  <li><a class="dropdown-item" href="#">Usuario</a></li>
                  <li><a class="dropdown-item" href="#">Diseñador</a></li>

                </ul>
              </li>

            </ul>

            {/* <div class="dropdown text-end">
              <a href="#" class="d-block link-body-emphasis text-decoration-none dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                <img src="https://github.com/mdo.png" alt="mdo" width="32" height="32" class="rounded-circle" />
              </a>
              <ul class="dropdown-menu dropdown-menu-end text-small">
                <li><a class="dropdown-item" href="#">New project...</a></li>
                <li><a class="dropdown-item" href="#">Settings</a></li>
                <li><a class="dropdown-item" href="#">Profile</a></li>
                <li><hr class="dropdown-divider" /></li>
                <li><a class="dropdown-item" href="#">Sign out</a></li>
              </ul>
            </div> */}
          </div>
        </div>
      </nav>
    </div>
  )
}

export default Navbar
