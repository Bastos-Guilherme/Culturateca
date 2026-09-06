import { BrowserRouter, Routes, Route } from 'react-router-dom'

import './App.css'

import Navbar from './components/Navbar'

import Home from './pages/Home'
import Property from './pages/Property'
import CreateCurator from './pages/CreateCurator'
import Login from './pages/Login'

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<Navbar />}>
          <Route path="/" element={<Home />} />
          <Route path="/property" element={<Property />} />
          <Route path="/createCurator" element={<CreateCurator />} />
          <Route path="/login" element={<Login />} />
        </Route>
      </Routes>
    </BrowserRouter>
  )
}

export default App
