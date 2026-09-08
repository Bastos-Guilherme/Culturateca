import { useEffect, useState } from 'react'

import { getCurrentCurator, type Curator as CuratorType } from '../services/curatorService'

function Curator() {
    const [curator, setCurator] = useState<CuratorType | null>(null)
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        async function loadCurator() {
            try {
                const data = await getCurrentCurator()
                setCurator(data)
            } catch (error) {
                console.error(error)
            } finally {
                setLoading(false)
            }
        }
        loadCurator()
    }, [])

    if (loading) {
        return <p>Carregando perfil...</p>
    }

    if (!curator) {
        return <p>Não foi possível carregar o perfil.</p>
    }

    return (
        <div className="container mt-5">
            <h1>Meu perfil</h1>
            <hr />
            <div className="mt-4">
                <p>
                    <strong>Nome:</strong> {curator.name}
                </p>
                <p>
                    <strong>E-mail:</strong> {curator.email}
                </p>
                <p>
                    <strong>Telefone:</strong> {curator.phone}
                </p>
                <p>
                    <strong>Gênero:</strong> {curator.gender}
                </p>
                <p>
                    <strong>Perfil público:</strong>{' '}
                    {curator.isPublic ? 'Sim' : 'Não'}
                </p>
            </div>
        </div>
    )
}

export default Curator