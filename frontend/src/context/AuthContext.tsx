import { createContext, useContext, useState, type ReactNode } from "react";

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

export function AuthProvider({ children }: AuthProviderProps){
    const [token, setToken] = useState<string | null>(() => {
        return localStorage.getItem('token')
    })

    function login(newToken: string){
        localStorage.setItem('token', newToken)
        setToken(newToken)
    }

    function logout(){
        localStorage.removeItem('token')
        setToken(null)
    }

    const isAuthenticated = token !== null

    return(
        <AuthContext.Provider value={{
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

export function useAuth(){
    const context = useContext(AuthContext)

    if(!context){
        throw new Error('useAuth deve ser usado dentro de um AuthProvider')
    }

    return context
}