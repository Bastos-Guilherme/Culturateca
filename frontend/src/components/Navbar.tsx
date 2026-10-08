import { useState } from 'react' 
import { NavLink, useNavigate, Outlet } from 'react-router-dom' 
import { useAuth } from '../context/AuthContext' 
import '../styles/Navbar.css'
import LogoutModal from './LogoutModal'

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
        <h1 className="logo" onClick={() => navigate('/')}>
          <i className="bi bi-archive"></i>
          Culturateca
        </h1>

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
                <button onClick={() => {navigate('/login'), setMenuOpen(!menuOpen)}}>
                  <i className="bi bi-box-arrow-in-right"></i>
                  Login
                </button>
              )}

              {isAuthenticated && (
                <button onClick={() => {navigate('/settings'), setMenuOpen(!menuOpen)}}>
                  <i className="bi bi-gear"></i>
                  Configurações
                </button>
              )}

              {isAuthenticated && (
                <button onClick={() => {setLogoutModalOpen(true), setMenuOpen(!menuOpen)}}>
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
          <NavLink to="/" className={({ isActive }) => (isActive ? 'active' : '')}>
            <i className="bi bi-house"></i>
            <span>Home</span>
          </NavLink>

          <NavLink to="/curator" className={({ isActive }) => (isActive ? 'active' : '')}>
            <i className="bi bi-person"></i>
            <span>Perfil</span>
          </NavLink>
        </nav>
      </aside>

      <main className="content">
        <Outlet />
      </main>

      <LogoutModal
        isOpen={logoutModalOpen}
        onClose={() => setLogoutModalOpen(false)}
        onLogout={handleLogout}
      />
    </div>
  )
}

export default Navbar