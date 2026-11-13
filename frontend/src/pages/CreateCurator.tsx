import '../styles/CreateCurator.css'

import { useState, type SyntheticEvent } from 'react'
import { useNavigate } from 'react-router-dom'
import { useNotification } from '../context/NotificationContext'

import { requestRegistrationCode, type RegisterCuratorData } from '../services/curatorService'

import VerifyRegistrationModal from '../components/VerifyRegistrationModal.tsx'

import { formatPhone, validatePhone, validateEmail } from '../utils/formatters'

function CreateCurator() {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [name, setName] = useState('')
    const [gender, setGender] = useState<string | null>(null)
    const [phone, setPhone] = useState<string | null>(null)
    const [bio, setBio] = useState<string | null>(null)

    const [showVerifyRegistrationModal, setShowVerifyRegistrationModal] = useState(false)
    const [registrationData, setRegistrationData] = useState<RegisterCuratorData | null>(null)

    const [submitting, setSubmitting] = useState(false)
    const [error, setError] = useState('')

    const { showNotification } = useNotification()
    const navigate = useNavigate()

    const profilePicture = null

    const [confirmPassword, setConfirmPassword] = useState('')


    async function submit(event: SyntheticEvent<HTMLFormElement>) {
        event.preventDefault()

        const normalizedEmail = email.trim()

        if (!validateEmail(normalizedEmail)) {
            setError('Informe um e-mail válido.')
            return
        }

        const requirements = validatePassword(password)
        const passwordIsValid = Object.values(requirements).every(Boolean)

        if (!passwordIsValid) {
            setError('A senha não atende a todos os requisitos.')
            return
        }

        if (password !== confirmPassword) {
            setError('As senhas não coincidem.')
            return
        }

        if (!validatePhone(phone ?? '')) {
            setError('Informe um telefone válido com DDD.')
            return
        }

        const data: RegisterCuratorData = {
            email: normalizedEmail,
            password,
            name: name.trim(),
            gender,
            phone: phone?.trim() || null,
            bio,
            isPublic: true,
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
        showNotification('Cadastro realizado com sucesso!', 'success')
        navigate('/login')
    }

    function validatePassword(password: string) {
        return {
            minLength: password.length >= 8,
            uppercase: /[A-Z]/.test(password),
            lowercase: /[a-z]/.test(password),
            number: /[0-9]/.test(password),
            special: /[^A-Za-z0-9\s]/.test(password)
        }
    }

    const passwordRequirements = validatePassword(password)

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
                            placeholder="exemplo@email.com"
                            autoComplete="email"
                            value={email}
                            onChange={event => setEmail(event.target.value)}
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
                            onChange={event => setPassword(event.target.value)}
                            minLength={8}
                            required
                            disabled={submitting}
                        />
                        <div className="password-requirements mt-2">
                            <small className={passwordRequirements.minLength ? 'valid' : ''}>
                                {passwordRequirements.minLength ? '✓' : '○'} Pelo menos 8 caracteres
                            </small>
                            <small className={passwordRequirements.uppercase ? 'valid' : ''}>
                                {passwordRequirements.uppercase ? '✓' : '○'} Uma letra maiúscula
                            </small>
                            <small className={passwordRequirements.lowercase ? 'valid' : ''}>
                                {passwordRequirements.lowercase ? '✓' : '○'} Uma letra minúscula
                            </small>
                            <small className={passwordRequirements.number ? 'valid' : ''}>
                                {passwordRequirements.number ? '✓' : '○'} Um número
                            </small>
                            <small className={passwordRequirements.special ? 'valid' : ''}>
                                {passwordRequirements.special ? '✓' : '○'} Um caractere especial
                            </small>
                        </div>
                    </div>
                    <div className="mb-3">
                        <label htmlFor="confirmPassword" className="form-label">
                            Confirmar senha *
                        </label>
                        <input
                            type="password"
                            className="form-control"
                            id="confirmPassword"
                            value={confirmPassword}
                            onChange={event => setConfirmPassword(event.target.value)}
                            required
                            disabled={submitting}
                        />
                        {confirmPassword && (
                            <small className={
                                password === confirmPassword
                                    ? 'password-match'
                                    : 'password-mismatch'
                            }>
                                {password === confirmPassword
                                    ? '✓ As senhas coincidem'
                                    : '✕ As senhas não coincidem'}
                            </small>
                        )}
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
                            placeholder="(11) 99999-9999"
                            value={phone ?? ''}
                            onChange={event =>
                                setPhone(formatPhone(event.target.value) || null)
                            }
                            maxLength={15}
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
