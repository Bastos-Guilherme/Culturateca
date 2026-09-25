import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'

import { getCollectionById, type Collection } from '../services/collectionService'

import { getCopyById, type Copy } from '../services/copyService'

import { getCanonicById, type Canonic } from '../services/canonicService'

import { getLocationById, type Location } from '../services/locationService'

import { getAllProperty, type Property } from '../services/propertyService'

import { getAllCategory, type Category } from '../services/categoryService'

import '../styles/Collection.css'

function Collection() {

    const { id } = useParams<{ id: string }>()
    const navigate = useNavigate()

    const [collection, setCollection] = useState<Collection | null>(null)

    const [copies, setCopies] = useState<Copy[]>([])

    const [canonics, setCanonics] = useState<Record<number, Canonic>>({})

    const [locations, setLocations] = useState<Record<number, Location>>({})

    const [properties, setProperties] = useState<Record<number, Property>>({})

    const [categories, setCategories] = useState<Record<number, Category>>({})

    const [loading, setLoading] = useState(true)

    useEffect(() => {
        async function loadCollection() {
            try {
                if (!id) {
                    return
                }

                const collectionData = await getCollectionById(Number(id))

                setCollection(collectionData)

                const copyData = await Promise.all(
                    collectionData.copies.map(copy => getCopyById(copy.id))
                )

                setCopies(copyData)

                const propertyData = await getAllProperty()

                const propertyMap: Record<number, Property> = {}

                propertyData.forEach((property: Property) => {
                    propertyMap[property.id] = property
                })

                setProperties(propertyMap)

                const categoryData = await getAllCategory()

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
                    canonicIds.map(canonicId => getCanonicById(canonicId))
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
                    locationIds.map(locationId => getLocationById(locationId))
                )

                const locationMap: Record<number, Location> = {}

                locationData.forEach(location => {
                    locationMap[location.id] = location
                })

                setLocations(locationMap)
            } catch (error) {
                console.error(error)
            } finally {
                setLoading(false)
            }
        }
        loadCollection()
    }, [id])

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
                        ></i>
                        {collection.isPublic
                            ? 'Coleção pública'
                            : 'Coleção privada'}
                    </p>
                </div>

                <button
                    className="collection-back-button"
                    onClick={() => navigate('/curator')}
                >
                    <i className="bi bi-arrow-left"></i>
                    Voltar para o perfil
                </button>
            </section>

            <section className="collection-info">
                <div className="collection-stat">
                    <strong>
                        {copies.length}
                    </strong>
                    <span>
                        {copies.length === 1
                            ? 'Item'
                            : 'Itens'}
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
                        <i className="bi bi-box"></i>
                        <p>
                            Esta coleção ainda não possui itens.
                        </p>
                    </div>
                ) : (
                    <div className="collection-grid">
                        {copies.map(copy => {
                            const canonic =
                                copy.canonic
                                    ? canonics[copy.canonic.id]
                                    : null
                            const location =
                                copy.location
                                    ? locations[copy.location.id]
                                    : null

                            return (
                                <div
                                    className="collection-detail-item"
                                    key={copy.id}
                                >
                                    <div className="detail-item-header">
                                        <div className="detail-item-image">
                                            <i className="bi bi-box"></i>
                                        </div>

                                        <div>
                                            <h3>
                                                Item #{copy.id}
                                            </h3>
                                            <small>
                                                Cód. COP-
                                                {String(copy.id).padStart(3, '0')}
                                            </small>
                                        </div>
                                    </div>
                                    <div className="detail-section">
                                        <h4>
                                            <i className="bi bi-tags"></i>
                                            Propriedades da cópia
                                        </h4>

                                        {Object.entries(copy.property).length > 0 ? (
                                            <div className="property-list">
                                                {Object.entries(copy.property).map(
                                                    ([propertyId, value]) => {
                                                        const property =
                                                            properties[
                                                            Number(propertyId)
                                                            ]

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
                                                                <strong>
                                                                    {value}
                                                                </strong>
                                                            </div>
                                                        )
                                                    }
                                                )}
                                            </div>
                                        ) : (
                                            <p className="detail-empty">
                                                Nenhuma propriedade cadastrada.
                                            </p>
                                        )}
                                    </div>

                                    <div className="detail-section">
                                        <h4>
                                            <i className="bi bi-book"></i>
                                            Obra
                                        </h4>

                                        {canonic ? (
                                            <>
                                                <p className="detail-id">
                                                    {canonic.category
                                                        ? categories[
                                                            canonic.category.id
                                                        ]?.name
                                                        ?? `Categoria #${canonic.category.id}`
                                                        : 'Sem categoria'
                                                    } # {canonic.id}
                                                </p>

                                                {Object.entries(
                                                    canonic.property
                                                ).length > 0 && (
                                                        <div className="property-list">
                                                            {Object.entries(
                                                                canonic.property
                                                            ).map(
                                                                ([propertyId, value]) => {
                                                                    const property =
                                                                        properties[
                                                                        Number(propertyId)
                                                                        ]

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
                                                                            <strong>
                                                                                {value}
                                                                            </strong>
                                                                        </div>
                                                                    )
                                                                }
                                                            )}
                                                        </div>
                                                    )}
                                                <div className="category-info">
                                                    <span>
                                                        Categoria
                                                    </span>
                                                    <strong>
                                                        {canonic.category
                                                            ? categories[
                                                                canonic.category.id
                                                            ]?.name
                                                            ?? `Categoria #${canonic.category.id}`

                                                            : 'Sem categoria'
                                                        }
                                                    </strong>
                                                </div>
                                            </>
                                        ) : (
                                            <p className="detail-empty">
                                                Esta cópia não possui
                                                uma obra associada.
                                            </p>
                                        )}
                                    </div>

                                    <div className="detail-section">
                                        <h4>
                                            <i className="bi bi-geo-alt"></i>
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
                                                Esta cópia não possui
                                                localização.
                                            </p>
                                        )}
                                    </div>
                                </div>
                            )
                        })}
                    </div>
                )}
            </section>
        </div>
    )
}

export default Collection