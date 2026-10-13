import { useState } from 'react'

import { createCollection, type Collection } from '../services/collectionService'

import '../styles/AddCollectionModal.css'

interface AddCollectionModalProps {
    collections: Collection[]
    curatorEmail: string
    onClose: () => void
    onCreated: (collection: Collection) => void
}

function AddCollectionModal({
    collections,
    curatorEmail,
    onClose,
    onCreated
}: AddCollectionModalProps) {
    const [name, setName] = useState('')
    const [isPublic, setIsPublic] = useState(false)
    const [saving, setSaving] = useState(false)
    const [error, setError] = useState('')

    async function handleCreate() {
        const normalizedName = name.trim()

        if (!normalizedName) {
            setError('Informe o nome da coleção.')
            return
        }

        const alreadyExists = collections.some(
            collection =>
                collection.name.trim().toLowerCase() ===
                normalizedName.toLowerCase()
        )

        if (alreadyExists) {
            setError('Você já possui uma coleção com esse nome.')
            return
        }

        try {
            setSaving(true)
            setError('')
            const createdCollection = await createCollection(
                curatorEmail,
                normalizedName,
                isPublic,
                []
            )
            onCreated(createdCollection)
        } catch (error) {
            console.error(error)
            setError(
                'Não foi possível cadastrar a coleção.'
            )
        } finally {
            setSaving(false)
        }
    }

    return (
        <div
            className="add-collection-overlay"
            onMouseDown={event => {
                if (event.target === event.currentTarget && !saving) {
                    onClose()
                }
            }}
        >
            <div
                className="add-collection-modal"
                role="dialog"
                aria-modal="true"
                aria-labelledby="add-collection-title"
            >
                <div className="add-collection-header">
                    <div>
                        <span className="section-label">
                            NOVA COLEÇÃO
                        </span>

                        <h2 id="add-collection-title">
                            Adicionar coleção
                        </h2>
                    </div>
                    <button
                        type="button"
                        className="add-collection-close"
                        onClick={onClose}
                        disabled={saving}
                        aria-label="Fechar modal"
                    >
                        <i className="bi bi-x-lg"></i>
                    </button>
                </div>
                <div className="add-collection-body">
                    <p className="add-collection-description">
                        Organize seus itens em uma coleção
                        para encontrá-los com mais facilidade.
                    </p>
                    <div className="add-collection-field">
                        <label htmlFor="collection-name">
                            Nome da coleção
                        </label>
                        <input
                            id="collection-name"
                            type="text"
                            value={name}
                            onChange={event =>
                                setName(event.target.value)
                            }
                            placeholder="Ex.: Meus livros favoritos"
                            maxLength={100}
                            autoFocus
                            disabled={saving}
                            onKeyDown={event => {
                                if (event.key === 'Enter') {
                                    event.preventDefault()
                                    handleCreate()
                                }
                            }}
                        />
                    </div>
                    <div className="add-collection-visibility">
                        <div className="add-collection-visibility-text">
                            <span> Coleção pública </span>
                            <p>
                                {isPublic
                                    ? 'Outras pessoas poderão visualizar sua coleção.'
                                    : 'Somente você poderá visualizar sua coleção.'}
                            </p>
                        </div>
                        <label className="switch">
                            <input
                                type="checkbox"
                                checked={isPublic}
                                onChange={event =>
                                    setIsPublic(event.target.checked)
                                }
                                disabled={saving}
                            />
                            <span className="slider"></span>
                        </label>
                    </div>
                    {error && (
                        <p className="add-collection-error" role="alert">
                            {error}
                        </p>
                    )}
                </div>
                <div className="add-collection-actions">
                    <button
                        type="button"
                        className="add-collection-cancel"
                        onClick={onClose}
                        disabled={saving}
                    >
                        Cancelar
                    </button>
                    <button
                        type="button"
                        className="add-collection-save"
                        onClick={handleCreate}
                        disabled={saving}
                    >
                        {saving
                            ? 'Cadastrando...'
                            : 'Cadastrar coleção'}
                    </button>
                </div>
            </div>
        </div>
    )
}

export default AddCollectionModal