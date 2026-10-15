import '../styles/CreateCurator.css'

import { useState, type SyntheticEvent } from 'react'
import { useNavigate } from 'react-router-dom'

import { requestRegistrationCode, type RegisterCuratorData } from '../services/curatorService'

import VerifyRegistrationModal from '../components/verifyRegistrationModal'

function CreateCurator() {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [name, setName] = useState('')
    const [gender, setGender] = useState<string | null>(null)
    const [phone, setPhone] = useState<string | null>(null)
    const [bio, setBio] = useState<string | null>(null)
    const [profilePicture, setProfilePicture] = useState<string | null>(null)
    const [isPublic, setIsPublic] = useState(true)

    const [showVerifyRegistrationModal, setShowVerifyRegistrationModal] = useState(false)
    const [registrationData, setRegistrationData] = useState<RegisterCuratorData | null>(null)

    const [submitting, setSubmitting] = useState(false)
    const [error, setError] = useState('')

    const navigate = useNavigate()

    async function submit(event: SyntheticEvent<HTMLFormElement>) {
        event.preventDefault()

        const data: RegisterCuratorData = {
            email: email.trim(),
            password,
            name: name.trim(),
            gender,
            phone,
            bio,
            isPublic,
            profilePicture,
            location: null
        }

        try {
            setSubmitting(true)
            setError('')

            await requestRegistrationCode(data)

            setRegistrationData(data)
            setShowVerifyRegistrationModal(true)
        } catch (error) {
            console.error(error)

            setError(
                error instanceof Error
                    ? error.message
                    : 'Não foi possível iniciar o cadastro.'
            )
        } finally {
            setSubmitting(false)
        }
    }

    function handleVerified() {
        setShowVerifyRegistrationModal(false)
        alert('Cadastro realizado com sucesso!')
        navigate('/login')
    }

    return (
        <main className="create-curator-page">
            <section className="create-curator-header">
                <h1>Criar conta</h1>
                <p>
                    Crie sua conta para organizar suas memórias e
                    artefatos culturais.
                </p>
            </section>
            <section className="create-curator-card">
                <form
                    onSubmit={submit}
                    className="create-curator-form"
                >
                    <div className="mb-3">
                        <label htmlFor="email" className="form-label">
                            E-mail *
                        </label>
                        <input
                            type="email"
                            className="form-control"
                            id="email"
                            value={email}
                            onChange={event =>
                                setEmail(event.target.value)
                            }
                            required
                            disabled={submitting}
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
                            onChange={event =>
                                setPassword(event.target.value)
                            }
                            required
                            disabled={submitting}
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
                            onChange={event =>
                                setName(event.target.value)
                            }
                            required
                            disabled={submitting}
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="gender" className="form-label">
                            Gênero
                        </label>
                        <select
                            className="form-select"
                            id="gender"
                            value={gender ?? ''}
                            onChange={event =>
                                setGender(
                                    event.target.value === ''
                                        ? null
                                        : event.target.value
                                )
                            }
                            disabled={submitting}
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
                            value={phone ?? ''}
                            onChange={event =>
                                setPhone(event.target.value || null)
                            }
                            disabled={submitting}
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="bio" className="form-label">
                            Bio
                        </label>
                        <textarea
                            className="form-control"
                            id="bio"
                            rows={3}
                            value={bio ?? ''}
                            onChange={event =>
                                setBio(event.target.value || null)
                            }
                            disabled={submitting}
                        />
                    </div>
                    <div className="mb-3">
                        <label
                            htmlFor="profilePicture"
                            className="form-label"
                        >
                            Foto de perfil
                        </label>
                        <input
                            type="url"
                            className="form-control"
                            id="profilePicture"
                            value={profilePicture ?? ''}
                            onChange={event =>
                                setProfilePicture(
                                    event.target.value || null
                                )
                            }
                            placeholder="https://exemplo.com/imagem.jpg"
                            disabled={submitting}
                        />
                        <div className="form-text">
                            Informe o caminho da imagem.
                        </div>
                    </div>
                    <div className="public-profile">
                        <span>Perfil público?</span>

                        <label className="switch">
                            <input
                                type="checkbox"
                                id="isPublic"
                                checked={isPublic}
                                onChange={event =>
                                    setIsPublic(event.target.checked)
                                }
                                disabled={submitting}
                            />
                            <span className="slider"></span>
                        </label>
                    </div>
                    {error && (
                        <p role="alert" className="create-curator-error">
                            {error}
                        </p>
                    )}
                    <button
                        type="submit"
                        className="create-curator-button"
                        disabled={submitting}
                    >
                        {submitting
                            ? 'Enviando código...'
                            : 'Criar conta'}
                    </button>
                </form>
            </section>
            <div className="create-curator-login">
                <span>Já tem uma conta?</span>
                <button
                    type="button"
                    onClick={() => navigate('/login')}
                >
                    Fazer login
                </button>
            </div>
            {showVerifyRegistrationModal && registrationData && (
                <VerifyRegistrationModal
                    registrationData={registrationData}
                    onClose={() =>
                        setShowVerifyRegistrationModal(false)
                    }
                    onVerified={handleVerified}
                />
            )}
        </main>
    )
}

export default CreateCurator
