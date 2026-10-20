import { useEffect, useState } from "react"

import type { Copy } from "../services/copyService"
import { getCopyById, updateCopy, deleteCopy } from "../services/copyService"
import type { Canonic } from "../services/canonicService"
import type { Property } from "../services/propertyService"
import { getAllProperty } from "../services/propertyService"
import type { Location } from "../services/locationService"
import { getLocationsByCurator } from "../services/locationService"
import { getCurrentCurator } from "../services/curatorService"

import AddPropertyModal from "./AddPropertyModal"
import AddLocationModal from "./AddLocationModal"

import DeleteCopyModal from "./DeleteCopyModal"

import "../styles/EditCopyModal.css"

interface EditCopyModalProps {
    copy: Copy
    canonic: Canonic
    onClose: () => void
    onSaved: (copy: Copy) => void
    onDeleted: (copyId: number) => void
}

interface PropertyRow {
    propertyId: number | null
    value: string
}

export default function EditCopyModal({
    copy,
    canonic,
    onClose,
    onSaved,
    onDeleted
}: EditCopyModalProps) {
    const [name, setName] = useState("")
    const [rows, setRows] = useState<PropertyRow[]>([])
    const [properties, setProperties] = useState<Property[]>([])
    const [locations, setLocations] = useState<Location[]>([])
    const [curatorId, setCuratorId] = useState<number | null>(null)
    const [selectedLocation, setSelectedLocation] = useState("")
    const [showAddPropertyModal, setShowAddPropertyModal] = useState(false)
    const [showAddLocationModal, setShowAddLocationModal] = useState(false)
    const [loading, setLoading] = useState(true)
    const [saving, setSaving] = useState(false)
    const [error, setError] = useState("")

    const [showDeleteCopyModal, setShowDeleteCopyModal] = useState(false)
    const [deletingCopy, setDeletingCopy] = useState(false)

    useEffect(() => {
        async function loadData() {
            try {
                setLoading(true)
                setError("")

                const [allProperties, curator] = await Promise.all([
                    getAllProperty(),
                    getCurrentCurator()
                ])

                const curatorLocations = await getLocationsByCurator(
                    curator.id
                )

                setProperties(allProperties)
                setCuratorId(curator.id)
                setLocations(curatorLocations)

                setName(copy.name ?? "")
                setSelectedLocation(
                    copy.location?.id != null
                        ? String(copy.location.id)
                        : ""
                )

                const hidden = new Set(
                    (copy.hiddenProperties ?? []).map(Number)
                )

                const combined = new Map<number, string>()

                for (const [id, value] of Object.entries(
                    canonic.property ?? {}
                )) {
                    const propertyId = Number(id)

                    if (!hidden.has(propertyId)) {
                        combined.set(propertyId, value)
                    }
                }

                for (const [id, value] of Object.entries(
                    copy.property ?? {}
                )) {
                    combined.set(Number(id), value)
                }

                setRows(
                    Array.from(combined.entries()).map(
                        ([propertyId, value]) => ({
                            propertyId,
                            value
                        })
                    )
                )
            } catch (e) {
                console.error(e)
                setError("Não foi possível carregar os dados para edição.")
            } finally {
                setLoading(false)
            }
        }

        loadData()
    }, [copy, canonic])

    function updateRow(
        index: number,
        changes: Partial<PropertyRow>
    ) {
        setRows(current =>
            current.map((row, i) =>
                i === index ? { ...row, ...changes } : row
            )
        )
    }

    function addRow() {
        setRows(current => [
            ...current,
            { propertyId: null, value: "" }
        ])
    }

    function removeRow(index: number) {
        setRows(current => current.filter((_, i) => i !== index))
    }

    function handlePropertyCreated(property: Property) {
        setProperties(current => {
            if (current.some(item => item.id === property.id)) {
                return current
            }

            return [...current, property]
        })

        setRows(current => [
            ...current,
            {
                propertyId: property.id,
                value: ""
            }
        ])

        setShowAddPropertyModal(false)
    }

    function handleLocationCreated(location: Location) {
        setLocations(current => {
            if (current.some(item => item.id === location.id)) {
                return current
            }

            return [...current, location]
        })

        setSelectedLocation(String(location.id))
        setShowAddLocationModal(false)
    }

    async function handleSave() {
        setError("")

        if (!selectedLocation) {
            setError("Selecione uma localização para a cópia.")
            return
        }

        if (rows.some(row =>
            row.propertyId == null || !row.value.trim()
        )) {
            setError(
                "Preencha a propriedade e seu valor ou remova a linha incompleta."
            )
            return
        }

        const propertyIds = rows.map(row => row.propertyId as number)

        if (new Set(propertyIds).size !== propertyIds.length) {
            setError("Cada propriedade pode aparecer apenas uma vez.")
            return
        }

        const canonicalProperties = canonic.property ?? {}
        const copyProperties: { [key: number]: string } = {}
        const hiddenProperties: number[] = []

        const currentValues = new Map<number, string>(
            rows.map(row => [
                row.propertyId as number,
                row.value.trim()
            ])
        )

        for (const id of Object.keys(canonicalProperties)) {
            const propertyId = Number(id)
            const currentValue = currentValues.get(propertyId)

            if (currentValue === undefined) {
                hiddenProperties.push(propertyId)
                continue
            }

            if (currentValue !== canonicalProperties[propertyId]) {
                copyProperties[propertyId] = currentValue
                hiddenProperties.push(propertyId)
            }
        }

        for (const row of rows) {
            const propertyId = row.propertyId as number
            const id = String(propertyId)

            if (!(id in canonicalProperties)) {
                copyProperties[propertyId] = row.value.trim()
            }
        }

        try {
            setSaving(true)

            const updated = await updateCopy(
                copy.id,
                copyProperties,
                canonic.id,
                Number(selectedLocation),
                name.trim() || null,
                hiddenProperties
            )

            const refreshed = await getCopyById(updated.id)

            onSaved(refreshed)
            onClose()
        } catch (e) {
            console.error(e)
            setError(
                "Não foi possível salvar as alterações. Verifique a API e tente novamente."
            )
        } finally {
            setSaving(false)
        }
    }

    async function handleDeleteCopy() {
        setError("")

        try {
            setDeletingCopy(true)

            await deleteCopy(copy.id)

            setShowDeleteCopyModal(false)
            onDeleted(copy.id)
            onClose()
        } catch (e) {
            console.error(e)
            setError(
                "Não foi possível excluir a cópia. Verifique se ela está vinculada a alguma coleção e tente novamente."
            )
            setShowDeleteCopyModal(false)
        } finally {
            setDeletingCopy(false)
        }
    }

    if (loading) {
        return (
            <div className="edit-copy-overlay">
                <section
                    className="edit-copy-modal"
                    role="dialog"
                    aria-modal="true"
                    aria-labelledby="edit-copy-title"
                >
                    <header className="edit-copy-header">
                        <div>
                            <span className="edit-copy-eyebrow">
                                PERSONALIZAR EXEMPLAR
                            </span>
                            <h2 id="edit-copy-title">Editar item</h2>
                        </div>
                    </header>

                    <div className="edit-copy-content">
                        <p>Carregando dados...</p>
                    </div>
                </section>
            </div>
        )
    }

    return (
        <div
            className="edit-copy-overlay"
            onMouseDown={event => {
                if (event.target === event.currentTarget && !saving) {
                    onClose()
                }
            }}
        >
            <section
                className="edit-copy-modal"
                role="dialog"
                aria-modal="true"
                aria-labelledby="edit-copy-title"
            >
                <header className="edit-copy-header">
                    <div>
                        <span className="edit-copy-eyebrow">
                            PERSONALIZAR EXEMPLAR
                        </span>

                        <h2 id="edit-copy-title">Editar item</h2>
                    </div>
                    <button
                        type="button"
                        className="edit-copy-close"
                        onClick={onClose}
                        aria-label="Fechar"
                        disabled={saving}
                    >
                        ×
                    </button>
                </header>
                <div className="edit-copy-content">
                    {error && (
                        <p className="edit-copy-error" role="alert">
                            {error}
                        </p>
                    )}
                    <div className="edit-copy-field">
                        <label htmlFor="edit-copy-name">
                            Nome do item
                        </label>
                        <input
                            id="edit-copy-name"
                            value={name}
                            onChange={event => setName(event.target.value)}
                            placeholder={canonic.name}
                        />
                        <small>
                            Deixe vazio para usar o nome original: {canonic.name}
                        </small>
                    </div>
                    <div className="edit-copy-properties-header">
                        <label>Propriedades</label>
                        <button
                            type="button"
                            onClick={addRow}
                            className="edit-copy-secondary-button"
                        >
                            <i className="bi bi-plus" /> Adicionar
                        </button>
                    </div>
                    <div className="edit-copy-properties">
                        {rows.map((row, index) => (
                            <div
                                className="edit-copy-property-row"
                                key={`${row.propertyId ?? "new"}-${index}`}
                            >
                                <select
                                    aria-label="Propriedade"
                                    value={row.propertyId ?? ""}
                                    onChange={event =>
                                        updateRow(index, {
                                            propertyId: event.target.value
                                                ? Number(event.target.value)
                                                : null
                                        })
                                    }
                                >
                                    <option value="">
                                        Selecione uma propriedade
                                    </option>
                                    {properties.map(property => (
                                        <option
                                            key={property.id}
                                            value={property.id}
                                            disabled={rows.some(
                                                (other, otherIndex) =>
                                                    otherIndex !== index &&
                                                    other.propertyId === property.id
                                            )}
                                        >
                                            {property.name}
                                        </option>
                                    ))}
                                </select>
                                <input
                                    value={row.value}
                                    aria-label="Valor da propriedade"
                                    placeholder="Valor"
                                    onChange={event =>
                                        updateRow(index, {
                                            value: event.target.value
                                        })
                                    }
                                />
                                <button
                                    type="button"
                                    className="edit-copy-remove-button"
                                    onClick={() => removeRow(index)}
                                    aria-label="Remover propriedade"
                                    title="Remover propriedade"
                                >
                                    <i className="bi bi-trash" />
                                </button>
                            </div>
                        ))}
                    </div>
                    <button
                        type="button"
                        className="edit-copy-create-property"
                        onClick={() => setShowAddPropertyModal(true)}
                    >
                        Criar nova propriedade
                    </button>
                    <div className="edit-copy-field">
                        <label htmlFor="edit-copy-location">
                            Localização <span>*</span>
                        </label>
                        <select
                            id="edit-copy-location"
                            value={selectedLocation}
                            onChange={event =>
                                setSelectedLocation(event.target.value)
                            }
                            required
                        >
                            <option value="">
                                Selecione uma localização
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
                            className="edit-copy-create-property"
                            onClick={() => setShowAddLocationModal(true)}
                        >
                            Criar nova localização
                        </button>
                    </div>
                </div>
                <footer className="edit-copy-footer">
                    <button
                        type="button"
                        className="edit-copy-delete"
                        onClick={() => setShowDeleteCopyModal(true)}
                        disabled={saving || deletingCopy}
                    >
                        <i className="bi bi-trash" />
                        Excluir cópia
                    </button>
                    <button
                        type="button"
                        className="edit-copy-cancel"
                        onClick={onClose}
                        disabled={saving}
                    >
                        Cancelar
                    </button>
                    <button
                        type="button"
                        className="edit-copy-save"
                        onClick={handleSave}
                        disabled={saving}
                    >
                        {saving ? "Salvando..." : "Salvar alterações"}
                    </button>
                </footer>
            </section>
            {showAddPropertyModal && (
                <AddPropertyModal
                    properties={properties}
                    onClose={() => setShowAddPropertyModal(false)}
                    onCreated={handlePropertyCreated}
                />
            )}
            {showAddLocationModal && curatorId != null && (
                <AddLocationModal
                    locations={locations}
                    curatorId={curatorId}
                    onClose={() => setShowAddLocationModal(false)}
                    onCreated={handleLocationCreated}
                />
            )}
            <DeleteCopyModal
                isOpen={showDeleteCopyModal}
                onClose={() => setShowDeleteCopyModal(false)}
                onDelete={handleDeleteCopy}
                deleting={deletingCopy}
            />
        </div>
    )
}