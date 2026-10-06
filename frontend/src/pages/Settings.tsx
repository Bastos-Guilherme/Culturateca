import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'

import { getCurrentCurator, updateCurator, deleteCurator, type Curator, type UpdateCuratorData } from '../services/curatorService'
import { getLocationById, type Location } from '../services/locationService'
import { useAuth } from '../context/AuthContext'

import '../styles/Settings.css'
import DeleteCuratorModal from '../components/DeleteCuratorModal'

export default function Settings() {

    const navigate = useNavigate()
    const { logout } = useAuth()

    const [curator, setCurator] = useState<Curator | null>(null)
    const [location, setLocation] = useState<Location | null>(null)

    const [loading, setLoading] = useState(true)
    const [editing, setEditing] = useState(false)
    const [saving, setSaving] = useState(false)

    const [showDeleteModal, setShowDeleteModal] = useState(false)
    const [deleting, setDeleting] = useState(false)

    const [formData, setFormData] = useState({
        email: '',
        name: '',
        gender: '',
        phone: '',
        isPublic: true,
        bio: '',
        profilePicture: '',
        locationId: null as number | null
    })

    const [originalData, setOriginalData] = useState({
        email: '',
        name: '',
        gender: '',
        phone: '',
        isPublic: true,
        bio: '',
        profilePicture: '',
        locationId: null as number | null
    })

    useEffect(() => {
        loadCurator()
    }, [])

    async function loadCurator() {
        try {
            setLoading(true)

            const data = await getCurrentCurator()

            setCurator(data)

            const dataForm = {
                email: data.email,
                name: data.name ?? '',
                gender: data.gender ?? '',
                phone: data.phone ?? '',
                isPublic: data.isPublic ?? true,
                bio: data.bio ?? '',
                profilePicture: data.profilePicture ?? '',
                locationId: data.location?.id ?? null
            }

            setFormData(dataForm)
            setOriginalData(dataForm)

            if (data.location?.id) {
                try {
                    const locationData = await getLocationById(data.location.id)
                    setLocation(locationData)
                } catch {
                    setLocation(null)
                }
            }

        } catch (error) {
            console.error('Erro ao carregar conta:', error)
        } finally {
            setLoading(false)
        }
    }

    function handleChange(
        field: keyof typeof formData,
        value: string | boolean | number | null
    ) {
        setFormData(previous => ({
            ...previous,
            [field]: value
        }))
    }

    const hasChanges =
        formData.email !== originalData.email ||
        formData.name !== originalData.name ||
        formData.gender !== originalData.gender ||
        formData.phone !== originalData.phone ||
        formData.isPublic !== originalData.isPublic ||
        formData.bio !== originalData.bio ||
        formData.profilePicture !== originalData.profilePicture ||
        formData.locationId !== originalData.locationId

    async function handleSave() {

        if (!hasChanges || !curator) {
            return
        }

        try {
            setSaving(true)

            const changes: UpdateCuratorData = {}

            if (formData.email !== originalData.email) {
                changes.email = formData.email
            }

            if (formData.name !== originalData.name) {
                changes.name = formData.name
            }

            if (formData.gender !== originalData.gender) {
                changes.gender =
                    formData.gender === ''
                        ? null
                        : formData.gender
            }

            if (formData.phone !== originalData.phone) {
                changes.phone =
                    formData.phone === ''
                        ? null
                        : formData.phone
            }

            if (formData.isPublic !== originalData.isPublic) {
                changes.isPublic = formData.isPublic
            }

            if (formData.bio !== originalData.bio) {
                changes.bio =
                    formData.bio === ''
                        ? null
                        : formData.bio
            }

            if (formData.profilePicture !== originalData.profilePicture) {
                changes.profilePicture =
                    formData.profilePicture === ''
                        ? null
                        : formData.profilePicture
            }

            if (formData.locationId !== originalData.locationId) {
                changes.location =
                    formData.locationId !== null
                        ? { id: formData.locationId }
                        : null
            }

            const emailChanged = formData.email !== curator.email

            await updateCurator(
                curator.id,
                changes
            )

            if (emailChanged) {
                logout()
                navigate('/login')
                return
            }

            loadCurator()
            setEditing(false)

        } catch (error) {
            console.error('Erro ao atualizar conta:', error)
            alert('Não foi possível atualizar a conta.')
        } finally {
            setSaving(false)
        }
    }

    function handleCancel() {
        setFormData(originalData)
        setEditing(false)
    }

    async function handleDelete() {
        if (!curator) {
            return
        }

        try {
            setDeleting(true)

            await deleteCurator(curator.id)

            logout()
            navigate('/login')
        } catch (error) {
            console.error('Erro ao excluir conta:', error)
            alert('Não foi possível excluir a conta.')
            setDeleting(false)
        }
    }

    if (loading) {
        return (
            <div className="settings-page">
                <div className="settings-loading">
                    Carregando configurações...
                </div>
            </div>
        )
    }

    if (!curator) {
        return (
            <div className="settings-page">
                <div className="settings-loading">
                    Não foi possível carregar os dados da conta.
                </div>
            </div>
        )
    }

    return (
        <div className="settings-page">
            <div className="settings-header">
                <div>
                    <span className="section-label">
                        CONTA
                    </span>
                    <h1>
                        Configurações
                    </h1>
                    <p>
                        Gerencie as informações da sua conta.
                    </p>
                </div>

                {!editing && (
                    <button
                        className="settings-edit-button"
                        onClick={() => setEditing(true)}
                    >
                        <i className="bi bi-pencil"></i>
                        Editar
                    </button>
                )}
            </div>

            <div className="settings-content">
                <section className="settings-section">
                    <div className="settings-section-header">
                        <div>
                            <span className="section-label">
                                INFORMAÇÕES
                            </span>
                            <h2>
                                Dados da conta
                            </h2>
                        </div>
                    </div>

                    <div className="settings-fields">
                        <div className="settings-field">
                            <label>
                                E-mail
                            </label>

                            {editing ? (
                                <input
                                    type="email"
                                    value={formData.email}
                                    onChange={event =>
                                        handleChange('email', event.target.value)
                                    }
                                />
                            ) : (
                                <div className="settings-value">
                                    {curator.email}
                                </div>
                            )}
                        </div>

                        <div className="settings-field">
                            <label>
                                Nome
                            </label>

                            {editing ? (
                                <input
                                    type="text"
                                    value={formData.name}
                                    onChange={event =>
                                        handleChange('name', event.target.value)
                                    }
                                />
                            ) : (
                                <div className="settings-value">
                                    {curator.name || 'Não informado'}
                                </div>
                            )}
                        </div>

                        <div className="settings-field">
                            <label>
                                Gênero
                            </label>

                            {editing ? (
                                <select
                                    value={formData.gender}
                                    onChange={event =>
                                        handleChange('gender', event.target.value)
                                    }
                                >
                                    <option value="">
                                        Não informado
                                    </option>
                                    <option value="MALE">
                                        Masculino
                                    </option>
                                    <option value="FEMALE">
                                        Feminino
                                    </option>
                                </select>
                            ) : (
                                <div className="settings-value">
                                    {curator.gender === 'MALE'
                                        ? 'Masculino'
                                        : curator.gender === 'FEMALE'
                                            ? 'Feminino'
                                            : 'Não informado'}
                                </div>
                            )}
                        </div>

                        <div className="settings-field">
                            <label>
                                Telefone
                            </label>

                            {editing ? (
                                <input
                                    type="text"
                                    value={formData.phone}
                                    onChange={event =>
                                        handleChange('phone', event.target.value)
                                    }
                                />
                            ) : (
                                <div className="settings-value">
                                    {curator.phone || 'Não informado'}
                                </div>
                            )}
                        </div>

                        <div className="settings-field settings-field-full">
                            <label>Perfil público?</label>

                            {editing ? (
                                <label className="switch">
                                    <input
                                        type="checkbox"
                                        checked={formData.isPublic}
                                        onChange={event =>
                                            handleChange(
                                                'isPublic',
                                                event.target.checked
                                            )
                                        }
                                    />
                                    <span className="slider"></span>
                                </label>
                            ) : (
                                <div className="settings-value">
                                    {curator.isPublic ? 'Sim' : 'Não'}
                                </div>
                            )}
                        </div>

                        <div className="settings-field settings-field-full">
                            <label>
                                Biografia
                            </label>

                            {editing ? (
                                <textarea
                                    value={formData.bio}
                                    onChange={event =>
                                        handleChange('bio', event.target.value)
                                    }
                                    rows={4}
                                    placeholder="Escreva algo sobre você..."
                                />
                            ) : (
                                <div className="settings-value settings-bio">
                                    {curator.bio || 'Não informado'}
                                </div>
                            )}
                        </div>

                        <div className="settings-field settings-field-full">
                            <label>
                                Foto de perfil
                            </label>

                            {editing ? (
                                <input
                                    type="text"
                                    value={formData.profilePicture}
                                    onChange={event =>
                                        handleChange(
                                            'profilePicture',
                                            event.target.value
                                        )
                                    }
                                    placeholder="URL da imagem"
                                />
                            ) : (
                                <div className="settings-value">
                                    {curator.profilePicture
                                        ? curator.profilePicture
                                        : 'Não informado'}
                                </div>
                            )}
                        </div>

                        <div className="settings-field settings-field-full">
                            <label>
                                Localização
                            </label>

                            {editing ? (
                                <input
                                    type="number"
                                    value={formData.locationId ?? ''}
                                    onChange={event =>
                                        handleChange(
                                            'locationId',
                                            event.target.value === ''
                                                ? null
                                                : Number(event.target.value)
                                        )
                                    }
                                    placeholder="ID da localização"
                                />
                            ) : (
                                <div className="settings-value">
                                    {location
                                        ? location.name
                                        : curator.location
                                            ? `Localização #${curator.location.id}`
                                            : 'Não informado'}
                                </div>
                            )}
                        </div>
                    </div>
                </section>

                {editing && (
                    <div className="settings-actions">
                        <button
                            className="settings-cancel-button"
                            onClick={handleCancel}
                        >
                            Cancelar
                        </button>

                        <button
                            className="settings-save-button"
                            disabled={!hasChanges || saving}
                            onClick={handleSave}
                        >
                            {saving ? (
                                'Salvando...'
                            ) : (
                                <>
                                    <i className="bi bi-check-lg"></i>
                                    Aplicar mudanças
                                </>
                            )}
                        </button>
                    </div>
                )}

                <section className="settings-danger">
                    <div>
                        <span className="section-label">
                            ZONA DE PERIGO
                        </span>
                        <h2>
                            Excluir conta
                        </h2>
                        <p>
                            Essa ação excluirá permanentemente sua conta e
                            os dados associados a ela.
                        </p>
                    </div>

                    <button
                        className="settings-delete-button"
                        onClick={() => setShowDeleteModal(true)}
                    >
                        <i className="bi bi-trash"></i>
                        Excluir conta
                    </button>
                </section>
            </div>

            <DeleteCuratorModal
                isOpen={showDeleteModal}
                onClose={() => setShowDeleteModal(false)}
                onDelete={handleDelete}
            />
            {deleting && <span className="deleting-text">Saindo...</span>}
        </div>
    )
}