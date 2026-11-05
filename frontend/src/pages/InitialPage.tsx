import { useEffect, useState } from 'react'
import { useAuth } from '../context/AuthContext'
import { getCurrentCurator } from '../services/curatorService'
import '../styles/InitialPage.css'

function InitialPage() {
    const { isAuthenticated } = useAuth()
    const [curatorName, setCuratorName] = useState('')

    useEffect(() => {
        async function loadCurator() {
            if (!isAuthenticated) {
                setCuratorName('')
                return
            }

            try {
                const curator = await getCurrentCurator()
                setCuratorName(curator.name)
            } catch (error) {
                console.error('Erro ao carregar dados do curador:', error)
            }
        }

        loadCurator()
    }, [isAuthenticated])

    return (
        <div className="initial-page">
            <div className="initial-content">
                <i className="bi bi-archive initial-icon"></i>
                {isAuthenticated ? (
                    <>
                        <h1>
                            Olá{curatorName ? `, ${curatorName}` : ''}!
                        </h1>
                        <p>
                            Que bom ter você por aqui!
                            Organize e mantenha seus itens culturais
                            em um só lugar.
                        </p>
                        <div className="initial-message">
                            <i className="bi bi-list"></i>
                            <span>
                                Utilize a barra lateral para navegar
                                pela Culturateca e realizar as ações
                                desejadas.
                            </span>
                        </div>
                    </>
                ) : (
                    <>
                        <h1>Bem-vindo à Culturateca!</h1>
                        <p>
                            Registre, organize e mantenha seus itens culturais
                            em um só lugar.
                        </p>
                        <div className="initial-message">
                            <i className="bi bi-person-circle"></i>
                            <span>
                                Para começar, faça login utilizando o botão
                                localizado no canto superior direito da tela.
                            </span>
                        </div>
                    </>
                )}
            </div>
        </div>
    )
}

export default InitialPage
