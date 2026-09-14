import { useState } from 'react' 
import { Link, useNavigate, Outlet } from 'react-router-dom' 
import { useAuth } from '../context/AuthContext' 
import '../styles/Navbar.css'

function Navbar() {
  const [sidebarOpen, setSidebarOpen] = useState(false)
  const [menuOpen, setMenuOpen] = useState(false)
  const [logoutModalOpen, setLogoutModalOpen] = useState(false)

  const navigate = useNavigate()
  const { logout, isAuthenticated } = useAuth()

  function handleLogout() { 
    logout() 
    setLogoutModalOpen(false) 
    setMenuOpen(false) 
    navigate('/login') 
  }

  return (
    <div className={`layout ${sidebarOpen ? 'sidebar-open' : ''}`}>

      <header className="topbar">
        <h1 className="logo">Culturateca</h1>

        <div className="user-menu">
          <button
            className="user-button"
            onClick={() => setMenuOpen(!menuOpen)}
          >
            <i className="bi bi-person-circle"></i>
          </button>

          {menuOpen && (
            <div className="dropdown-menu">
              {!isAuthenticated && (
                <button onClick={() => navigate('/login')}>
                  <i className="bi bi-box-arrow-in-right"></i>
                  Login
                </button>
              )}

              {isAuthenticated && (
                <button onClick={() => navigate('/curator')}>
                  <i className="bi bi-box-arrow-in-right"></i>
                  Configurações
                </button>
              )}

              {isAuthenticated && (
                <button onClick={() => setLogoutModalOpen(true)}>
                  <i className="bi bi-box-arrow-in-right"></i>
                  Logout
                </button>
              )}

            </div>
          )}
        </div>
      </header>

      <aside className={`sidebar ${sidebarOpen ? 'open' : ''}`}>
        <button
          className="menu-button"
          onClick={() => setSidebarOpen(!sidebarOpen)}
        >
          <i className="bi bi-list"></i>
        </button>

        <nav>
          <Link to="/">
            <i className="bi bi-house"></i>
            <span>Home</span>
          </Link>

          <Link to="/curator">
            <i className="bi bi-person"></i>
            <span>Perfil</span>
          </Link>

          <Link to="/property">
            <i className="bi bi-box-fill"></i>
            <span>Property</span>
          </Link>
        </nav>
      </aside>

      <main className="content">
        <Outlet />
      </main>

      {logoutModalOpen && (
        <div className="logout-overlay">
          <div className="logout-modal">
            <h3>Confirmar Logout</h3>
            <p>Tem certeza que deseja sair?</p>
            <div className="logout-buttons">
              <button className="btn-cancel" onClick={() => setLogoutModalOpen(false)}>
              Cancelar
              </button>
              <button className="btn-confirm" onClick={handleLogout}>
              Sair
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}

export default Navbar