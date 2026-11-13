import { useEffect, useMemo, useState } from 'react'
import { useNavigate } from 'react-router-dom'

import {
    getLocationsByCurator,
    createLocation,
    updateLocation,
    deleteLocation,
    type Location
} from '../services/locationService'

import { getCurrentCurator } from '../services/curatorService'
import { useNotification } from '../context/NotificationContext'

import '../styles/Locations.css'

export default function Locations() {
    const navigate = useNavigate()
    const { showNotification } = useNotification()

    const [locations, setLocations] = useState<Location[]>([])
    const [curatorId, setCuratorId] = useState<number | null>(null)

    const [searchTerm, setSearchTerm] = useState('')
    const [locationName, setLocationName] = useState('')
    const [latitude, setLatitude] = useState('')
    const [longitude, setLongitude] = useState('')
    const [parentLocationId, setParentLocationId] = useState('')

    const [editingLocation, setEditingLocation] = useState<Location | null>(null)
    const [locationToDelete, setLocationToDelete] = useState<Location | null>(null)

    const [loading, setLoading] = useState(true)
    const [saving, setSaving] = useState(false)
    const [deletingId, setDeletingId] = useState<number | null>(null)
    const [showForm, setShowForm] = useState(false)

    useEffect(() => {
        async function loadLocations() {
            try {
                const curator = await getCurrentCurator()
                setCuratorId(curator.id)

                const data: Location[] = await getLocationsByCurator(curator.id)
                setLocations(data)
            } catch (error) {
                console.error('Erro ao carregar localizações:', error)

                showNotification(
                    'Não foi possível carregar suas localizações.',
                    'error'
                )
            } finally {
                setLoading(false)
            }
        }

        loadLocations()
    }, [])

    const filteredLocations = useMemo(() => {
        const normalizedSearch = searchTerm.trim().toLowerCase()

        return locations
            .filter(location =>
                location.name.toLowerCase().includes(normalizedSearch)
            )
            .sort((a, b) => a.name.localeCompare(b.name, 'pt-BR'))
    }, [locations, searchTerm])

    const locationsWithCoordinates = useMemo(() => {
        return locations.filter(location =>
            location.latitude !== null && location.longitude !== null
        ).length
    }, [locations])

    function getParentName(location: Location) {
        if (!location.location) {
            return 'Localização final'
        }

        const parent = locations.find(
            item => item.id === location.location?.id
        )

        return parent?.name ?? 'Localização vinculada'
    }

    function resetForm() {
        setLocationName('')
        setLatitude('')
        setLongitude('')
        setParentLocationId('')
        setEditingLocation(null)
    }

    function openCreateForm() {
        resetForm()
        setShowForm(true)
    }

    function openEditForm(location: Location) {
        setEditingLocation(location)
        setLocationName(location.name)
        setLatitude(location.latitude?.toString() ?? '')
        setLongitude(location.longitude?.toString() ?? '')
        setParentLocationId(location.location?.id.toString() ?? '')
        setShowForm(true)
    }

    function closeForm() {
        if (saving) return

        setShowForm(false)
        resetForm()
    }

    async function handleSave() {
        const normalizedName = locationName.trim()

        if (!normalizedName) {
            showNotification('Informe o nome da localização.', 'warning')
            return
        }

        if (curatorId === null) {
            showNotification(
                'Não foi possível identificar seu usuário.',
                'error'
            )
            return
        }

        const hasLatitude = latitude.trim() !== ''
        const hasLongitude = longitude.trim() !== ''

        if (hasLatitude !== hasLongitude) {
            showNotification(
                'Informe latitude e longitude juntas ou deixe ambas vazias.',
                'warning'
            )
            return
        }

        let parsedLatitude: number | null = null
        let parsedLongitude: number | null = null

        if (hasLatitude && hasLongitude) {
            parsedLatitude = Number(latitude.replace(',', '.'))
            parsedLongitude = Number(longitude.replace(',', '.'))

            if (
                !Number.isFinite(parsedLatitude) ||
                !Number.isFinite(parsedLongitude)
            ) {
                showNotification(
                    'Informe coordenadas numéricas válidas.',
                    'warning'
                )
                return
            }

            if (parsedLatitude < -90 || parsedLatitude > 90) {
                showNotification(
                    'A latitude deve estar entre -90 e 90.',
                    'warning'
                )
                return
            }

            if (parsedLongitude < -180 || parsedLongitude > 180) {
                showNotification(
                    'A longitude deve estar entre -180 e 180.',
                    'warning'
                )
                return
            }
        }

        const alreadyExists = locations.some(location =>
            location.name.trim().toLowerCase() === normalizedName.toLowerCase()
            && location.id !== editingLocation?.id
        )

        if (alreadyExists) {
            showNotification(
                'Você já possui uma localização com esse nome.',
                'warning'
            )
            return
        }

        const selectedParentId = parentLocationId
            ? Number(parentLocationId)
            : undefined

        if (selectedParentId !== undefined) {
            if (selectedParentId === editingLocation?.id) {
                showNotification(
                    'Uma localização não pode ser vinculada a ela mesma.',
                    'warning'
                )
                return
            }
        }

        try {
            setSaving(true)

            if (editingLocation) {
                const updatedLocation = await updateLocation(
                    editingLocation.id,
                    normalizedName,
                    parsedLatitude,
                    parsedLongitude,
                    selectedParentId,
                    curatorId
                )

                setLocations(previous =>
                    previous.map(location =>
                        location.id === updatedLocation.id
                            ? updatedLocation
                            : location
                    )
                )

                showNotification(
                    'Localização atualizada com sucesso!',
                    'success'
                )
            } else {
                const createdLocation = await createLocation(
                    normalizedName,
                    parsedLatitude,
                    parsedLongitude,
                    selectedParentId,
                    curatorId
                )

                setLocations(previous => [...previous, createdLocation])

                showNotification(
                    'Localização cadastrada com sucesso!',
                    'success'
                )
            }

            setShowForm(false)
            resetForm()
        } catch (error) {
            console.error('Erro ao salvar localização:', error)

            showNotification(
                editingLocation
                    ? 'Não foi possível atualizar a localização.'
                    : 'Não foi possível cadastrar a localização.',
                'error'
            )
        } finally {
            setSaving(false)
        }
    }

    async function handleDelete(location: Location) {
        try {
            setDeletingId(location.id)

            await deleteLocation(location.id)

            setLocations(previous =>
                previous.filter(item => item.id !== location.id)
            )

            showNotification(
                'Localização excluída com sucesso!',
                'success'
            )

            setLocationToDelete(null)
        } catch (error) {
            console.error('Erro ao excluir localização:', error)

            showNotification(
                'Não foi possível excluir a localização. Verifique se ela está sendo utilizada por algum item',
                'error'
            )
        } finally {
            setDeletingId(null)
        }
    }

    if (loading) {
        return (
            <div className="locations-page">
                <p className="locations-loading">
                    Carregando localizações...
                </p>
            </div>
        )
    }

    return (
        <div className="locations-page">
            <header className="locations-header">
                <div>
                    <span className="section-label">
                        ORGANIZAÇÃO DO ACERVO
                    </span>

                    <h1>Minhas localizações</h1>

                    <p>
                        Organize os lugares onde seus itens estão
                        armazenados e mantenha seu acervo sempre organizado.
                    </p>
                </div>

                <button
                    type="button"
                    className="locations-back-button"
                    onClick={() => navigate(-1)}
                >
                    <i className="bi bi-arrow-left"></i>
                    Voltar
                </button>
            </header>

            <main className="locations-content">
                <section className="locations-overview">
                    <div className="locations-overview-card">
                        <div className="locations-overview-icon">
                            <i className="bi bi-geo-alt"></i>
                        </div>

                        <div>
                            <span>Total cadastradas</span>
                            <strong>{locations.length}</strong>
                        </div>
                    </div>

                    <div className="locations-overview-card">
                        <div className="locations-overview-icon coordinates">
                            <i className="bi bi-crosshair"></i>
                        </div>

                        <div>
                            <span>Com coordenadas</span>
                            <strong>{locationsWithCoordinates}</strong>
                        </div>
                    </div>

                    <div className="locations-overview-card">
                        <div className="locations-overview-icon hierarchy">
                            <i className="bi bi-diagram-3"></i>
                        </div>

                        <div>
                            <span>Localizações finais</span>
                            <strong>
                                {locations.filter(item => !item.location).length}
                            </strong>
                        </div>
                    </div>
                </section>

                <div className="locations-toolbar">
                    <div>
                        <span className="section-label">
                            SEU ESPAÇO
                        </span>

                        <h2>Localizações cadastradas</h2>
                    </div>

                    <button
                        type="button"
                        className="locations-add-button"
                        onClick={openCreateForm}
                    >
                        <i className="bi bi-plus-lg"></i>
                        Nova localização
                    </button>
                </div>

                <div className="locations-search">
                    <i className="bi bi-search"></i>

                    <input
                        type="text"
                        value={searchTerm}
                        onChange={event => setSearchTerm(event.target.value)}
                        placeholder="Pesquisar localização pelo nome..."
                    />

                    {searchTerm && (
                        <button
                            type="button"
                            onClick={() => setSearchTerm('')}
                            aria-label="Limpar pesquisa"
                        >
                            <i className="bi bi-x-circle"></i>
                        </button>
                    )}
                </div>

                {showForm && (
                    <section className="locations-form">
                        <div className="locations-form-heading">
                            <div>
                                <span className="section-label">
                                    {editingLocation
                                        ? 'EDITAR LOCALIZAÇÃO'
                                        : 'NOVA LOCALIZAÇÃO'}
                                </span>

                                <h3>
                                    {editingLocation
                                        ? 'Atualizar informações'
                                        : 'Adicionar ao seu espaço'}
                                </h3>
                            </div>

                            <button
                                type="button"
                                className="locations-form-close"
                                onClick={closeForm}
                                disabled={saving}
                                aria-label="Fechar formulário"
                            >
                                <i className="bi bi-x-lg"></i>
                            </button>
                        </div>

                        <div className="locations-form-grid">
                            <div className="locations-form-field full-width">
                                <label htmlFor="location-name">
                                    Nome da localização *
                                </label>

                                <input
                                    id="location-name"
                                    type="text"
                                    value={locationName}
                                    onChange={event =>
                                        setLocationName(event.target.value)
                                    }
                                    onKeyDown={event => {
                                        if (event.key === 'Enter') handleSave()
                                    }}
                                    placeholder="Ex.: Estante principal, Quarto, Sala..."
                                    autoFocus
                                    maxLength={100}
                                />
                            </div>

                            <div className="locations-form-field full-width">
                                <label htmlFor="parent-location">
                                    Vincular a outra localização
                                </label>

                                <select
                                    id="parent-location"
                                    value={parentLocationId}
                                    onChange={event =>
                                        setParentLocationId(event.target.value)
                                    }
                                >
                                    <option value="">
                                        Nenhuma (localização final)
                                    </option>

                                    {locations
                                        .filter(location =>
                                            location.id !== editingLocation?.id
                                        )
                                        .sort((a, b) =>
                                            a.name.localeCompare(b.name, 'pt-BR')
                                        )
                                        .map(location => (
                                            <option
                                                key={location.id}
                                                value={location.id}
                                            >
                                                {location.name}
                                            </option>
                                        ))}
                                </select>

                                <small>
                                    Você pode criar uma hierarquia entre suas
                                    localizações.
                                </small>
                            </div>

                            <div className="locations-coordinate-heading full-width">
                                <i className="bi bi-globe2"></i>
                                <div>
                                    <strong>Coordenadas geográficas</strong>
                                    <span>
                                        Informações opcionais sobre a localização.
                                    </span>
                                </div>
                            </div>

                            <div className="locations-form-field">
                                <label htmlFor="location-latitude">
                                    Latitude
                                </label>

                                <input
                                    id="location-latitude"
                                    type="text"
                                    inputMode="decimal"
                                    value={latitude}
                                    onChange={event =>
                                        setLatitude(event.target.value)
                                    }
                                    placeholder="Ex.: -23.5505"
                                />
                            </div>

                            <div className="locations-form-field">
                                <label htmlFor="location-longitude">
                                    Longitude
                                </label>

                                <input
                                    id="location-longitude"
                                    type="text"
                                    inputMode="decimal"
                                    value={longitude}
                                    onChange={event =>
                                        setLongitude(event.target.value)
                                    }
                                    placeholder="Ex.: -46.6333"
                                />
                            </div>
                        </div>

                        <p className="locations-form-hint">
                            <i className="bi bi-info-circle"></i>
                            Latitude e longitude devem ser preenchidas juntas
                            ou deixadas vazias.
                        </p>

                        <div className="locations-form-actions">
                            <button
                                type="button"
                                className="locations-cancel-button"
                                onClick={closeForm}
                                disabled={saving}
                            >
                                Cancelar
                            </button>

                            <button
                                type="button"
                                className="locations-save-button"
                                onClick={handleSave}
                                disabled={saving}
                            >
                                {saving
                                    ? 'Salvando...'
                                    : editingLocation
                                        ? 'Salvar alterações'
                                        : 'Cadastrar localização'}
                            </button>
                        </div>
                    </section>
                )}

                <section className="locations-list">
                    {filteredLocations.length > 0 ? (
                        filteredLocations.map(location => (
                            <article
                                className="locations-card"
                                key={location.id}
                            >
                                <div className="locations-card-icon">
                                    <i className="bi bi-geo-alt"></i>
                                </div>

                                <div className="locations-card-info">
                                    <div className="locations-card-title">
                                        <strong>{location.name}</strong>

                                        {!location.location && (
                                            <span className="locations-main-badge">
                                                Final
                                            </span>
                                        )}
                                    </div>

                                    <div className="locations-card-details">
                                        <span>
                                            <i className="bi bi-diagram-3"></i>
                                            {getParentName(location)}
                                        </span>

                                        {location.latitude !== null &&
                                            location.longitude !== null ? (
                                            <span>
                                                <i className="bi bi-crosshair"></i>
                                                {location.latitude}, {location.longitude}
                                            </span>
                                        ) : (
                                            <span>
                                                <i className="bi bi-geo"></i>
                                                Sem coordenadas
                                            </span>
                                        )}
                                    </div>
                                </div>

                                <div className="locations-card-actions">
                                    <button
                                        type="button"
                                        className="locations-edit-button"
                                        onClick={() => openEditForm(location)}
                                        disabled={saving || deletingId !== null}
                                        title="Editar localização"
                                    >
                                        <i className="bi bi-pencil"></i>
                                    </button>

                                    <button
                                        type="button"
                                        className="locations-delete-button"
                                        onClick={() =>
                                            setLocationToDelete(location)
                                        }
                                        disabled={saving || deletingId !== null}
                                        title="Excluir localização"
                                    >
                                        {deletingId === location.id
                                            ? <i className="bi bi-hourglass-split"></i>
                                            : <i className="bi bi-trash"></i>}
                                    </button>
                                </div>
                            </article>
                        ))
                    ) : (
                        <div className="locations-empty">
                            <i className="bi bi-geo-alt"></i>

                            <h3>
                                {searchTerm
                                    ? 'Nenhuma localização encontrada'
                                    : 'Seu espaço ainda está vazio'}
                            </h3>

                            <p>
                                {searchTerm
                                    ? 'Tente pesquisar usando outro termo.'
                                    : 'Cadastre suas localizações para registrar onde seus itens estão armazenados.'}
                            </p>

                            {!searchTerm && (
                                <button
                                    type="button"
                                    onClick={openCreateForm}
                                >
                                    <i className="bi bi-plus-lg"></i>
                                    Criar primeira localização
                                </button>
                            )}
                        </div>
                    )}
                </section>

                <div className="locations-footer">
                    Exibindo {filteredLocations.length} de {locations.length}{' '}
                    {locations.length === 1
                        ? 'localização'
                        : 'localizações'}
                </div>

                {locationToDelete && (
                    <div className="locations-delete-overlay">
                        <div className="locations-delete-modal">
                            <div className="locations-delete-icon">
                                <i className="bi bi-geo-alt"></i>
                            </div>

                            <h2>Excluir localização?</h2>

                            <p>
                                Tem certeza de que deseja excluir a localização
                                <strong> "{locationToDelete.name}"</strong>?
                            </p>

                            <p className="locations-delete-warning">
                                Essa ação não poderá ser desfeita.
                            </p>

                            <div className="locations-delete-actions">
                                <button
                                    type="button"
                                    className="locations-delete-cancel"
                                    onClick={() => setLocationToDelete(null)}
                                    disabled={deletingId !== null}
                                >
                                    Cancelar
                                </button>

                                <button
                                    type="button"
                                    className="locations-delete-confirm"
                                    onClick={() => handleDelete(locationToDelete)}
                                    disabled={deletingId === locationToDelete.id}
                                >
                                    {deletingId === locationToDelete.id
                                        ? 'Excluindo...'
                                        : 'Excluir localização'}
                                </button>
                            </div>
                        </div>
                    </div>
                )}
            </main>
        </div>
    )
}
