import { useState } from 'react'
import { type Category } from '../services/categoryService'
import '../styles/AllCategoriesModal.css'

interface AllCategoriesModalProps {
    categories: Category[]
    selectedCategory: number | null
    onSelect: (id: number) => void
    onClose: () => void
    onCreateCategory: () => void
}

function AllCategoriesModal({
    categories,
    selectedCategory,
    onSelect,
    onClose,
    onCreateCategory
}: AllCategoriesModalProps) {
    const [searchTerm, setSearchTerm] = useState('')

    const filteredCategories = categories.filter(category =>
        category.name
            .toLowerCase()
            .includes(searchTerm.trim().toLowerCase())
    )

    function handleSelect(id: number) {
        onSelect(id)
        onClose()
    }

    function handleCreateCategory() {
        onClose()
        onCreateCategory()
    }

    return (
        <div
            className="all-categories-overlay"
            onClick={onClose}
        >
            <div
                className="all-categories-modal"
                onClick={event => event.stopPropagation()}
            >
                <div className="all-categories-header">
                    <div>
                        <h2>Selecionar categoria</h2>
                        <p>
                            Pesquise e selecione uma categoria
                            para seu item.
                        </p>
                    </div>
                    <button
                        type="button"
                        className="all-categories-close"
                        onClick={onClose}
                        aria-label="Fechar modal"
                    >
                        <i className="bi bi-x-lg"></i>
                    </button>
                </div>
                <div className="all-categories-search">
                    <i className="bi bi-search"></i>

                    <input
                        type="text"
                        placeholder="Buscar categoria pelo nome..."
                        value={searchTerm}
                        onChange={event =>
                            setSearchTerm(event.target.value)
                        }
                        autoFocus
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
                <div className="all-categories-list">
                    {filteredCategories.length > 0 ? (
                        filteredCategories.map(category => (
                            <button
                                key={category.id}
                                type="button"
                                className={`create-item-category ${
                                    selectedCategory === category.id
                                        ? 'selected'
                                        : ''
                                }`}
                                onClick={() =>
                                    handleSelect(category.id)
                                }
                            >
                                <div className="category-icon">
                                    <i className="bi bi-box"></i>
                                </div>
                                <div className="category-content">
                                    <strong>{category.name}</strong>

                                    <span>
                                        Categoria disponível
                                        para este acervo.
                                    </span>
                                </div>
                                <div className="category-check">
                                    <i
                                        className={
                                            selectedCategory === category.id
                                                ? 'bi bi-check-circle-fill'
                                                : 'bi bi-circle'
                                        }
                                    ></i>
                                </div>
                            </button>
                        ))
                    ) : (
                        <div className="all-categories-empty">
                            <i className="bi bi-search"></i>
                            <p>
                                Nenhuma categoria encontrada.
                            </p>
                            <span>
                                Tente pesquisar por outro nome
                                ou crie uma nova categoria.
                            </span>
                        </div>
                    )}
                </div>
                <div className="all-categories-footer">
                    <span>
                        {filteredCategories.length}{' '}
                        {filteredCategories.length === 1
                            ? 'categoria encontrada'
                            : 'categorias encontradas'}
                    </span>
                    <button
                        type="button"
                        className="all-categories-create"
                        onClick={handleCreateCategory}
                    >
                        <i className="bi bi-plus-lg"></i>
                        Criar nova categoria
                    </button>
                </div>
            </div>
        </div>
    )
}

export default AllCategoriesModal