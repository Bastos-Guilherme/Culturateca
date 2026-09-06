
import { useState } from 'react'
import { Link, useNavigate, Outlet } from 'react-router-dom'
import '../styles/Navbar.css'

function Navbar() {
  const [sidebarOpen, setSidebarOpen] = useState(false)
  const navigate = useNavigate()

  return (
    <div className={`layout ${sidebarOpen ? 'sidebar-open' : ''}`}>
      <header className="topbar">
        <h1>Culturateca</h1>
        <button className="user-button" onClick={() => navigate('/login')}>
          <i className="bi bi-person-circle"></i>
        </button>
      </header>

      <aside className={`sidebar ${sidebarOpen ? 'open' : ''}`}>
        <button className="menu-button" onClick={() => setSidebarOpen(!sidebarOpen)}>
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
            <Link to="/createCurator">
                <i className="bi bi-person-plus"></i>
                <span>Create Curator</span>
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

