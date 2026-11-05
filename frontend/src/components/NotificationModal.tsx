
import { useEffect } from 'react'
import '../styles/NotificationModal.css'

export type NotificationType = 'success' | 'error' | 'warning' | 'info'

interface NotificationModalProps {
    message: string
    type: NotificationType
    duration: number
    onClose: () => void
}

function NotificationModal({
    message,
    type,
    duration,
    onClose
}: NotificationModalProps) {

    useEffect(() => {
        if (duration <= 0) return

        const timer = setTimeout(onClose, duration)

        return () => clearTimeout(timer)
    }, [duration, onClose])

    const icons = {
        success: 'bi-check-circle',
        error: 'bi-x-circle',
        warning: 'bi-exclamation-triangle',
        info: 'bi-info-circle'
    }

    const titles = {
        success: 'Sucesso!',
        error: 'Ops! Algo deu errado',
        warning: 'Atenção!',
        info: 'Informação'
    }

    return (
        <div className="notification-overlay" onMouseDown={event => {
            if (event.target === event.currentTarget) {
                onClose()
            }
        }}>
            <div
                className={`notification-modal notification-${type}`}
                role={type === 'error' || type === 'warning' ? 'alert' : 'status'}
                aria-live={type === 'error' || type === 'warning' ? 'assertive' : 'polite'}
            >
                <button
                    className="notification-close"
                    onClick={onClose}
                    aria-label="Fechar notificação"
                >
                    <i className="bi bi-x-lg"></i>
                </button>

                <i className={`bi ${icons[type]} notification-icon`}></i>

                <h2>{titles[type]}</h2>

                <p>{message}</p>

                <button
                    className="notification-confirm"
                    onClick={onClose}
                >
                    Entendi
                </button>
            </div>
        </div>
    )
}

export default NotificationModal
