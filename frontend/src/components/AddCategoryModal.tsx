import { useState } from 'react'

import { createCategory, type Category } from '../services/categoryService'

import '../styles/AddCategoryModal.css'

interface AddCategoryModalProps {
    categories: Category[]
    onClose: () => void
    onCreated: (category: Category) => void
}

function AddCategoryModal({
    categories,
    onClose,
    onCreated
}: AddCategoryModalProps) {
    const [name, setName] = useState('')
    const [saving, setSaving] = useState(false)
    const [error, setError] = useState('')

    async function handleCreate() {
        const alreadyExists = categories.some(
            category =>
                category.name.toLowerCase() ===
                name.toLowerCase()
        )
        if (alreadyExists) {
            setError('Essa categoria já existe.')
            return
        }
        try {
            setSaving(true)
            setError('')
            const createdCategory = await createCategory(name)
            onCreated(createdCategory)
        } catch (error) {
            console.error(error)
            setError(
                'Não foi possível cadastrar a categoria.'
            )
        } finally {
            setSaving(false)
        }
    }

    return (
        <div
            className="add-category-overlay"
            onMouseDown={event => {
                if (event.target === event.currentTarget) {
                    onClose()
                }
            }}
        >
            <div className="add-category-modal">
                <div className="add-category-header">
                    <div>
                        <span className="section-label">
                            NOVA CATEGORIA
                        </span>
                        <h2>
                            Adicionar categoria
                        </h2>
                    </div>
                    <button
                        type="button"
                        className="add-category-close"
                        onClick={onClose}
                    >
                        <i className="bi bi-x-lg"></i>
                    </button>
                </div>
                <div className="add-category-body">
                    <p className="add-category-description">
                        Categorias representam as definições do tipo de seu item.
                    </p>
                    <div className="add-category-existing">
                        <label>
                            Categorias disponíveis
                        </label>
                        <div className="add-category-list">
                            {categories.length === 0 ? (
                                <span>
                                    Nenhuma categoria cadastrada.
                                </span>
                            ) : (
                                categories.map(category => (
                                    <div
                                        className="add-category-item"
                                        key={category.id}
                                    >
                                        <i className="bi bi-tag"></i>
                                        {category.name}
                                    </div>
                                ))
                            )}
                        </div>
                    </div>
                    <div className="add-category-divider">
                        <span>ou crie uma nova</span>
                    </div>
                    <div className="add-category-field">
                        <label>
                            Nome da categoria
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
                        <p className="add-category-error">
                            {error}
                        </p>
                    )}
                </div>
                <div className="add-category-actions">
                    <button
                        type="button"
                        className="add-category-cancel"
                        onClick={onClose}
                        disabled={saving}
                    >
                        Cancelar
                    </button>
                    <button
                        type="button"
                        className="add-category-save"
                        onClick={handleCreate}
                        disabled={saving}
                    >
                        {saving
                            ? 'Cadastrando...'
                            : 'Cadastrar categoria'
                        }
                    </button>
                </div>
            </div>
        </div>
    )
}

export default AddCategoryModal