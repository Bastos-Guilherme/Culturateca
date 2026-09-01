import '../styles/Home.css'
import { Link } from 'react-router-dom'

function Home() {
  return (
    <>
      <h1>Home</h1>

      <h2>Páginas</h2>

      <Link to="/property">Property</Link>
    </>
  )
}

export default Home