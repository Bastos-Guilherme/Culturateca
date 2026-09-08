import { useState } from 'react'
import { Link, useNavigate, Outlet } from 'react-router-dom'
import '../styles/Navbar.css'

function Navbar() {
  const [sidebarOpen, setSidebarOpen] = useState(false)
  const [menuOpen, setMenuOpen] = useState(false)
  const navigate = useNavigate()

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
              <button onClick={() => navigate('/login')}>
                <i className="bi bi-box-arrow-in-right"></i>
                Login
              </button>
              <button onClick={() => navigate('/curator')}>
                <i className="bi bi-box-arrow-in-right"></i>
                perfil
              </button>
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

          <Link to="/property">
            <i className="bi bi-building"></i>
            <span>Property</span>
          </Link>
        </nav>
      </aside>

      <main className="content">
        <Outlet />
      </main>

    </div>
  )
}

export default Navbar