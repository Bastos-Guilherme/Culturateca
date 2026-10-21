
import { useState, type SyntheticEvent } from 'react'
import { Link, useNavigate } from 'react-router-dom'

import { authenticate, type LoginMfaResponse } from '../services/loginService'
import { useAuth } from '../context/AuthContext'
import VerifyLoginModal from '../components/VerifyLoginModal'
import PasswordRecoveryModal from '../components/PasswordRecoveryModal'

import '../styles/Login.css'

function Login() {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [challengeId, setChallengeId] = useState<string | null>(null)
    const [showVerification, setShowVerification] = useState(false)
    const [submitting, setSubmitting] = useState(false)
    const [showPasswordRecovery, setShowPasswordRecovery] = useState(false)

    const { login } = useAuth()
    const navigate = useNavigate()

    async function submitLogin(event: SyntheticEvent<HTMLFormElement>) {
        event.preventDefault()

        try {
            setSubmitting(true)

            const response = await authenticate(email, password)

            if (typeof response === 'string') {
                login(response)

                setEmail('')
                setPassword('')

                alert('Login realizado com sucesso!')
                navigate('/')
                return
            }

            const mfaResponse = response as LoginMfaResponse

            if (mfaResponse.mfaRequired) {
                setChallengeId(mfaResponse.challengeId)
                setShowVerification(true)
            }
        } catch (error) {
            console.error(error)

            alert(
                error instanceof Error
                    ? error.message
                    : 'Erro ao realizar o login.'
            )
        } finally {
            setSubmitting(false)
        }
    }

    function handleVerified(token: string) {
        login(token)
        setShowVerification(false)
        setChallengeId(null)
        setEmail('')
        setPassword('')

        alert('Login realizado com sucesso!')
        navigate('/')
    }

    function handleCloseVerification() {
        setShowVerification(false)
        setChallengeId(null)
    }

    return (
        <main className="login-page">
            <section className="login-header">
                <h1>Login</h1>
                <p>Seu arquivo digital de memórias e artefatos culturais.</p>
            </section>

            <section className="login-card">
                <form onSubmit={submitLogin} className="login-form">
                    <div className="login-field">
                        <label htmlFor="email">
                            E-mail
                        </label>

                        <input
                            type="email"
                            id="email"
                            placeholder="exemplo@email.com"
                            value={email}
                            onChange={(event) => setEmail(event.target.value)}
                            required
                            disabled={submitting}
                        />
                    </div>

                    <div className="login-field">
                        <div className="login-password-header">
                            <label htmlFor="password">
                                Senha
                            </label>
                        </div>

                        <input
                            type="password"
                            id="password"
                            value={password}
                            onChange={(event) => setPassword(event.target.value)}
                            required
                            disabled={submitting}
                        />

                        <div className="login-password-bottom">
                            <button
                                type="button"
                                className="forgot-password"
                                onClick={() => setShowPasswordRecovery(true)}
                                disabled={submitting}
                            >
                                Esqueci minha senha
                            </button>
                        </div>
                    </div>

                    <button
                        type="submit"
                        className="login-button"
                        disabled={submitting}
                    >
                        {submitting ? 'AGUARDE...' : 'ENTRAR'}
                        <i className="bi bi-arrow-right"></i>
                    </button>
                </form>
            </section>

            <div className="login-register">
                <span>Não tem uma conta?</span>

                <Link to="/createCurator">
                    Criar nova conta
                </Link>
            </div>
            {showVerification && challengeId && (
                <VerifyLoginModal
                    challengeId={challengeId}
                    onClose={handleCloseVerification}
                    onVerified={handleVerified}
                />
            )}
            {showPasswordRecovery && (
                <PasswordRecoveryModal
                    initialEmail={email}
                    onClose={() => setShowPasswordRecovery(false)}
                    onSuccess={() => {
                        setShowPasswordRecovery(false)
                        setPassword('')
                        alert('Senha redefinida com sucesso! Faça login com sua nova senha.')
                    }}
                />
            )}
        </main>
    )
}

export default Login