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

export async function apiFetch(url: string, options: RequestInit = {}) {

    const token = localStorage.getItem('token')

    const headers = new Headers(options.headers)

    headers.set('Content-Type', 'application/json')

    if (token) {

        if (!isTokenValid(token)) {
            localStorage.removeItem('token')

            window.dispatchEvent(new Event('auth:logout'))

            throw new Error('Token expirado')
        }

        headers.set('Authorization', `Bearer ${token}`)
    }

    return fetch(url, {
        ...options,
        headers
    })
}