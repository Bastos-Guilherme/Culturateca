import {
    createContext,
    useContext,
    useState,
    type ReactNode
} from 'react'

import NotificationModal, {
    type NotificationType
} from '../components/NotificationModal'

interface NotificationContextProps {
    showNotification: (
        message: string,
        type?: NotificationType,
        duration?: number
    ) => void
}

interface Notification {
    message: string
    type: NotificationType
    duration: number
}

const NotificationContext = createContext<NotificationContextProps | undefined>(
    undefined
)

export function NotificationProvider({ children }: { children: ReactNode }) {
    const [notification, setNotification] = useState<Notification | null>(null)

    function showNotification(
        message: string,
        type: NotificationType = 'info',
        duration: number = 4000
    ) {
        setNotification({ message, type, duration })
    }

    function closeNotification() {
        setNotification(null)
    }

    return (
        <NotificationContext.Provider value={{ showNotification }}>
            {children}

            {notification && (
                <NotificationModal
                    message={notification.message}
                    type={notification.type}
                    duration={notification.duration}
                    onClose={closeNotification}
                />
            )}
        </NotificationContext.Provider>
    )
}

export function useNotification() {
    const context = useContext(NotificationContext)

    if (!context) {
        throw new Error(
            'useNotification deve ser utilizado dentro de NotificationProvider'
        )
    }

    return context
}
