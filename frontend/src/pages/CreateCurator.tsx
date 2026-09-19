import '../styles/CreateCurator.css'
import { useNavigate } from 'react-router-dom'

import { createCurator } from '../services/curatorService'
import { useState, type SyntheticEvent } from 'react'

function CreateCurator() {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [name, setName] = useState('')
    const [gender, setGender] = useState<string | null>(null)
    const [phone, setPhone] = useState<string | null>(null)
    const locationId = null
    const [bio, setBio] = useState<string | null>(null)
    const [profilePicture, setProfilePicture] = useState<string | null>(null)
    const [isPublic, setIsPublic] = useState(true)
    const following: string[] = []

    const navigate = useNavigate()

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
                        value={phone ?? ""}
                        onChange={(event) =>
                            setPhone(event.target.value || null)
                        }
                    />
                </div>

                <div className="mb-3">
                    <label htmlFor="bio" className="form-label">
                        Biografia
                    </label>
                    <textarea
                        className="form-control"
                        id="bio"
                        rows={3}
                        value={bio ?? ""}
                        onChange={(event) =>
                            setBio(event.target.value || null)
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
                        value={profilePicture ?? ""}
                        onChange={(event) =>
                            setProfilePicture(event.target.value || null)
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
        </div>
    )
}

export default CreateCurator
