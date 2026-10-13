
import { useState } from 'react'

import { createLocation, type Location } from '../services/locationService'

import '../styles/AddLocationModal.css'

interface AddLocationModalProps {
    locations: Location[]
    curatorId: number
    onClose: () => void
    onCreated: (location: Location) => void
}

function AddLocationModal({
    locations,
    curatorId,
    onClose,
    onCreated
}: AddLocationModalProps) {
    const [name, setName] = useState('')
    const [latitude, setLatitude] = useState('')
    const [longitude, setLongitude] = useState('')
    const [parentLocation, setParentLocation] = useState<number | null>(null)

    const [saving, setSaving] = useState(false)
    const [error, setError] = useState('')

    async function handleCreate() {
        const normalizedName = name.trim()
        const parsedLatitude = Number(latitude)
        const parsedLongitude = Number(longitude)

        if (!normalizedName) {
            setError('Informe o nome da localização.')
            return
        }

        if (
            latitude.trim() === '' ||
            longitude.trim() === '' ||
            !Number.isFinite(parsedLatitude) ||
            !Number.isFinite(parsedLongitude)
        ) {
            setError('Informe uma latitude e longitude válidas.')
            return
        }

        if (
            parsedLatitude < -90 ||
            parsedLatitude > 90
        ) {
            setError('A latitude deve estar entre -90 e 90.')
            return
        }

        if (
            parsedLongitude < -180 ||
            parsedLongitude > 180
        ) {
            setError('A longitude deve estar entre -180 e 180.')
            return
        }

        const alreadyExists = locations.some(
            location =>
                location.name.trim().toLowerCase() ===
                normalizedName.toLowerCase()
        )

        if (alreadyExists) {
            setError(
                'Você já possui uma localização com esse nome.'
            )
            return
        }

        try {
            setSaving(true)
            setError('')
            const createdLocation = await createLocation(
                normalizedName,
                parsedLatitude,
                parsedLongitude,
                parentLocation ?? undefined,
                curatorId
            )
            onCreated(createdLocation)
        } catch (error) {
            console.error(error)
            setError(
                'Não foi possível cadastrar a localização.'
            )
        } finally {
            setSaving(false)
        }
    }

    return (
        <div
            className="add-location-overlay"
            onMouseDown={event => {
                if (
                    event.target === event.currentTarget &&
                    !saving
                ) {
                    onClose()
                }
            }}
        >
            <div
                className="add-location-modal"
                role="dialog"
                aria-modal="true"
                aria-labelledby="add-location-title"
            >
                <div className="add-location-header">
                    <div>
                        <span className="section-label">
                            NOVA LOCALIZAÇÃO
                        </span>
                        <h2 id="add-location-title">
                            Adicionar localização
                        </h2>
                    </div>
                    <button
                        type="button"
                        className="add-location-close"
                        onClick={onClose}
                        disabled={saving}
                        aria-label="Fechar modal"
                    >
                        <i className="bi bi-x-lg"></i>
                    </button>
                </div>
                <div className="add-location-body">
                    <p className="add-location-description">
                        Cadastre um local físico para organizar
                        onde seus itens ficam guardados.
                    </p>
                    <div className="add-location-field">
                        <label htmlFor="location-name">
                            Nome da localização
                        </label>
                        <input
                            id="location-name"
                            type="text"
                            value={name}
                            onChange={event =>
                                setName(event.target.value)
                            }
                            placeholder="Ex.: Estante da sala"
                            maxLength={100}
                            autoFocus
                            disabled={saving}
                        />
                    </div>
                    <div className="add-location-coordinates">
                        <div className="add-location-field">
                            <label htmlFor="location-latitude">
                                Latitude
                            </label>
                            <input
                                id="location-latitude"
                                type="number"
                                step="any"
                                min="-90"
                                max="90"
                                value={latitude}
                                onChange={event =>
                                    setLatitude(event.target.value)
                                }
                                placeholder="Ex.: -23.5505"
                                disabled={saving}
                            />
                        </div>
                        <div className="add-location-field">
                            <label htmlFor="location-longitude">
                                Longitude
                            </label>
                            <input
                                id="location-longitude"
                                type="number"
                                step="any"
                                min="-180"
                                max="180"
                                value={longitude}
                                onChange={event =>
                                    setLongitude(event.target.value)
                                }
                                placeholder="Ex.: -46.6333"
                                disabled={saving}
                            />
                        </div>
                    </div>
                    <div className="add-location-field">
                        <label htmlFor="parent-location">
                            Localização filha (opcional)
                        </label>
                        <select
                            id="parent-location"
                            value={parentLocation ?? ''}
                            onChange={event =>
                                setParentLocation(
                                    event.target.value
                                        ? Number(event.target.value)
                                        : null
                                )
                            }
                            disabled={saving}
                        >
                            <option value="">
                                Nenhuma localização filha
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
                        <small>
                            Use este campo para representar, por
                            exemplo, uma caixa dentro de uma estante.
                        </small>
                    </div>
                    {error && (
                        <p className="add-location-error" role="alert">
                            {error}
                        </p>
                    )}
                </div>
                <div className="add-location-actions">
                    <button
                        type="button"
                        className="add-location-cancel"
                        onClick={onClose}
                        disabled={saving}
                    >
                        Cancelar
                    </button>
                    <button
                        type="button"
                        className="add-location-save"
                        onClick={handleCreate}
                        disabled={saving}
                    >
                        {saving
                            ? 'Cadastrando...'
                            : 'Cadastrar localização'}
                    </button>
                </div>
            </div>
        </div>
    )
}

export default AddLocationModal
