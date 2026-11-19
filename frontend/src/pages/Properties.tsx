import { useEffect, useMemo, useState } from 'react'
import { useNavigate } from 'react-router-dom'

import {
    getPropertiesByCurator,
    createProperty,
    updateProperty,
    deleteProperty,
    type Property
} from '../services/propertyService'

import { getCurrentCurator } from '../services/curatorService'
import { useNotification } from '../context/NotificationContext'

import DeleteCheckModal from '../components/DeleteCheckModal'

import '../styles/Properties.css'

export default function Properties() {
    const navigate = useNavigate()
    const { showNotification } = useNotification()

    const [properties, setProperties] = useState<Property[]>([])
    const [curatorId, setCuratorId] = useState<number | null>(null)

    const [searchTerm, setSearchTerm] = useState('')
    const [propertyName, setPropertyName] = useState('')
    const [editingProperty, setEditingProperty] = useState<Property | null>(null)

    const [loading, setLoading] = useState(true)
    const [saving, setSaving] = useState(false)
    const [deletingId, setDeletingId] = useState<number | null>(null)

    const [showForm, setShowForm] = useState(false)
    const [propertyToDelete, setPropertyToDelete] = useState<Property | null>(null)

    useEffect(() => {
        async function loadProperties() {
            try {
                const curator = await getCurrentCurator()
                setCuratorId(curator.id)

                const data = await getPropertiesByCurator(curator.id)
                setProperties(data)
            } catch (error) {
                console.error('Erro ao carregar properties:', error)

                showNotification(
                    'Não foi possível carregar suas propriedades.',
                    'error'
                )
            } finally {
                setLoading(false)
            }
        }

        loadProperties()
    }, [])

    const filteredProperties = useMemo(() => {
        const normalizedSearch = searchTerm.trim().toLowerCase()

        return properties
            .filter(property =>
                property.name.toLowerCase().includes(normalizedSearch)
            )
            .sort((a, b) => a.name.localeCompare(b.name, 'pt-BR'))
    }, [properties, searchTerm])

    function openCreateForm() {
        setEditingProperty(null)
        setPropertyName('')
        setShowForm(true)
    }

    function openEditForm(property: Property) {
        setEditingProperty(property)
        setPropertyName(property.name)
        setShowForm(true)
    }

    function closeForm() {
        setShowForm(false)
        setEditingProperty(null)
        setPropertyName('')
    }

    async function handleSave() {
        const normalizedName = propertyName.trim()

        if (!normalizedName) {
            showNotification('Informe o nome da propriedade.', 'warning')
            return
        }

        if (curatorId === null) {
            showNotification('Não foi possível identificar seu usuário.', 'error')
            return
        }

        const alreadyExists = properties.some(property =>
            property.name.trim().toLowerCase() === normalizedName.toLowerCase()
            && property.id !== editingProperty?.id
        )

        if (alreadyExists) {
            showNotification(
                'Você já possui uma propriedade com esse nome.',
                'warning'
            )
            return
        }

        try {
            setSaving(true)

            if (editingProperty) {
                const updatedProperty = await updateProperty(
                    editingProperty.id,
                    normalizedName,
                    curatorId
                )

                setProperties(previous =>
                    previous.map(property =>
                        property.id === updatedProperty.id
                            ? updatedProperty
                            : property
                    )
                )

                showNotification(
                    'Propriedade atualizada com sucesso!',
                    'success'
                )
            } else {
                const createdProperty = await createProperty(
                    normalizedName,
                    curatorId
                )

                setProperties(previous => [...previous, createdProperty])

                showNotification(
                    'Propriedade cadastrada com sucesso!',
                    'success'
                )
            }

            closeForm()
        } catch (error) {
            console.error('Erro ao salvar propriedade:', error)

            showNotification(
                editingProperty
                    ? 'Não foi possível atualizar a propriedade.'
                    : 'Não foi possível cadastrar a propriedade.',
                'error'
            )
        } finally {
            setSaving(false)
        }
    }

    async function handleDelete(property: Property) {
        try {
            setDeletingId(property.id)

            await deleteProperty(property.id)

            setProperties(previous =>
                previous.filter(item => item.id !== property.id)
            )

            showNotification(
                'Propriedade excluída com sucesso!',
                'success'
            )

            setPropertyToDelete(null)
        } catch (error) {
            console.error('Erro ao excluir propriedade:', error)

            showNotification(
                'Não foi possível excluir a propriedade. Verifique se ela está sendo utilizada por algum item.',
                'error'
            )
        } finally {
            setDeletingId(null)
        }
    }

    if (loading) {
        return (
            <div className="properties-page">
                <p className="properties-loading">
                    Carregando propriedades...
                </p>
            </div>
        )
    }

    return (
        <div className="properties-page">
            <header className="properties-header">
                <div>
                    <span className="section-label">
                        ORGANIZAÇÃO DO ACERVO
                    </span>

                    <h1>Minhas propriedades</h1>

                    <p>
                        Gerencie as propriedades utilizadas para descrever
                        e identificar os itens do seu acervo.
                    </p>
                </div>

                <button
                    type="button"
                    className="properties-back-button"
                    onClick={() => navigate(-1)}
                >
                    <i className="bi bi-arrow-left"></i>
                    Voltar
                </button>
            </header>

            <main className="properties-content">
                <div className="properties-toolbar">
                    <div className="properties-summary">
                        <span className="section-label">
                            SUAS PROPRIEDADES
                        </span>

                        <h2>
                            {properties.length}{' '}
                            {properties.length === 1
                                ? 'propriedade cadastrada'
                                : 'propriedades cadastradas'}
                        </h2>
                    </div>

                    <button
                        type="button"
                        className="properties-add-button"
                        onClick={openCreateForm}
                    >
                        <i className="bi bi-plus-lg"></i>
                        Nova propriedade
                    </button>
                </div>

                <div className="properties-search">
                    <i className="bi bi-search"></i>

                    <input
                        type="text"
                        value={searchTerm}
                        onChange={event => setSearchTerm(event.target.value)}
                        placeholder="Pesquisar propriedade pelo nome..."
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
                    <section className="properties-form">
                        <div className="properties-form-heading">
                            <div>
                                <span className="section-label">
                                    {editingProperty
                                        ? 'EDITAR PROPRIEDADE'
                                        : 'NOVA PROPRIEDADE'}
                                </span>

                                <h3>
                                    {editingProperty
                                        ? 'Alterar nome'
                                        : 'Adicionar propriedade'}
                                </h3>
                            </div>

                            <button
                                type="button"
                                className="properties-form-close"
                                onClick={closeForm}
                                disabled={saving}
                                aria-label="Fechar formulário"
                            >
                                <i className="bi bi-x-lg"></i>
                            </button>
                        </div>

                        <div className="properties-form-field">
                            <label htmlFor="property-name">
                                Nome da propriedade
                            </label>

                            <input
                                id="property-name"
                                type="text"
                                value={propertyName}
                                onChange={event => setPropertyName(event.target.value)}
                                onKeyDown={event => {
                                    if (event.key === 'Enter') handleSave()
                                }}
                                placeholder="Ex.: Autor, Editora, Ano, Título..."
                                autoFocus
                                maxLength={100}
                            />
                        </div>

                        <div className="properties-form-actions">
                            <button
                                type="button"
                                className="properties-cancel-button"
                                onClick={closeForm}
                                disabled={saving}
                            >
                                Cancelar
                            </button>

                            <button
                                type="button"
                                className="properties-save-button"
                                onClick={handleSave}
                                disabled={saving}
                            >
                                {saving
                                    ? 'Salvando...'
                                    : editingProperty
                                        ? 'Salvar alterações'
                                        : 'Cadastrar propriedade'}
                            </button>
                        </div>
                    </section>
                )}

                <section className="properties-list">
                    {filteredProperties.length > 0 ? (
                        filteredProperties.map(property => (
                            <article
                                className="properties-card"
                                key={property.id}
                            >
                                <div className="properties-card-icon">
                                    <i className="bi bi-card-list"></i>
                                </div>

                                <div className="properties-card-info">
                                    <strong>{property.name}</strong>
                                    <span>Propriedade do seu acervo</span>
                                </div>

                                <div className="properties-card-actions">
                                    <button
                                        type="button"
                                        className="properties-edit-button"
                                        onClick={() => openEditForm(property)}
                                        disabled={saving || deletingId !== null}
                                        title="Editar propriedade"
                                    >
                                        <i className="bi bi-pencil"></i>
                                    </button>

                                    <button
                                        type="button"
                                        className="properties-delete-button"
                                        onClick={() => setPropertyToDelete(property)}
                                        disabled={saving || deletingId !== null}
                                        title="Excluir propriedade"
                                    >
                                        {deletingId === property.id
                                            ? <i className="bi bi-hourglass-split"></i>
                                            : <i className="bi bi-trash"></i>}
                                    </button>
                                </div>
                            </article>
                        ))
                    ) : (
                        <div className="properties-empty">
                            <i className="bi bi-card-list"></i>

                            <h3>
                                {searchTerm
                                    ? 'Nenhuma propriedade encontrada'
                                    : 'Você ainda não possui propriedades'}
                            </h3>

                            <p>
                                {searchTerm
                                    ? 'Tente pesquisar usando outro termo.'
                                    : 'Adicione sua primeira propriedade para começar a descrever seu acervo.'}
                            </p>

                            {!searchTerm && (
                                <button
                                    type="button"
                                    onClick={openCreateForm}
                                >
                                    <i className="bi bi-plus-lg"></i>
                                    Criar primeira propriedade
                                </button>
                            )}
                        </div>
                    )}
                </section>

                <div className="properties-footer">
                    Exibindo {filteredProperties.length} de {properties.length}{' '}
                    {properties.length === 1
                        ? 'propriedade'
                        : 'propriedades'}
                </div>

                {propertyToDelete && (
                    <DeleteCheckModal
                        itemName={propertyToDelete.name}
                        itemType="propriedade"
                        deleting={deletingId === propertyToDelete.id}
                        onCancel={() => setPropertyToDelete(null)}
                        onConfirm={() => handleDelete(propertyToDelete)}
                    />
                )}
            </main>
        </div>
    )
}
