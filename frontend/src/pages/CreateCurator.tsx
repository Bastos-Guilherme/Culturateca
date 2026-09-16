import '../styles/CreateCurator.css'
import { useEffect, useState, type SyntheticEvent } from 'react'
import { useNavigate } from 'react-router-dom'

import { createCurator } from '../services/curatorService'

import { getAllLocation, createLocation, type Location } from '../services/locationService'

function CreateCurator() {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [name, setName] = useState('')
    const [gender, setGender] = useState<string | null>(null)
    const [phone, setPhone] = useState('')
    const [locationId, setLocationId] = useState('')
    const [bio, setBio] = useState('')
    const [profilePicture, setProfilePicture] = useState('')
    const [isPublic, setIsPublic] = useState(true)
    const following: string[] = []

    const [locations, setLocations] = useState<Location[]>([])

    const [locationModalOpen, setLocationModalOpen] = useState(false)
    const [newLocationName, setNewLocationName] = useState('')
    const [newLocationLatitude, setNewLocationLatitude] = useState('')
    const [newLocationLongitude, setNewLocationLongitude] = useState('')
    const [newLocationChildId, setNewLocationChildId] = useState('')

    const navigate = useNavigate()

    useEffect(() => {
        async function loadLocations() {
            try {
                const data = await getAllLocation()
                setLocations(data)
            } catch (error) {
                console.error(error)
                alert('Erro ao carregar localizações')
            }
        }

        loadLocations()
    }, [])

    async function submit(event: SyntheticEvent<HTMLFormElement>) {
        event.preventDefault()

        try {
            await createCurator(
                email,
                password,
                name,
                gender,
                phone,
                isPublic,
                bio,
                following,
                profilePicture,
                locationId === ''
                    ? null
                    : Number(locationId)
            )

            alert('Cadastro realizado com sucesso!')
            navigate('/login')
        } catch (error) {
            console.error(error)
            alert('Erro ao realizar cadastro')
        }
    }

    async function submitLocation(event: SyntheticEvent<HTMLFormElement>) {
        event.preventDefault()

        try {
            const location = await createLocation(
                newLocationName,
                Number(newLocationLatitude),
                Number(newLocationLongitude),
                Number(newLocationChildId)
            )

            setLocations((currentLocations) => [
                ...currentLocations,
                location
            ])

            setLocationId(String(location.id))

            setNewLocationName('')
            setNewLocationLatitude('')
            setNewLocationLongitude('')
            setNewLocationChildId('')
            setLocationModalOpen(false)

            alert('Localização cadastrada com sucesso!')

        } catch (error) {
            console.error(error)
            alert('Erro ao cadastrar localização')
        }
    }

    return (
        <div className="container mt-5">
            <h1>Criar conta</h1>

            <form onSubmit={submit}>
                <div className="mb-3">
                    <label htmlFor="email" className="form-label">
                        E-mail *
                    </label>
                    <input
                        type="email"
                        className="form-control"
                        id="email"
                        value={email}
                        onChange={(event) =>
                            setEmail(event.target.value)
                        }
                        required
                    />
                </div>

                <div className="mb-3">
                    <label htmlFor="password" className="form-label">
                        Senha *
                    </label>
                    <input
                        type="password"
                        className="form-control"
                        id="password"
                        value={password}
                        onChange={(event) =>
                            setPassword(event.target.value)
                        }
                        required
                    />
                </div>

                <div className="mb-3">
                    <label htmlFor="name" className="form-label">
                        Nome *
                    </label>
                    <input
                        type="text"
                        className="form-control"
                        id="name"
                        value={name}
                        onChange={(event) =>
                            setName(event.target.value)
                        }
                        required
                    />
                </div>

                <div className="mb-3">
                    <label htmlFor="gender" className="form-label">
                        Gênero
                    </label>
                    <select
                        className="form-select"
                        id="gender"
                        value={gender ?? ""}
                        onChange={(event) =>
                            setGender(event.target.value === "" ? null : event.target.value)
                        }
                    >
                        <option value="">
                            Prefiro não informar
                        </option>
                        <option value="MALE">
                            Masculino
                        </option>
                        <option value="FEMALE">
                            Feminino
                        </option>
                    </select>
                </div>

                <div className="mb-3">
                    <label htmlFor="phone" className="form-label">
                        Telefone
                    </label>
                    <input
                        type="tel"
                        className="form-control"
                        id="phone"
                        value={phone}
                        onChange={(event) =>
                            setPhone(event.target.value)
                        }
                    />
                </div>

                <div className="mb-3">
                    <label htmlFor="location" className="form-label">
                        Localização
                    </label>

                    <div className="d-flex justify-content-center align-items-center">
                        <select
                            className="form-select"
                            id="location"
                            value={locationId}
                            onChange={(event) =>
                                setLocationId(event.target.value)
                            }
                        >
                            <option value="">
                                Nenhuma localização
                            </option>

                            {locations.map((location) => (
                                <option
                                    key={location.id}
                                    value={location.id}
                                >
                                    {location.name}
                                </option>
                            ))}
                        </select>

                        <button type="button" className="btn btn-secondary ms-2" 
                            onClick={() => setLocationModalOpen(true)}
                        > 
                            <i className="bi bi-plus-lg"></i> Nova localização 
                        </button>
                    </div>
                </div>

                <div className="mb-3">
                    <label htmlFor="bio" className="form-label">
                        Biografia
                    </label>
                    <textarea
                        className="form-control"
                        id="bio"
                        rows={3}
                        value={bio}
                        onChange={(event) =>
                            setBio(event.target.value)
                        }
                    />
                </div>

                <div className="mb-3">
                    <label htmlFor="profilePicture" className="form-label">
                        Foto de perfil
                    </label>
                    <input
                        type="url"
                        className="form-control"
                        id="profilePicture"
                        value={profilePicture}
                        onChange={(event) =>
                            setProfilePicture(event.target.value)
                        }
                        placeholder="https://exemplo.com/imagem.jpg"
                    />
                    <div className="form-text">
                        informe o caminho da imagem.
                    </div>
                </div>

                <div className="form-check mb-3">
                    <input
                        type="checkbox"
                        className="form-check-input"
                        id="isPublic"
                        checked={isPublic}
                        onChange={(event) =>
                            setIsPublic(event.target.checked)
                        }
                    />
                    <label htmlFor="isPublic" className="form-check-label">
                        Perfil público?
                    </label>
                </div>

                <button type="submit" className="btn btn-primary">
                    Criar conta
                </button>
            </form>

            {locationModalOpen && (
                <div className="location-overlay">
                    <div className="location-modal">

                        <div className="location-modal-header">
                            <h3>Nova localização</h3>

                            <button type="button" className="location-close"
                                onClick={() => setLocationModalOpen(false)}
                            >
                                <i className="bi bi-x-lg"></i>
                            </button>
                        </div>

                        <form onSubmit={submitLocation}>
                            <div className="location-modal-body">
                                <div className="mb-3">
                                    <label className="form-label">
                                        Nome *
                                    </label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        value={newLocationName}
                                        onChange={(e) =>
                                            setNewLocationName(e.target.value)
                                        }
                                        required
                                    />
                                </div>
                                <div className="mb-3">
                                    <label className="form-label">
                                        Localização Filha
                                    </label>
                                    <select
                                        className="form-select"
                                        value={newLocationChildId}
                                        onChange={(e) =>
                                            setNewLocationChildId(e.target.value)
                                        }
                                    >
                                        <option value="">
                                            Nenhuma localização
                                        </option>
                                        {locations.map((location) => (
                                            <option
                                                key={location.id}
                                                value={location.id}
                                            >
                                                {location.name}
                                            </option>
                                        ))}
                                    </select>
                                </div>
                                <div className="row">
                                    <div className="col-md-6 mb-3">
                                        <label className="form-label">
                                            Latitude *
                                        </label>
                                        <input
                                            type="number"
                                            step="any"
                                            className="form-control"
                                            value={newLocationLatitude}
                                            onChange={(e) =>
                                                setNewLocationLatitude(e.target.value)
                                            }
                                            required
                                        />
                                    </div>

                                    <div className="col-md-6 mb-3">
                                        <label className="form-label">
                                            Longitude *
                                        </label>
                                        <input
                                            type="number"
                                            step="any"
                                            className="form-control"
                                            value={newLocationLongitude}
                                            onChange={(e) =>
                                                setNewLocationLongitude(e.target.value)
                                            }
                                            required
                                        />
                                    </div>
                                </div>
                            </div>

                            <div className="location-modal-footer">
                                <button type="button" className="btn btn-secondary"
                                    onClick={() => setLocationModalOpen(false)}
                                >
                                    Cancelar
                                </button>
                                <button type="submit" className="btn btn-primary">
                                    Criar localização
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    )
}

export default CreateCurator
