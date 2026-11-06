
import { useEffect, useMemo, useState } from 'react'
import { useNavigate } from 'react-router-dom'

import {
    getCategoriesByCurator,
    createCategory,
    updateCategory,
    deleteCategory,
    type Category
} from '../services/categoryService'

import { getCurrentCurator } from '../services/curatorService'
import { useNotification } from '../context/NotificationContext'

import '../styles/Categories.css'

export default function Categories() {
    const navigate = useNavigate()
    const { showNotification } = useNotification()

    const [categories, setCategories] = useState<Category[]>([])
    const [curatorId, setCuratorId] = useState<number | null>(null)

    const [searchTerm, setSearchTerm] = useState('')
    const [categoryName, setCategoryName] = useState('')
    const [editingCategory, setEditingCategory] = useState<Category | null>(null)

    const [loading, setLoading] = useState(true)
    const [saving, setSaving] = useState(false)
    const [deletingId, setDeletingId] = useState<number | null>(null)
    const [showForm, setShowForm] = useState(false)
    const [categoryToDelete, setCategoryToDelete] = useState<Category | null>(null)

    useEffect(() => {
        async function loadCategories() {
            try {
                const curator = await getCurrentCurator()
                setCuratorId(curator.id)

                const data = await getCategoriesByCurator(curator.id)
                setCategories(data)
            } catch (error) {
                console.error('Erro ao carregar categorias:', error)
                showNotification(
                    'Não foi possível carregar suas categorias.',
                    'error'
                )
            } finally {
                setLoading(false)
            }
        }

        loadCategories()
    }, [])

    const filteredCategories = useMemo(() => {
        const normalizedSearch = searchTerm.trim().toLowerCase()

        return categories
            .filter(category =>
                category.name.toLowerCase().includes(normalizedSearch)
            )
            .sort((a, b) => a.name.localeCompare(b.name, 'pt-BR'))
    }, [categories, searchTerm])

    function openCreateForm() {
        setEditingCategory(null)
        setCategoryName('')
        setShowForm(true)
    }

    function openEditForm(category: Category) {
        setEditingCategory(category)
        setCategoryName(category.name)
        setShowForm(true)
    }

    function closeForm() {
        setShowForm(false)
        setEditingCategory(null)
        setCategoryName('')
    }

    async function handleSave() {
        const normalizedName = categoryName.trim()

        if (!normalizedName) {
            showNotification('Informe o nome da categoria.', 'warning')
            return
        }

        if (curatorId === null) {
            showNotification('Não foi possível identificar seu usuário.', 'error')
            return
        }

        const alreadyExists = categories.some(category =>
            category.name.trim().toLowerCase() === normalizedName.toLowerCase()
            && category.id !== editingCategory?.id
        )

        if (alreadyExists) {
            showNotification('Você já possui uma categoria com esse nome.', 'warning')
            return
        }

        try {
            setSaving(true)

            if (editingCategory) {
                const updatedCategory = await updateCategory(
                    editingCategory.id,
                    normalizedName,
                    curatorId
                )

                setCategories(previous =>
                    previous.map(category =>
                        category.id === updatedCategory.id
                            ? updatedCategory
                            : category
                    )
                )

                showNotification('Categoria atualizada com sucesso!', 'success')
            } else {
                const createdCategory = await createCategory(
                    normalizedName,
                    curatorId
                )

                setCategories(previous => [...previous, createdCategory])

                showNotification('Categoria cadastrada com sucesso!', 'success')
            }

            closeForm()
        } catch (error) {
            console.error('Erro ao salvar categoria:', error)

            showNotification(
                editingCategory
                    ? 'Não foi possível atualizar a categoria.'
                    : 'Não foi possível cadastrar a categoria.',
                'error'
            )
        } finally {
            setSaving(false)
        }
    }

    async function handleDelete(category: Category) {
        try {
            setDeletingId(category.id)

            await deleteCategory(category.id)

            setCategories(previous =>
                previous.filter(item => item.id !== category.id)
            )

            showNotification('Categoria excluída com sucesso!', 'success')
            setCategoryToDelete(null)
        } catch (error) {
            console.error('Erro ao excluir categoria:', error)

            showNotification(
                'Não foi possível excluir a categoria. Verifique se ela está sendo utilizada por algum item.',
                'error'
            )
        } finally {
            setDeletingId(null)
        }
    }

    if (loading) {
        return (
            <div className="categories-page">
                <p className="categories-loading">
                    Carregando categorias...
                </p>
            </div>
        )
    }

    return (
        <div className="categories-page">
            <header className="categories-header">
                <div>
                    <span className="section-label">
                        ORGANIZAÇÃO DO ACERVO
                    </span>

                    <h1>Minhas categorias</h1>

                    <p>
                        Gerencie as categorias utilizadas para organizar
                        e identificar os itens do seu acervo.
                    </p>
                </div>

                <button
                    type="button"
                    className="categories-back-button"
                    onClick={() => navigate(-1)}
                >
                    <i className="bi bi-arrow-left"></i>
                    Voltar
                </button>
            </header>

            <main className="categories-content">
                <div className="categories-toolbar">
                    <div className="categories-summary">
                        <span className="section-label">
                            SUAS CATEGORIAS
                        </span>

                        <h2>
                            {categories.length}{' '}
                            {categories.length === 1
                                ? 'categoria cadastrada'
                                : 'categorias cadastradas'}
                        </h2>
                    </div>

                    <button
                        type="button"
                        className="categories-add-button"
                        onClick={openCreateForm}
                    >
                        <i className="bi bi-plus-lg"></i>
                        Nova categoria
                    </button>
                </div>

                <div className="categories-search">
                    <i className="bi bi-search"></i>

                    <input
                        type="text"
                        value={searchTerm}
                        onChange={event => setSearchTerm(event.target.value)}
                        placeholder="Pesquisar categoria pelo nome..."
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
                    <section className="categories-form">
                        <div className="categories-form-heading">
                            <div>
                                <span className="section-label">
                                    {editingCategory
                                        ? 'EDITAR CATEGORIA'
                                        : 'NOVA CATEGORIA'}
                                </span>

                                <h3>
                                    {editingCategory
                                        ? 'Alterar nome'
                                        : 'Adicionar categoria'}
                                </h3>
                            </div>

                            <button
                                type="button"
                                className="categories-form-close"
                                onClick={closeForm}
                                disabled={saving}
                                aria-label="Fechar formulário"
                            >
                                <i className="bi bi-x-lg"></i>
                            </button>
                        </div>

                        <div className="categories-form-field">
                            <label htmlFor="category-name">
                                Nome da categoria
                            </label>

                            <input
                                id="category-name"
                                type="text"
                                value={categoryName}
                                onChange={event => setCategoryName(event.target.value)}
                                onKeyDown={event => {
                                    if (event.key === 'Enter') handleSave()
                                }}
                                placeholder="Ex.: Livro, Revista, Jogo..."
                                autoFocus
                                maxLength={100}
                            />
                        </div>

                        <div className="categories-form-actions">
                            <button
                                type="button"
                                className="categories-cancel-button"
                                onClick={closeForm}
                                disabled={saving}
                            >
                                Cancelar
                            </button>

                            <button
                                type="button"
                                className="categories-save-button"
                                onClick={handleSave}
                                disabled={saving}
                            >
                                {saving
                                    ? 'Salvando...'
                                    : editingCategory
                                        ? 'Salvar alterações'
                                        : 'Cadastrar categoria'}
                            </button>
                        </div>
                    </section>
                )}

                <section className="categories-list">
                    {filteredCategories.length > 0 ? (
                        filteredCategories.map(category => (
                            <article
                                className="categories-card"
                                key={category.id}
                            >
                                <div className="categories-card-icon">
                                    <i className="bi bi-tag"></i>
                                </div>

                                <div className="categories-card-info">
                                    <strong>{category.name}</strong>
                                    <span>Categoria do seu acervo</span>
                                </div>

                                <div className="categories-card-actions">
                                    <button
                                        type="button"
                                        className="categories-edit-button"
                                        onClick={() => openEditForm(category)}
                                        disabled={saving || deletingId !== null}
                                        title="Editar categoria"
                                    >
                                        <i className="bi bi-pencil"></i>
                                    </button>

                                    <button
                                        type="button"
                                        className="categories-delete-button"
                                        onClick={() => setCategoryToDelete(category)}
                                        disabled={saving || deletingId !== null}
                                        title="Excluir categoria"
                                    >
                                        {deletingId === category.id
                                            ? <i className="bi bi-hourglass-split"></i>
                                            : <i className="bi bi-trash"></i>}
                                    </button>
                                </div>
                            </article>
                        ))
                    ) : (
                        <div className="categories-empty">
                            <i className="bi bi-tags"></i>

                            <h3>
                                {searchTerm
                                    ? 'Nenhuma categoria encontrada'
                                    : 'Você ainda não possui categorias'}
                            </h3>

                            <p>
                                {searchTerm
                                    ? 'Tente pesquisar usando outro termo.'
                                    : 'Adicione sua primeira categoria para começar a organizar seu acervo.'}
                            </p>

                            {!searchTerm && (
                                <button
                                    type="button"
                                    onClick={openCreateForm}
                                >
                                    <i className="bi bi-plus-lg"></i>
                                    Criar primeira categoria
                                </button>
                            )}
                        </div>
                    )}
                </section>

                <div className="categories-footer">
                    Exibindo {filteredCategories.length} de {categories.length}{' '}
                    {categories.length === 1 ? 'categoria' : 'categorias'}
                </div>

                {categoryToDelete && (
                    <div className="delete-modal-overlay">
                        <div className="delete-modal">
                            <div className="delete-modal-icon">
                                <i className="bi bi-trash"></i>
                            </div>

                            <h2>Excluir categoria?</h2>

                            <p>
                                Tem certeza de que deseja excluir a categoria
                                <strong> "{categoryToDelete.name}"</strong>?
                            </p>

                            <p className="delete-modal-warning">
                                Essa ação não poderá ser desfeita.
                            </p>

                            <div className="delete-modal-actions">
                                <button
                                    className="delete-modal-cancel"
                                    onClick={() => setCategoryToDelete(null)}
                                >
                                    Cancelar
                                </button>

                                <button
                                    className="delete-modal-confirm"
                                    onClick={() => handleDelete(categoryToDelete)}
                                    disabled={deletingId === categoryToDelete.id}
                                >
                                    {deletingId === categoryToDelete.id
                                        ? 'Excluindo...'
                                        : 'Excluir categoria'}
                                </button>
                            </div>
                        </div>
                    </div>
                )}
            </main>
        </div>
    )
}
