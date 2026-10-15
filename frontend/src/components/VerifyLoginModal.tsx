
import { useState, type SyntheticEvent } from 'react'

import { verifyLoginCode } from '../services/loginService'
import '../styles/VerifyLoginModal.css'

interface VerifyLoginModalProps {
    challengeId: string
    onClose: () => void
    onVerified: (token: string) => void
}

function VerifyLoginModal({
    challengeId,
    onClose,
    onVerified,
}: VerifyLoginModalProps) {
    const [code, setCode] = useState('')
    const [error, setError] = useState('')
    const [verifying, setVerifying] = useState(false)

    async function handleVerify(event: SyntheticEvent<HTMLFormElement>) {
        event.preventDefault()
        setError('')

        const normalizedCode = code.trim()

        if (!/^\d{6}$/.test(normalizedCode)) {
            setError('Digite o código de seis dígitos enviado ao seu e-mail.')
            return
        }

        try {
            setVerifying(true)

            const token = await verifyLoginCode(
                challengeId,
                normalizedCode
            )

            onVerified(token)
        } catch (err) {
            setError(
                err instanceof Error
                    ? err.message
                    : 'Não foi possível verificar o código.'
            )
        } finally {
            setVerifying(false)
        }
    }

    return (
        <div className="verify-login-overlay">
            <section
                className="verify-login-modal"
                role="dialog"
                aria-modal="true"
                aria-labelledby="verify-login-title"
            >
                <header className="verify-login-header">
                    <div>
                        <span className="section-label">
                            SEGURANÇA DA CONTA
                        </span>

                        <h2 id="verify-login-title">
                            Verifique seu e-mail
                        </h2>
                    </div>
                    <button
                        type="button"
                        className="verify-login-close"
                        onClick={onClose}
                        disabled={verifying}
                        aria-label="Fechar"
                    >
                        &times;
                    </button>
                </header>
                <div className="verify-login-body">
                    <p className="verify-login-description">
                        Enviamos um código de verificação para seu e-mail.
                        Digite-o abaixo para concluir o login.
                    </p>
                    <form onSubmit={handleVerify}>
                        <div className="verify-login-field">
                            <label htmlFor="login-verification-code">
                                CÓDIGO DE VERIFICAÇÃO
                            </label>
                            <input
                                id="login-verification-code"
                                type="text"
                                inputMode="numeric"
                                autoComplete="one-time-code"
                                placeholder="000000"
                                maxLength={6}
                                value={code}
                                onChange={(event) => {
                                    setCode(
                                        event.target.value
                                            .replace(/\D/g, '')
                                            .slice(0, 6)
                                    )
                                    setError('')
                                }}
                                disabled={verifying}
                                required
                            />
                        </div>
                        {error && (
                            <p className="verify-login-error" role="alert">
                                {error}
                            </p>
                        )}
                        <div className="verify-login-actions">
                            <button
                                type="button"
                                className="verify-login-cancel"
                                onClick={onClose}
                                disabled={verifying}
                            >
                                Cancelar
                            </button>
                            <button
                                type="submit"
                                className="verify-login-confirm"
                                disabled={verifying || code.length !== 6}
                            >
                                {verifying ? 'Verificando...' : 'VERIFICAR E ENTRAR'}
                            </button>
                        </div>
                    </form>
                </div>
            </section>
        </div>
    )
}

export default VerifyLoginModal