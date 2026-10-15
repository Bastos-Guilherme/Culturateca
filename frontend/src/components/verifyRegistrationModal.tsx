
import { useEffect, useState } from 'react'

import { requestRegistrationCode, verifyRegistrationCode, type RegisterCuratorData } from '../services/curatorService'

import '../styles/VerifyRegistrationModal.css'

interface VerifyRegistrationModalProps {
    registrationData: RegisterCuratorData
    onClose: () => void
    onVerified: () => void
}

function VerifyRegistrationModal({
    registrationData,
    onClose,
    onVerified
}: VerifyRegistrationModalProps) {
    const [code, setCode] = useState('')
    const [resendSeconds, setResendSeconds] = useState(60)

    const [verifying, setVerifying] = useState(false)
    const [resending, setResending] = useState(false)
    const [error, setError] = useState('')
    const [message, setMessage] = useState('')

    useEffect(() => {
        if (resendSeconds <= 0) {
            return
        }

        const timer = window.setInterval(() => {
            setResendSeconds(current =>
                Math.max(0, current - 1)
            )
        }, 1000)

        return () => window.clearInterval(timer)
    }, [resendSeconds])

    async function handleVerify() {
        const normalizedCode = code.trim()

        if (!/^\d{6}$/.test(normalizedCode)) {
            setError('Informe o código de verificação de 6 dígitos.')
            return
        }

        try {
            setVerifying(true)
            setError('')
            setMessage('')

            await verifyRegistrationCode(
                registrationData.email,
                normalizedCode
            )

            onVerified()
        } catch (error) {
            console.error(error)

            setError(
                error instanceof Error
                    ? error.message
                    : 'Não foi possível verificar o código.'
            )
        } finally {
            setVerifying(false)
        }
    }

    async function handleResend() {
        if (
            resendSeconds > 0 ||
            resending ||
            verifying
        ) {
            return
        }

        try {
            setResending(true)
            setError('')
            setMessage('')

            await requestRegistrationCode(registrationData)

            setCode('')
            setResendSeconds(60)
            setMessage(
                'Se a solicitação for válida, enviaremos um novo código.'
            )
        } catch (error) {
            console.error(error)

            setError(
                error instanceof Error
                    ? error.message
                    : 'Não foi possível reenviar o código.'
            )
        } finally {
            setResending(false)
        }
    }

    return (
        <div
            className="verify-registration-overlay"
            onMouseDown={event => {
                if (
                    event.target === event.currentTarget &&
                    !verifying &&
                    !resending
                ) {
                    onClose()
                }
            }}
        >
            <div
                className="verify-registration-modal"
                role="dialog"
                aria-modal="true"
                aria-labelledby="verify-registration-title"
            >
                <div className="verify-registration-header">
                    <div>
                        <span className="section-label">
                            VERIFICAÇÃO DE E-MAIL
                        </span>

                        <h2 id="verify-registration-title">
                            Confirme seu e-mail
                        </h2>
                    </div>

                    <button
                        type="button"
                        className="verify-registration-close"
                        onClick={onClose}
                        disabled={verifying || resending}
                        aria-label="Fechar modal"
                    >
                        <i className="bi bi-x-lg"></i>
                    </button>
                </div>

                <div className="verify-registration-body">
                    <p className="verify-registration-description">
                        Enviamos um código de verificação para:
                    </p>

                    <p className="verify-registration-email">
                        {registrationData.email}
                    </p>

                    <p className="verify-registration-description">
                        Digite o código de 6 dígitos recebido por e-mail
                        para concluir a criação da sua conta.
                    </p>

                    <div className="verify-registration-field">
                        <label htmlFor="verification-code">
                            Código de verificação
                        </label>

                        <input
                            id="verification-code"
                            type="text"
                            inputMode="numeric"
                            autoComplete="one-time-code"
                            maxLength={6}
                            value={code}
                            onChange={event => {
                                setCode(
                                    event.target.value
                                        .replace(/\D/g, '')
                                        .slice(0, 6)
                                )
                                setError('')
                            }}
                            placeholder="000000"
                            autoFocus
                            disabled={verifying || resending}
                            onKeyDown={event => {
                                if (event.key === 'Enter') {
                                    event.preventDefault()
                                    void handleVerify()
                                }
                            }}
                        />
                    </div>

                    {error && (
                        <p
                            className="verify-registration-error"
                            role="alert"
                        >
                            {error}
                        </p>
                    )}

                    {message && (
                        <p
                            className="verify-registration-message"
                            role="status"
                        >
                            {message}
                        </p>
                    )}

                    <div className="verify-registration-resend">
                        <span>Não recebeu o código?</span>

                        {resendSeconds > 0 ? (
                            <span>
                                Reenviar em {resendSeconds}s
                            </span>
                        ) : (
                            <button
                                type="button"
                                onClick={handleResend}
                                disabled={resending || verifying}
                            >
                                {resending
                                    ? 'Reenviando...'
                                    : 'Reenviar código'}
                            </button>
                        )}
                    </div>
                </div>

                <div className="verify-registration-actions">
                    <button
                        type="button"
                        className="verify-registration-cancel"
                        onClick={onClose}
                        disabled={verifying || resending}
                    >
                        Cancelar
                    </button>

                    <button
                        type="button"
                        className="verify-registration-confirm"
                        onClick={handleVerify}
                        disabled={verifying || resending}
                    >
                        {verifying
                            ? 'Verificando...'
                            : 'Confirmar e-mail'}
                    </button>
                </div>
            </div>
        </div>
    )
}

export default VerifyRegistrationModal
