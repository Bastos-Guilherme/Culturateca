import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { getCurrentCurator, getAllCurator, type Curator } from '../services/curatorService'
import { getCollectionsByCurator, type Collection } from '../services/collectionService'
import { getCopyById } from '../services/copyService'
import { getAllCategory, type Category } from '../services/categoryService'
import { getCanonicById, type Canonic } from '../services/canonicService'

import '../styles/Curator.css'

import NewItem from '../components/NewItem'
import AddCollectionModal from '../components/AddCollectionModal'

function CuratorPage() {
    const [curator, setCurator] = useState<Curator | null>(null)
    const [curatorEmail, setCuratorEmail] = useState<string>('')
    const [collections, setCollections] = useState<Collection[]>([])
    const [loading, setLoading] = useState(true)
    const [copyNames, setCopyNames] = useState<Record<number, string>>({})
    const [categories, setCategories] = useState<Record<number, Category>>({})

    const [showAddCollectionModal, setShowAddCollectionModal] = useState(false)

    const [searchTerm, setSearchTerm] = useState('')
    const [collectionSort, setCollectionSort] = useState<'all' | 'largest' | 'smallest'>('all')

    const navigate = useNavigate()

    useEffect(() => {
        async function loadProfile() {
            try {
                const currentCurator = await getCurrentCurator()

                setCurator(currentCurator)
                setCuratorEmail(currentCurator.email)

                const [curatorCollections] = await Promise.all([
                    getCollectionsByCurator(currentCurator.email),
                    getAllCurator()
                ])

                setCollections(curatorCollections)

                const copyIds = [
                    ...new Set(
                        curatorCollections.flatMap(collection =>
                            collection.copies.map(copy => copy.id)
                        )
                    )
                ]

                const copyData = await Promise.all(
                    copyIds.map(copyId => getCopyById(copyId))
                )

                const canonicIds = [
                    ...new Set(
                        copyData
                            .filter(copy => copy.canonic !== null)
                            .map(copy => copy.canonic!.id)
                    )
                ]

                const [canonicData, categoryData] = await Promise.all([
                    Promise.all(
                        canonicIds.map(id => getCanonicById(id))
                    ),
                    getAllCategory()
                ])

                const canonicMap: Record<number, Canonic> = {}

                canonicData.forEach(canonic => {
                    canonicMap[canonic.id] = canonic
                })

                const categoryMap: Record<number, Category> = {}

                categoryData.forEach(category => {
                    categoryMap[category.id] = category
                })

                setCategories(categoryMap)

                const copyNameMap: Record<number, string> = {}

                copyData.forEach(copy => {
                    const canonic = copy.canonic
                        ? canonicMap[copy.canonic.id]
                        : null

                    copyNameMap[copy.id] =
                        copy.name?.trim() ||
                        canonic?.name ||
                        `Item #${copy.id}`
                })

                setCopyNames(copyNameMap)

            } catch (error) {
                console.error(error)
            } finally {
                setLoading(false)
            }
        }

        loadProfile()
    }, [])

    function handleCollectionCreated(collection: Collection) {
        setCollections(previous => {
            const alreadyExists = previous.some(
                item => item.id === collection.id
            )
            return alreadyExists
                ? previous
                : [...previous, collection]
        })
        setShowAddCollectionModal(false)
    }

    const uniqueCopyIds = new Set(
        collections.flatMap(
            collection =>
                collection.copies.map(copy => copy.id)
        )
    )

    function normalizeSearch(value: string) {
        return value
            .normalize('NFD')
            .replace(/[\u0300-\u036f]/g, '')
            .toLowerCase()
            .trim()
    }

    const filteredCollections = collections.filter(collection => {
        const search = normalizeSearch(searchTerm)

        if (!search) return true

        const copySearchValues = collection.copies.flatMap(copy => {
            const categoryName = copy.category
                ? categories[copy.category.id]?.name
                : null

            return [
                copyNames[copy.id],
                categoryName,
                `Item #${copy.id}`
            ]
        })

        const itemCount = collection.copies.length

        const searchableValues = [
            collection.name,
            String(itemCount),
            `${itemCount} item`,
            `${itemCount} itens`,
            ...copySearchValues
        ]

        return searchableValues.some(value =>
            normalizeSearch(String(value ?? '')).includes(search)
        )
    }).sort((a, b) => {
        if (collectionSort === 'largest') {
            return b.copies.length - a.copies.length
        }

        if (collectionSort === 'smallest') {
            return a.copies.length - b.copies.length
        }

        return 0
    })

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

    return (
        <div className="curator-page">
            <section className="curator-header">
                <div className="curator-info">
                    <div className="curator-name-row">
                        <div>
                            <h1>{curator.name}</h1>
                            <p className="curator-username">
                                @{curator.email.split('@')[0]}
                            </p>
                        </div>
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
                </div>
                <div className="curator-stats">
                    <div className="curator-stat">
                        <strong>{collections.length}</strong>
                        <span>Coleções</span>
                    </div>

                    <div className="curator-stat">
                        <strong>{uniqueCopyIds.size}</strong>
                        <span>Itens</span>
                    </div>
                </div>
            </section>

            <section className="curator-collections">
                <div className="collections-header">
                    <div className="collections-title">
                        <span className="section-label">
                            ACERVO SALVAGUARDADO
                        </span>

                        <h2>Acervos & Coleções</h2>
                    </div>
                    <div className="collection-filters">
                        <NewItem
                            icon="bi-plus"
                            title="Adicionar coleção"
                            description="Criar uma nova coleção"
                            className="collection-new-item collections-header-new-item"
                            onClick={() => setShowAddCollectionModal(true)}
                        />
                        <button
                            type="button"
                            className={collectionSort === 'all' ? 'active' : ''}
                            onClick={() => setCollectionSort('all')}
                        >
                            Todas as coleções
                        </button>
                        <button
                            type="button"
                            className={collectionSort === 'largest' ? 'active' : ''}
                            onClick={() => setCollectionSort('largest')}
                        >
                            Maiores coleções
                        </button>
                        <button
                            type="button"
                            className={collectionSort === 'smallest' ? 'active' : ''}
                            onClick={() => setCollectionSort('smallest')}
                        >
                            Menores coleções
                        </button>
                    </div>
                </div>
                <div className="collections-search">
                    <i className="bi bi-search"></i>
                    <input
                        type="text"
                        placeholder="Pesquisar coleções, itens ou categorias..."
                        value={searchTerm}
                        onChange={event => setSearchTerm(event.target.value)}
                    />
                    {searchTerm && (
                        <button
                            type="button"
                            className="collections-search-clear"
                            onClick={() => setSearchTerm('')}
                            title="Limpar pesquisa"
                        >
                            <i className="bi bi-x-lg"></i>
                        </button>
                    )}
                </div>
                {collections.length === 0 ? (
                    <div className="empty-collections">
                        <i className="bi bi-archive"></i>
                        <p>
                            Este curador ainda não possui coleções.
                        </p>
                    </div>
                ) : filteredCollections.length === 0 ? (
                    <div className="empty-collections">
                        <i className="bi bi-search"></i>
                        <p>
                            Nenhuma coleção encontrada para "{searchTerm}".
                        </p>
                    </div>
                ) : (
                    <div className="collections-list">
                        {filteredCollections.map(collection => (
                            <div className="collection-card" key={collection.id}>
                                <div className="collection-card-header">
                                    <div>
                                        <div className="collection-title">
                                            <h3>
                                                {collection.name}
                                            </h3>
                                        </div>
                                        <p>
                                            {collection.copies.length === 0
                                                ? 'Esta coleção ainda não possui itens.'
                                                : `${collection.copies.length} item${collection.copies.length !== 1 ? 's' : ''} nesta coleção.`}
                                        </p>
                                    </div>
                                    <div className="collection-card-actions">
                                        <NewItem
                                            icon="bi-plus"
                                            title="Adicionar item"
                                            description="Adicionar novo item a esta coleção"
                                            route={`/createItem`}
                                            className="collection-new-item"
                                        />
                                        <button
                                            className="collection-button"
                                            onClick={() => navigate(`/collection/${collection.id}`)}
                                        >
                                            Ver coleção
                                            <i className="bi bi-arrow-right"></i>
                                        </button>
                                    </div>
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
                                                    <h4 title={copyNames[copy.id] ?? `Item #${copy.id}`}>
                                                        {copyNames[copy.id] ?? `Item #${copy.id}`}
                                                    </h4>

                                                    <span className="collection-item-category">
                                                        {copy.category
                                                            ? categories[copy.category.id]?.name ?? 'Sem categoria'
                                                            : 'Sem categoria'}
                                                    </span>
                                                </div>
                                            ))}
                                    </div>
                                )}
                            </div>
                        ))}
                    </div>
                )}
            </section>
            {showAddCollectionModal && (
                <AddCollectionModal
                    collections={collections}
                    curatorEmail={curatorEmail}
                    onClose={() => setShowAddCollectionModal(false)}
                    onCreated={handleCollectionCreated}
                />
            )}
        </div>
    )
}

export default CuratorPage