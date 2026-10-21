
import { useState, type SyntheticEvent } from 'react'
import { requestPasswordRecoveryCode, verifyPasswordRecoveryCode, resetPassword } from '../services/passwordRecoveryService'
import '../styles/PasswordRecoveryModal.css'

interface Props {
    initialEmail: string
    onClose: () => void
    onSuccess: () => void
}

type Step = 'email' | 'code' | 'password'

function PasswordRecoveryModal({
    initialEmail,
    onClose,
    onSuccess
}: Props) {
    const [step, setStep] = useState<Step>('email')
    const [email, setEmail] = useState(initialEmail)
    const [code, setCode] = useState('')
    const [challengeId, setChallengeId] = useState('')
    const [newPassword, setNewPassword] = useState('')
    const [confirmPassword, setConfirmPassword] = useState('')
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState('')
    const [message, setMessage] = useState('')

    async function handleRequestCode(event: SyntheticEvent<HTMLFormElement>) {
        event.preventDefault()
        setError('')
        setMessage('')
        setLoading(true)

        try {
            const response = await requestPasswordRecoveryCode(email)
            setChallengeId(response.challengeId)
            setStep('code')
            setMessage(response.message)
        } catch (err) {
            setError(
                err instanceof Error
                    ? err.message
                    : 'Não foi possível solicitar o código.'
            )
        } finally {
            setLoading(false)
        }
    }

    async function handleVerifyCode(event: SyntheticEvent<HTMLFormElement>) {
        event.preventDefault()
        setError('')
        setMessage('')
        setLoading(true)

        try {
            await verifyPasswordRecoveryCode(challengeId, code)
            setStep('password')
        } catch (err) {
            setError(
                err instanceof Error
                    ? err.message
                    : 'Não foi possível validar o código.'
            )
        } finally {
            setLoading(false)
        }
    }

    async function handleResetPassword(event: SyntheticEvent<HTMLFormElement>) {
        event.preventDefault()
        setError('')
        setMessage('')

        if (newPassword.length < 8) {
            setError('A senha deve ter pelo menos 8 caracteres.')
            return
        }

        if (newPassword !== confirmPassword) {
            setError('As senhas não coincidem.')
            return
        }

        setLoading(true)

        try {
            await resetPassword(challengeId, code, newPassword)
            onSuccess()
        } catch (err) {
            setError(
                err instanceof Error
                    ? err.message
                    : 'Não foi possível redefinir a senha.'
            )
        } finally {
            setLoading(false)
        }
    }

    function handleOverlayClick() {
        if (!loading) onClose()
    }

    return (
        <div
            className="password-recovery-overlay"
            onMouseDown={event => {
                if (event.target === event.currentTarget) {
                    handleOverlayClick()
                }
            }}
        >
            <section
                className="password-recovery-modal"
                role="dialog"
                aria-modal="true"
                aria-labelledby="password-recovery-title"
            >
                <button
                    type="button"
                    className="password-recovery-close"
                    onClick={onClose}
                    disabled={loading}
                    aria-label="Fechar"
                >
                    ×
                </button>
                <span className="password-recovery-eyebrow">
                    CULTURATECA
                </span>
                <h2 id="password-recovery-title">
                    {step === 'email' && 'Recuperar senha'}
                    {step === 'code' && 'Confira seu e-mail'}
                    {step === 'password' && 'Crie uma nova senha'}
                </h2>
                <p className="password-recovery-description">
                    {step === 'email' &&
                        'Informe o e-mail associado à sua conta para receber as instruções de recuperação.'}
                    {step === 'code' &&
                        'Digite o código de seis dígitos enviado para o seu e-mail.'}
                    {step === 'password' &&
                        'Escolha uma nova senha com pelo menos 8 caracteres.'}
                </p>
                {message && (
                    <p className="password-recovery-message" role="status">
                        {message}
                    </p>
                )}
                {error && (
                    <p className="password-recovery-error" role="alert">
                        {error}
                    </p>
                )}
                {step === 'email' && (
                    <form onSubmit={handleRequestCode}>
                        <label htmlFor="recovery-email">E-mail</label>
                        <input
                            id="recovery-email"
                            type="email"
                            value={email}
                            onChange={event => setEmail(event.target.value)}
                            placeholder="exemplo@email.com"
                            required
                            disabled={loading}
                        />
                        <button
                            type="submit"
                            className="password-recovery-submit"
                            disabled={loading}
                        >
                            {loading ? 'ENVIANDO...' : 'ENVIAR CÓDIGO'}
                        </button>
                    </form>
                )}
                {step === 'code' && (
                    <form onSubmit={handleVerifyCode}>
                        <label htmlFor="recovery-code">
                            Código de verificação
                        </label>
                        <input
                            id="recovery-code"
                            type="text"
                            inputMode="numeric"
                            autoComplete="one-time-code"
                            maxLength={6}
                            pattern="[0-9]{6}"
                            value={code}
                            onChange={event =>
                                setCode(
                                    event.target.value
                                        .replace(/\D/g, '')
                                        .slice(0, 6)
                                )
                            }
                            placeholder="000000"
                            required
                            disabled={loading}
                        />
                        <button
                            type="submit"
                            className="password-recovery-submit"
                            disabled={loading || code.length !== 6}
                        >
                            {loading ? 'VERIFICANDO...' : 'VALIDAR CÓDIGO'}
                        </button>
                        <button
                            type="button"
                            className="password-recovery-back"
                            onClick={() => {
                                setError('')
                                setMessage('')
                                setStep('email')
                            }}
                            disabled={loading}
                        >
                            Usar outro e-mail
                        </button>
                    </form>
                )}
                {step === 'password' && (
                    <form onSubmit={handleResetPassword}>
                        <label htmlFor="recovery-new-password">
                            Nova senha
                        </label>
                        <input
                            id="recovery-new-password"
                            type="password"
                            autoComplete="new-password"
                            value={newPassword}
                            onChange={event =>
                                setNewPassword(event.target.value)
                            }
                            minLength={8}
                            required
                            disabled={loading}
                        />
                        <label htmlFor="recovery-confirm-password">
                            Confirmar nova senha
                        </label>
                        <input
                            id="recovery-confirm-password"
                            type="password"
                            autoComplete="new-password"
                            value={confirmPassword}
                            onChange={event =>
                                setConfirmPassword(event.target.value)
                            }
                            minLength={8}
                            required
                            disabled={loading}
                        />
                        <button
                            type="submit"
                            className="password-recovery-submit"
                            disabled={loading}
                        >
                            {loading ? 'SALVANDO...' : 'ALTERAR SENHA'}
                        </button>
                    </form>
                )}
                <button
                    type="button"
                    className="password-recovery-cancel"
                    onClick={onClose}
                    disabled={loading}
                >
                    Cancelar
                </button>
            </section>
        </div>
    )
}

export default PasswordRecoveryModal
