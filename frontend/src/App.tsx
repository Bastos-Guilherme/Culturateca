import { BrowserRouter, Routes, Route } from 'react-router-dom'

import './App.css'

import Navbar from './components/Navbar'

import CreateCurator from './pages/CreateCurator'
import Login from './pages/Login'
import CuratorPage from './pages/Curator'
import CollectionPage from './pages/Collection'
import Settings from './pages/Settings'
import CreateItem from './pages/CreateItem'
import InitialPage from './pages/InitialPage'
import Categories from './pages/Categories'

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<Navbar />}>
          <Route path="/" element={<InitialPage />} />
          <Route path="/createCurator" element={<CreateCurator />} />
          <Route path="/curator" element={<CuratorPage />} />
          <Route path="/login" element={<Login />} />
          <Route path="/collection/:id" element={<CollectionPage />} />
          <Route path="/settings" element={<Settings />} />
          <Route path="/createItem" element={<CreateItem />} />
          <Route path="/categories" element={<Categories />} />
          <Route path="*" element={<h1>404 - Página não encontrada</h1>} />
        </Route>
      </Routes>
    </BrowserRouter>
  )
}

export default App
