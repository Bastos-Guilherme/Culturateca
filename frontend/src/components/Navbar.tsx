import { useEffect, useState } from 'react'
import { NavLink, useNavigate, Outlet } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import '../styles/Navbar.css'
import LogoutModal from './LogoutModal'
import { getCollectionsByCurator, type Collection } from '../services/collectionService'
import { getCurrentCurator } from '../services/curatorService'
import AddCollectionModal from './AddCollectionModal'

function Navbar() {
  const [sidebarOpen, setSidebarOpen] = useState(false)
  const [menuOpen, setMenuOpen] = useState(false)
  const [logoutModalOpen, setLogoutModalOpen] = useState(false)
  const [collections, setCollections] = useState<Collection[]>([])
  const [showAddCollectionModal, setShowAddCollectionModal] = useState(false)
  const [curatorEmail, setCuratorEmail] = useState('')

  const navigate = useNavigate()
  const { logout, isAuthenticated } = useAuth()

  useEffect(() => {
    async function loadData() {
      if (!isAuthenticated) return

      try {
        const curator = await getCurrentCurator()
        setCuratorEmail(curator.email)

        const collections = await getCollectionsByCurator(curator.email)
        setCollections(collections)
      } catch (error) {
        console.error('Erro ao carregar coleções:', error)
      }
    }

    loadData()
  }, [isAuthenticated])

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
                <button onClick={() => { navigate('/login'), setMenuOpen(!menuOpen) }}>
                  <i className="bi bi-box-arrow-in-right"></i>
                  Login
                </button>
              )}

              {isAuthenticated && (
                <button onClick={() => { navigate('/settings'), setMenuOpen(!menuOpen) }}>
                  <i className="bi bi-gear"></i>
                  Configurações
                </button>
              )}

              {isAuthenticated && (
                <button onClick={() => { setLogoutModalOpen(true), setMenuOpen(!menuOpen) }}>
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
          {isAuthenticated && (
            <>
              <NavLink to="/curator" className={({ isActive }) => (isActive ? 'active' : '')}>
                <i className="bi bi-person-rolodex"></i>
                <span>Home</span>
              </NavLink>

              <NavLink to="/categories" className={({ isActive }) => (isActive ? 'active' : '')}>
                <i className="bi bi-back"></i>
                <span>Categorias</span>
              </NavLink>

              <button
                className="sidebar-action"
                onClick={() => setShowAddCollectionModal(true)}
              >
                <i className="bi bi-collection"></i>
                <span>Adicionar coleção</span>
              </button>

              <NavLink to="/createItem" className={({ isActive }) => (isActive ? 'active' : '')}>
                <i className="bi bi-plus-circle"></i>
                <span>Criar Item</span>
              </NavLink>

              <NavLink to="/settings" className={({ isActive }) => (isActive ? 'active' : '')}>
                <i className="bi bi-gear"></i>
                <span>Configurações</span>
              </NavLink>
            </>
          )}

          <a
            href="https://docs.google.com/forms/d/e/1FAIpQLSdEkQKvTuimKQ0d8LwrfTjZHwa7kVXCuP_CseR8k8vWkWP11w/viewform?usp=publish-editor"
            target="_blank"
            rel="noopener noreferrer"
            className="feedback-link"
            title="Enviar feedback"
          >
            <i className="bi bi-chat-square-text"></i>
            <span>Feedback</span>
          </a>
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
      {showAddCollectionModal && (
        <AddCollectionModal
          collections={collections}
          curatorEmail={curatorEmail}
          onClose={() => setShowAddCollectionModal(false)}
          onCreated={(collection) => {
            setCollections(prev => [...prev, collection])
            setShowAddCollectionModal(false)
          }}
        />
      )}
    </div>
  )
}

export default Navbar