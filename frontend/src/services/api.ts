export async function apiFetch(url: string, options: RequestInit = {}) {
    const token = localStorage.getItem('token')

    const headers = new Headers(options.headers)

    headers.set('Content-Type', 'application/json')

    if (token) {
        headers.set('Authorization', `Bearer ${token}`)
    }

    return fetch(url, {
        ...options,
        headers
    })
}