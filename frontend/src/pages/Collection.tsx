import { useEffect, useState, useCallback } from 'react'
import { useNavigate, useParams } from 'react-router-dom'

import { getCollectionById, type Collection } from '../services/collectionService'
import { getCopyById, type Copy } from '../services/copyService'
import { getCanonicById, type Canonic } from '../services/canonicService'
import { getLocationById, type Location } from '../services/locationService'
import { getAllProperty, type Property } from '../services/propertyService'
import { getAllCategory, type Category } from '../services/categoryService'
import EditCopyModal from '../components/EditCopyModal'

import '../styles/Collection.css'

function CollectionPage() {
    const { id } = useParams<{ id: string }>()
    const navigate = useNavigate()

    const [collection, setCollection] = useState<Collection | null>(null)
    const [copies, setCopies] = useState<Copy[]>([])

    const [canonics, setCanonics] = useState<Record<number, Canonic>>({})
    const [locations, setLocations] = useState<Record<number, Location>>({})
    const [properties, setProperties] = useState<Record<number, Property>>({})
    const [categories, setCategories] = useState<Record<number, Category>>({})
    const [editingCopy, setEditingCopy] = useState<Copy | null>(null)

    const [loading, setLoading] = useState(true)

    const loadCollection = useCallback(async () => {
        setLoading(true)

        try {
            if (!id) {
                setCollection(null)
                setCopies([])
                return
            }

            const collectionData = await getCollectionById(Number(id))
            setCollection(collectionData)

            const copyData = await Promise.all(
                collectionData.copies.map(copy =>
                    getCopyById(copy.id)
                )
            )

            setCopies(copyData)

            const [propertyData, categoryData] = await Promise.all([
                getAllProperty(),
                getAllCategory()
            ])

            const propertyMap: Record<number, Property> = {}

            propertyData.forEach((property: Property) => {
                propertyMap[property.id] = property
            })

            setProperties(propertyMap)

            const categoryMap: Record<number, Category> = {}

            categoryData.forEach(category => {
                categoryMap[category.id] = category
            })

            setCategories(categoryMap)

            const canonicIds = [
                ...new Set(
                    copyData
                        .filter(copy => copy.canonic !== null)
                        .map(copy => copy.canonic!.id)
                )
            ]

            const canonicData = await Promise.all(
                canonicIds.map(canonicId =>
                    getCanonicById(canonicId)
                )
            )

            const canonicMap: Record<number, Canonic> = {}

            canonicData.forEach(canonic => {
                canonicMap[canonic.id] = canonic
            })

            setCanonics(canonicMap)

            const locationIds = [
                ...new Set(
                    copyData
                        .filter(copy => copy.location !== null)
                        .map(copy => copy.location!.id)
                )
            ]

            const locationData = await Promise.all(
                locationIds.map(locationId =>
                    getLocationById(locationId)
                )
            )

            const locationMap: Record<number, Location> = {}

            locationData.forEach(location => {
                locationMap[location.id] = location
            })

            setLocations(locationMap)
        } catch (error) {
            console.error("Erro ao carregar coleção:", error)
        } finally {
            setLoading(false)
        }
    }, [id])

    useEffect(() => {
        loadCollection()
    }, [loadCollection])

    async function handleCopySaved(updatedCopy: Copy) {
        setCopies(current =>
            current.map(copy =>
                copy.id === updatedCopy.id
                    ? { ...copy, ...updatedCopy }
                    : copy
            )
        )

        if (updatedCopy.location?.id != null) {
            try {
                const updatedLocation = await getLocationById(
                    updatedCopy.location.id
                )

                setLocations(current => ({
                    ...current,
                    [updatedLocation.id]: updatedLocation
                }))
            } catch (error) {
                console.error(
                    'Erro ao atualizar a localização da cópia:',
                    error
                )
            }
        }

        setEditingCopy(null)
    }

    async function handleCopyDeleted(_copyId: number) {
        await loadCollection()
        setEditingCopy(null)
    }

    if (loading) {
        return (
            <div className="collection-page">
                <p className="collection-loading">
                    Carregando coleção...
                </p>
            </div>
        )
    }

    if (!collection) {
        return (
            <div className="collection-page">
                <p className="collection-loading">
                    Não foi possível carregar a coleção.
                </p>
            </div>
        )
    }

    return (
        <div className="collection-page">
            <section className="collection-header">
                <div className="collection-header-info">
                    <span className="section-label">
                        COLEÇÃO
                    </span>
                    <h1>
                        {collection.name}
                    </h1>
                    <p className="collection-visibility">
                        <i
                            className={
                                collection.isPublic
                                    ? 'bi bi-globe2'
                                    : 'bi bi-lock'
                            }
                        />
                        {collection.isPublic
                            ? 'Coleção pública'
                            : 'Coleção privada'}
                    </p>
                </div>
                <button
                    type="button"
                    className="collection-back-button"
                    onClick={() => navigate('/curator')}
                >
                    <i className="bi bi-arrow-left" />
                    Voltar para o perfil
                </button>
            </section>
            <section className="collection-info">
                <div className="collection-stat">
                    <strong>
                        {copies.length}
                    </strong>

                    <span>
                        {copies.length === 1 ? 'Item' : 'Itens'}
                    </span>
                </div>
            </section>
            <section className="collection-content">
                <div className="collection-content-header">
                    <div>
                        <span className="section-label">
                            ACERVO
                        </span>
                        <h2>
                            Itens da coleção
                        </h2>
                    </div>
                </div>
                {copies.length === 0 ? (
                    <div className="empty-collection">
                        <i className="bi bi-box" />
                        <p>
                            Esta coleção ainda não possui itens.
                        </p>
                    </div>
                ) : (
                    <div className="collection-grid">
                        {copies.map(copy => {
                            const canonic = copy.canonic
                                ? canonics[copy.canonic.id]
                                : null

                            const location = copy.location
                                ? locations[copy.location.id]
                                : null
                            const displayedProperties = Object.entries(
                                copy.property ?? {}
                            ).map(([propertyId, value]) => [
                                Number(propertyId),
                                value
                            ] as [number, string])

                            const displayName = copy.name?.trim() || canonic?.name || `Item #${copy.id}`
                            return (
                                <article
                                    className="collection-detail-item"
                                    key={copy.id}
                                >
                                    <div className="detail-item-header">
                                        <div className="detail-item-image">
                                            <i className="bi bi-box" />
                                        </div>
                                        <div className="detail-item-heading">
                                            <h3 title={displayName}>
                                                {displayName}
                                            </h3>
                                        </div>
                                        <button
                                            type="button"
                                            className="detail-item-edit"
                                            onClick={() => {
                                                if (canonic) {
                                                    setEditingCopy(copy)
                                                }
                                            }}
                                            disabled={!canonic}
                                            aria-label={`Editar ${displayName}`}
                                            title={
                                                canonic
                                                    ? 'Editar item'
                                                    : 'Este item não possui uma obra associada'
                                            }
                                        >
                                            <i className="bi bi-pencil" />
                                        </button>
                                    </div>
                                    <div className="detail-section">
                                        <h4>
                                            <i className="bi bi-tags" />
                                            Propriedades
                                        </h4>
                                        {displayedProperties.length > 0 ? (
                                            <div className="property-list">
                                                {displayedProperties.map(([propertyId, value]) => {
                                                    const property = properties[propertyId]

                                                    return (
                                                        <div
                                                            className="property-row"
                                                            key={propertyId}
                                                        >
                                                            <span>
                                                                {property
                                                                    ? property.name
                                                                    : `Propriedade #${propertyId}`}
                                                            </span>

                                                            <strong>{value}</strong>
                                                        </div>
                                                    )
                                                })}
                                            </div>
                                        ) : (
                                            <p className="detail-empty">
                                                Nenhuma propriedade cadastrada.
                                            </p>
                                        )}

                                    </div>
                                    <div className="detail-section">
                                        <h4>
                                            <i className="bi bi-book" />
                                            Informações do item
                                        </h4>

                                        <div className="category-info">
                                            <span>Categoria</span>
                                            <strong>
                                                {copy.category
                                                    ? categories[copy.category.id]?.name ??
                                                    `Categoria #${copy.category.id}`
                                                    : "Sem categoria"}
                                            </strong>
                                        </div>
                                    </div>
                                    <div className="detail-section">
                                        <h4>
                                            <i className="bi bi-geo-alt" />
                                            Localização
                                        </h4>
                                        {location ? (
                                            <div className="location-info">
                                                <strong>
                                                    {location.name}
                                                </strong>
                                                <span>
                                                    Latitude: {location.latitude}
                                                </span>

                                                <span>
                                                    Longitude: {location.longitude}
                                                </span>
                                            </div>
                                        ) : (
                                            <p className="detail-empty">
                                                Esta cópia não possui localização.
                                            </p>
                                        )}
                                    </div>
                                </article>
                            )
                        })}
                    </div>
                )}
            </section>
            {editingCopy?.canonic &&
                canonics[editingCopy.canonic.id] && (
                    <EditCopyModal
                        copy={editingCopy}
                        canonic={canonics[editingCopy.canonic.id]}
                        onClose={() => setEditingCopy(null)}
                        onSaved={handleCopySaved}
                        onDeleted={handleCopyDeleted}
                    />
                )}
        </div>
    )
}

export default CollectionPage