import { useEffect, useState } from 'react'
import { getCurrentCurator, type Curator } from '../services/curatorService'

import NewItem from '../components/NewItem.tsx'
import '../styles/Home.css'

function Home() {
  const [curator, setCurator] = useState<Curator | null>(null)

  useEffect(() => {
    async function loadCurator() {
      try {
        const data = await getCurrentCurator()
        setCurator(data)
      } catch (error) {
        console.error('Erro ao buscar curador:', error)
      }
    }

    loadCurator()
  }, [])

  return (
    <>
      <div className='container mt-5'>
        <div className="row">
          <div className="col-12 col-md-6 p-4 home-content">

            <h2 className='home-text'>Olá {curator?.name ?? 'Curador'}!</h2>
            <h3 className='home-text'>Bem-vindo à Culturateca!</h3>

            <p>
              Selecione uma opção para começarmos.
            </p>

            <NewItem
              icon="bi-plus"
              title="Gerenciar propriedades"
              description="CRUD de propriedades para seus itens"
              route="/property"
            />

          </div>
          <div className="col-12 col-md-6 home-feed">

            <h3 className='home-text'>Destaques</h3>
            <p>Confira favoritos e Curadores seguidos</p>

          </div>
        </div>
      </div>
    </>
  )
}

export default Home
