import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'

import { getAllCategory, type Category } from '../services/categoryService'
import { getAllProperty, type Property } from '../services/propertyService'
import { getCollectionsByCurator, type Collection } from '../services/collectionService'
import { getLocationsByCurator, type Location } from '../services/locationService'
import { getCurrentCurator } from '../services/curatorService'
import AddPropertyModal from '../components/AddPropertyModal'

import '../styles/CreateItem.css'

interface ItemProperty {
    propertyId: number | null
    value: string
}

export default function CreateItem() {
    const navigate = useNavigate()

    const [categories, setCategories] = useState<Category[]>([])
    const [properties, setProperties] = useState<Property[]>([])
    const [collections, setCollections] = useState<Collection[]>([])
    const [locations, setLocations] = useState<Location[]>([])

    const [selectedCategory, setSelectedCategory] = useState<number | null>(null)
    const [itemName, setItemName] = useState('')
    const [itemProperties, setItemProperties] = useState<ItemProperty[]>([])
    const [selectedCollection, setSelectedCollection] = useState<number | null>(null)
    const [selectedLocation, setSelectedLocation] = useState<number | null>(null)
    const [showPropertyModal, setShowPropertyModal] = useState(false)

    const [loading, setLoading] = useState(true)

    useEffect(() => {
        async function loadData() {
            try {
                const [
                    categoryData,
                    propertyData,
                    curatorData
                ] = await Promise.all([
                    getAllCategory(),
                    getAllProperty(),
                    getCurrentCurator()
                ])

                setCategories(categoryData)
                setProperties(propertyData)

                const [
                    collectionData,
                    locationData
                ] = await Promise.all([
                    getCollectionsByCurator(curatorData.email),
                    getLocationsByCurator(curatorData.id)
                ])

                setCollections(collectionData)
                setLocations(locationData)
            } catch (error) {
                console.error(
                    'Erro ao carregar dados do cadastro:',
                    error
                )
            } finally {
                setLoading(false)
            }
        }
        loadData()
    }, [])

    function addPropertyField() {
        setItemProperties(previous => [
            ...previous,
            {
                propertyId: null,
                value: ''
            }
        ])
    }

    function removePropertyField(index: number) {
        setItemProperties(previous =>
            previous.filter((_, propertyIndex) =>
                propertyIndex !== index
            )
        )
    }

    function updatePropertyField(
        index: number,
        propertyId: number | null
    ) {
        setItemProperties(previous =>
            previous.map((itemProperty, propertyIndex) =>
                propertyIndex === index
                    ? {
                        ...itemProperty,
                        propertyId
                    }
                    : itemProperty
            )
        )
    }

    function updatePropertyValue(
        index: number,
        value: string
    ) {
        setItemProperties(previous =>
            previous.map((itemProperty, propertyIndex) =>
                propertyIndex === index
                    ? {
                        ...itemProperty,
                        value
                    }
                    : itemProperty
            )
        )
    }

    function handlePropertyCreated(
        property: Property
    ) {
        setProperties(previous => [
            ...previous,
            property
        ])
        setItemProperties(previous => [
            ...previous,
            {
                propertyId: property.id,
                value: ''
            }
        ])
        setShowPropertyModal(false)
    }

    function handleSave() {
        console.log({
            category: selectedCategory,
            name: itemName,
            properties: itemProperties,
            collection: selectedCollection,
            location: selectedLocation
        })
    }

    if (loading) {
        return (
            <div className="create-item-page">
                <p className="create-item-loading">
                    Carregando informações...
                </p>
            </div>
        )
    }

    return (
        <div className="create-item-page">
            <header className="create-item-header">
                <div>
                    <span className="section-label">
                        NOVO ITEM
                    </span>
                    <h1>
                        Adicionar item ao acervo
                    </h1>
                    <p>
                        Cadastre uma nova obra ou artefato
                        cultural em sua coleção.
                    </p>
                </div>
                <button
                    className="create-item-back-button"
                    onClick={() => navigate(-1)}
                >
                    <i className="bi bi-arrow-left"></i>
                    Voltar para as coleções
                </button>
            </header>
            <main className="create-item-content">
                <section className="create-item-categories">
                    <div className="create-item-section-heading">
                        <span className="section-label">
                            01. CATEGORIAS DE ACERVO
                        </span>
                        <h2>
                            O que você deseja arquivar?
                        </h2>
                        <p>
                            Selecione uma categoria para
                            começar a catalogar seu artefato
                            cultural.
                        </p>
                    </div>
                    <div className="category-list">
                        {categories.map(category => (
                            <button
                                key={category.id}
                                type="button"
                                className={
                                    `create-item-category ${
                                        selectedCategory === category.id
                                            ? 'selected'
                                            : ''
                                    }`
                                }
                                onClick={() =>
                                    setSelectedCategory(category.id)
                                }
                            >
                                <div className="category-icon">
                                    <i className="bi bi-box"></i>
                                </div>
                                <div className="category-content">
                                    <strong>
                                        {category.name}
                                    </strong>
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
                        ))}
                        <button
                            type="button"
                            className="create-item-category new-category"
                        >
                            <div className="category-icon">
                                <i className="bi bi-plus-lg"></i>
                            </div>
                            <div className="category-content">
                                <strong>
                                    Criar nova categoria
                                </strong>

                                <span>
                                    Crie uma nova categoria
                                    para seu acervo.
                                </span>
                            </div>
                            <div className="category-check">
                                <i className="bi bi-arrow-right"></i>
                            </div>
                        </button>
                    </div>
                </section>
                <section className="create-item-form">
                    <div className="create-item-section-heading">
                        <span className="section-label">
                            02. INFORMAÇÕES DETALHADAS
                        </span>
                        <h2>
                            Ficha técnica do artefato
                        </h2>
                        <p>
                            Adicione as informações que
                            identificam e descrevem o item.
                        </p>
                    </div>
                    <div className="create-item-field">
                        <label>
                            Nome da obra ou item
                        </label>
                        <input
                            type="text"
                            value={itemName}
                            onChange={event =>
                                setItemName(event.target.value)
                            }
                            placeholder="Insira um nome explicativo do artefato"
                        />
                    </div>
                    <div className="create-item-properties">
                        <div className="create-item-properties-header">
                            <div>
                                <label>
                                    Propriedades adicionais
                                </label>

                                <small>
                                    Adicione campos para
                                    complementar as informações
                                    do item.
                                </small>
                            </div>
                            <button
                                type="button"
                                className="create-item-add-property"
                                onClick={() =>
                                    setShowPropertyModal(true)
                                }
                            >
                                <i className="bi bi-plus-circle"></i>
                                Nova propriedade
                            </button>
                        </div>
                        {itemProperties.map(
                            (itemProperty, index) => (
                                <div
                                    className="create-item-property-row"
                                    key={index}
                                >
                                    <select
                                        value={
                                            itemProperty.propertyId ?? ''
                                        }
                                        onChange={event =>
                                            updatePropertyField(
                                                index,
                                                event.target.value
                                                    ? Number(event.target.value)
                                                    : null
                                            )
                                        }
                                    >
                                        <option value="">
                                            Selecione uma propriedade
                                        </option>
                                        {properties.map(property => (

                                            <option
                                                key={property.id}
                                                value={property.id}
                                            >
                                                {property.name}
                                            </option>
                                        ))}
                                    </select>
                                    <input
                                        type="text"
                                        value={itemProperty.value}
                                        onChange={event =>
                                            updatePropertyValue(
                                                index,
                                                event.target.value
                                            )
                                        }
                                        placeholder="Valor"
                                    />
                                    <button
                                        type="button"
                                        className="create-item-remove-property"
                                        onClick={() =>
                                            removePropertyField(index)
                                        }
                                    >
                                        <i className="bi bi-trash"></i>
                                    </button>
                                </div>
                            )
                        )}
                        <button
                            type="button"
                            className="create-item-add-field"
                            onClick={addPropertyField}
                        >
                            <i className="bi bi-plus-circle"></i>
                            Adicionar campo
                        </button>
                    </div>
                    <div className="create-item-row">
                        <div className="create-item-field">
                            <label>
                                Vincular à coleção
                            </label>
                            <select
                                value={selectedCollection ?? ''}
                                onChange={event =>
                                    setSelectedCollection(
                                        event.target.value
                                            ? Number(event.target.value)
                                            : null
                                    )
                                }
                            >
                                <option value="">
                                    Selecione uma coleção
                                </option>
                                {collections.map(collection => (
                                    <option
                                        key={collection.id}
                                        value={collection.id}
                                    >
                                        {collection.name}
                                    </option>

                                ))}
                            </select>
                            <button
                                type="button"
                                className="create-item-inline-action"
                            >
                                <i className="bi bi-plus"></i>
                                Adicionar coleção
                            </button>
                        </div>
                        <div className="create-item-field">
                            <label>
                                Local físico de guarda
                            </label>
                            <select
                                value={selectedLocation ?? ''}
                                onChange={event =>
                                    setSelectedLocation(
                                        event.target.value
                                            ? Number(event.target.value)
                                            : null
                                    )
                                }
                            >
                                <option value="">
                                    Selecione um local
                                </option>
                                {locations.map(location => (
                                    <option
                                        key={location.id}
                                        value={location.id}
                                    >
                                        {location.name}
                                    </option>
                                ))}
                            </select>
                            <button
                                type="button"
                                className="create-item-inline-action"
                            >
                                <i className="bi bi-plus"></i>
                                Adicionar localização
                            </button>
                        </div>
                    </div>
                    <div className="create-item-actions">
                        <button
                            type="button"
                            className="create-item-cancel"
                            onClick={() => navigate(-1)}
                        >
                            Cancelar
                        </button>
                        <button
                            type="button"
                            className="create-item-save"
                            onClick={handleSave}
                        >
                            Concluir e salvar
                            <i className="bi bi-arrow-right"></i>
                        </button>
                    </div>
                </section>
            </main>
            {showPropertyModal && (
                <AddPropertyModal
                    properties={properties}
                    onClose={() =>
                        setShowPropertyModal(false)
                    }
                    onCreated={handlePropertyCreated}
                />
            )}
        </div>
    )
}