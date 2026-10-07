import { useState } from 'react'

import { createProperty, type Property } from '../services/propertyService'

import '../styles/AddPropertyModal.css'

interface AddPropertyModalProps {
    properties: Property[]
    onClose: () => void
    onCreated: (property: Property) => void
}

function AddPropertyModal({
    properties,
    onClose,
    onCreated
}: AddPropertyModalProps) {
    const [name, setName] = useState('')
    const [saving, setSaving] = useState(false)
    const [error, setError] = useState('')

    async function handleCreate() {
        const alreadyExists = properties.some(
            property =>
                property.name.toLowerCase() ===
                name.toLowerCase()
        )
        if (alreadyExists) {
            setError('Essa propriedade já existe.')
            return
        }
        try {
            setSaving(true)
            setError('')
            const createdProperty = await createProperty(name)
            onCreated(createdProperty)
        } catch (error) {
            console.error(error)
            setError(
                'Não foi possível cadastrar a propriedade.'
            )
        } finally {
            setSaving(false)
        }
    }

    return (
        <div
            className="add-property-overlay"
            onMouseDown={event => {
                if (event.target === event.currentTarget) {
                    onClose()
                }
            }}
        >
            <div className="add-property-modal">
                <div className="add-property-header">
                    <div>
                        <span className="section-label">
                            NOVA PROPRIEDADE
                        </span>
                        <h2>
                            Adicionar campo
                        </h2>
                    </div>
                    <button
                        type="button"
                        className="add-property-close"
                        onClick={onClose}
                    >
                        <i className="bi bi-x-lg"></i>
                    </button>
                </div>
                <div className="add-property-body">
                    <p className="add-property-description">
                        Propriedades representam os campos
                        utilizados para descrever seus itens.
                    </p>
                    <div className="add-property-existing">
                        <label>
                            Propriedades disponíveis
                        </label>
                        <div className="add-property-list">
                            {properties.length === 0 ? (
                                <span>
                                    Nenhuma propriedade cadastrada.
                                </span>
                            ) : (
                                properties.map(property => (
                                    <div
                                        className="add-property-item"
                                        key={property.id}
                                    >
                                        <i className="bi bi-tag"></i>
                                        {property.name}
                                    </div>
                                ))
                            )}
                        </div>
                    </div>
                    <div className="add-property-divider">
                        <span>ou crie uma nova</span>
                    </div>
                    <div className="add-property-field">
                        <label>
                            Nome da propriedade
                        </label>
                        <input
                            type="text"
                            value={name}
                            onChange={event =>
                                setName(event.target.value)
                            }
                            placeholder="Ex.: Autor, Ano, Editora..."
                            autoFocus
                        />
                    </div>
                    {error && (
                        <p className="add-property-error">
                            {error}
                        </p>
                    )}
                </div>
                <div className="add-property-actions">
                    <button
                        type="button"
                        className="add-property-cancel"
                        onClick={onClose}
                        disabled={saving}
                    >
                        Cancelar
                    </button>
                    <button
                        type="button"
                        className="add-property-save"
                        onClick={handleCreate}
                        disabled={saving}
                    >
                        {saving
                            ? 'Cadastrando...'
                            : 'Cadastrar propriedade'
                        }
                    </button>
                </div>
            </div>
        </div>
    )
}

export default AddPropertyModal