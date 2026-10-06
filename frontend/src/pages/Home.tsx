import NewItem from '../components/NewItem.tsx'
import '../styles/Home.css'

function Home() {
  return (
    <>
      <div className='container mt-5'>
        <div className="row">
          <div className="col-12 col-md-6 p-4 home-content">

            <h2 className='home-text'>Olá @user!</h2>
            <h3 className='home-text'>Bem-vindo à Culturateca!</h3>

            <p>
              Selecione uma opção para começarmos.
            </p>

            <NewItem
              icon="bi-plus"
              title="Inserir nova property."
              description="cadastro de porperty"
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
