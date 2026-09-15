import { createContext, useContext, useEffect, useState, type ReactNode } from "react";

interface AuthContextType {
    token: string | null
    isAuthenticated: boolean
    login: (token: string) => void
    logout: () => void
}

const AuthContext = createContext<AuthContextType | undefined>(undefined)

interface AuthProviderProps {
    children: ReactNode
}

function getTokenExpiration(token: string): number | null {
    try {
        const payload = JSON.parse(atob(token.split('.')[1]))

        if (!payload.exp) {
            return null
        }

        return payload.exp * 1000
    } catch {
        return null
    }
}

function isTokenValid(token: string): boolean {
    const expiration = getTokenExpiration(token)

    if (!expiration) {
        return false
    }

    return expiration > Date.now()
}

export function AuthProvider({ children }: AuthProviderProps) {

    const [token, setToken] = useState<string | null>(() => {
        const storedToken = localStorage.getItem('token')

        if (!storedToken) {
            return null
        }

        if (!isTokenValid(storedToken)) {
            localStorage.removeItem('token')
            return null
        }

        return storedToken
    })

    useEffect(() => {
        if (!token) {
            return
        }

        const expiration = getTokenExpiration(token)

        if (!expiration) {
            logout()
            return
        }

        const timeUntilExpiration = expiration - Date.now()

        if (timeUntilExpiration <= 0) {
            logout()
            return
        }

        const timeout = setTimeout(() => {
            logout()
        }, timeUntilExpiration)

        return () => {
            clearTimeout(timeout)
        }

    }, [token])

    function login(newToken: string) {
        if (!isTokenValid(newToken)) {
            return
        }

        localStorage.setItem('token', newToken)
        setToken(newToken)
    }

    function logout() {
        localStorage.removeItem('token')
        setToken(null)
    }

    const isAuthenticated = token !== null

    return (
        <AuthContext.Provider
            value={{
                token,
                isAuthenticated,
                login,
                logout
            }}
        >
            {children}
        </AuthContext.Provider>
    )
}

export function useAuth() {
    const context = useContext(AuthContext)

    if (!context) {
        throw new Error('useAuth deve ser usado dentro de um AuthProvider')
    }

    return context
}