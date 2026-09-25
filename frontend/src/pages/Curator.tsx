import { useEffect, useState } from 'react'

import { useNavigate } from 'react-router-dom'

import { getCurrentCurator, getAllCurator, type Curator } from '../services/curatorService'

import { getCollectionsByCurator, type Collection } from '../services/collectionService'

import '../styles/Curator.css'

function Curator() {
    const [curator, setCurator] = useState<Curator | null>(null)
    const [collections, setCollections] = useState<Collection[]>([])
    const [followersCount, setFollowersCount] = useState(0)
    const [loading, setLoading] = useState(true)

    const navigate = useNavigate()

    useEffect(() => {
        async function loadProfile() {
            try {
                const currentCurator = await getCurrentCurator()

                setCurator(currentCurator)

                const [curatorCollections, allCurators] = await Promise.all([
                    getCollectionsByCurator(currentCurator.email),
                    getAllCurator()
                ])

                setCollections(curatorCollections)

                console.log(collections)

                setFollowersCount(
                    allCurators.filter(
                        otherCurator =>
                            otherCurator.following?.includes(currentCurator.email)
                    ).length
                )

            } catch (error) {
                console.error(error)
            } finally {
                setLoading(false)
            }
        }

        loadProfile()
    }, [])

    if (loading) {
        return (
            <div className="curator-page">
                <p className="curator-loading">
                    Carregando perfil...
                </p>
            </div>
        )
    }

    if (!curator) {
        return (
            <div className="curator-page">
                <p className="curator-loading">
                    Não foi possível carregar o perfil.
                </p>
            </div>
        )
    }

    const publicCollections = collections.filter(
        collection => collection.isPublic
    )

    const uniqueCopyIds = new Set(
        collections.flatMap(
            collection =>
                collection.copies.map(copy => copy.id)
        )
    )

    const followingCount = curator.following?.length ?? 0

    return (
        <div className="curator-page">
            <section className="curator-header">
                <div className="curator-avatar-container">
                    {curator.profilePicture ? (
                        <img
                            src={curator.profilePicture}
                            alt={`Foto de ${curator.name}`}
                            className="curator-avatar"
                        />
                    ) : (
                        <div className="curator-avatar curator-avatar-placeholder">
                            <i className="bi bi-person"></i>
                        </div>
                    )}
                </div>

                <div className="curator-info">
                    <div className="curator-name-row">
                        <div>
                            <h1>{curator.name}</h1>
                            <p className="curator-username">
                                @{curator.email.split('@')[0]}
                            </p>
                        </div>
                        <button className="curator-edit-button">
                            <i className="bi bi-pencil"></i>
                            Editar perfil
                        </button>
                    </div>

                    {curator.location && (
                        <p className="curator-location">
                            <i className="bi bi-geo-alt"></i>
                            {curator.location.name}
                        </p>
                    )}

                    {curator.bio && (
                        <p className="curator-bio">
                            {curator.bio}
                        </p>
                    )}

                    <div className="curator-tags">
                        <span>
                            Colecionador
                        </span>
                        {curator.isPublic && (
                            <span>
                                Perfil público
                            </span>
                        )}
                    </div>
                </div>
            </section>

            <section className="curator-stats">
                <div className="curator-stat">
                    <strong>
                        {String(publicCollections.length).padStart(2, '0')}
                    </strong>

                    <span>
                        Pastas públicas
                    </span>
                </div>

                <div className="curator-stat">
                    <strong>
                        {uniqueCopyIds.size}
                    </strong>

                    <span>
                        Itens tombados
                    </span>
                </div>

                <div className="curator-stat">
                    <strong>
                        {followersCount}
                    </strong>

                    <span>
                        Seguidores
                    </span>
                </div>

                <div className="curator-stat">
                    <strong>
                        {followingCount}
                    </strong>

                    <span>
                        Seguindo
                    </span>
                </div>
            </section>

            <section className="curator-collections">
                <div className="collections-header">
                    <div>
                        <span className="section-label">
                            ACERVO SALVAGUARDADO
                        </span>

                        <h2>
                            Acervos & Coleções
                        </h2>
                    </div>

                    <div className="collection-filters">
                        <button className="active">
                            Todos os Acervos
                        </button>

                        <button>

                            Mais Recentes
                        </button>

                        <button>
                            Mais Populares
                        </button>
                    </div>
                </div>

                {collections.length === 0 ? (
                    <div className="empty-collections">
                        <i className="bi bi-archive"></i>
                        <p>
                            Este curador ainda não possui coleções.
                        </p>
                    </div>

                ) : (
                    <div className="collections-list">
                        {collections.map(collection => (
                            <div
                                className="collection-card"
                                key={collection.id}
                            >
                                <div className="collection-card-header">
                                    <div>
                                        <div className="collection-title">
                                            <h3>
                                                {collection.name}
                                            </h3>

                                            <span>
                                                {collection.isPublic
                                                    ? 'PÚBLICO'
                                                    : 'PRIVADO'}
                                            </span>
                                        </div>

                                        <p>
                                            {collection.copies.length === 0
                                                ? 'Esta coleção ainda não possui itens.'
                                                : `${collection.copies.length} item${collection.copies.length !== 1 ? 's' : ''} nesta coleção.`}
                                        </p>
                                    </div>

                                    <button className="collection-button"
                                        onClick={() => navigate(`/collection/${collection.id}`)}
                                    >
                                        Ver coleção
                                        <i className="bi bi-arrow-right"></i>
                                    </button>
                                </div>

                                {collection.copies.length > 0 && (
                                    <div className="collection-items">
                                        {collection.copies
                                            .slice(0, 4)
                                            .map(copy => (
                                                <div
                                                    className="collection-item"
                                                    key={copy.id}
                                                >
                                                    <div className="item-image">
                                                        <i className="bi bi-box"></i>
                                                    </div>

                                                    <h4>
                                                        Item # {copy.id}
                                                    </h4>

                                                    <p>
                                                        Cópia do acervo
                                                    </p>

                                                    <small>
                                                        Cód. COP-{String(copy.id).padStart(3, '0')}
                                                    </small>
                                                </div>
                                            ))}
                                    </div>
                                )}
                            </div>
                        ))}
                    </div>
                )}
            </section>
        </div>
    )
}

export default Curator