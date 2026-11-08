import { apiFetch } from "./api"
import API_URL from "./api"

export interface Canonic {
    id: number
    name: string
    property: {
        [key: number]: string
    }
    category: {
        id: number
    } | null
}

export async function getAllCanonic(): Promise<Canonic[]> {
    const response = await apiFetch(`${API_URL}/canonic`)

    if (!response.ok) {
        throw new Error('Erro ao buscar canonics')
    }

    return response.json()
}

export async function getCanonicById(id: number): Promise<Canonic> {
    const response = await apiFetch(`${API_URL}/canonic/id=${encodeURIComponent(id)}`)

    if (!response.ok) {
        throw new Error('Erro ao buscar canonic')
    }

    return response.json()
}

export async function getCanonicsByCategory(categoryId: number): Promise<Canonic[]> {
    const response = await apiFetch(`${API_URL}/canonic/category=${encodeURIComponent(categoryId)}`)

    if (!response.ok) {
        throw new Error('Erro ao buscar canonics pela category')
    }

    return response.json()
}

export async function getCanonicsByProperty(propertyId: number): Promise<Canonic[]> {
    const response = await apiFetch(`${API_URL}/canonic/property=${encodeURIComponent(propertyId)}`)

    if (!response.ok) {
        throw new Error('Erro ao buscar canonics pela property')
    }

    return response.json()
}

export async function createCanonic(
    property: { [key: number]: string },
    name: string,
    categoryId: number | null
) {
    const response = await apiFetch(
        `${API_URL}/canonic`,
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                property: property,
                name: name,
                category: categoryId !== null
                    ? { id: categoryId }
                    : null
            })
        }
    )

    if (!response.ok) {
        throw new Error('Erro ao criar canonic')
    }

    return response.json()
}

export async function updateCanonic(
    id: number,
    name: string,
    property: { [key: number]: string },
    categoryId: number | null
) {
    const response = await apiFetch(
        `${API_URL}/canonic`,
        {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: id,
                name: name,
                property: property,
                category: categoryId !== null
                    ? { id: categoryId }
                    : null
            })
        }
    )

    if (!response.ok) {
        throw new Error('Erro ao atualizar canonic')
    }

    return response.json()
}

export async function deleteCanonic(id: number) {
    const response = await apiFetch(`${API_URL}/canonic/${encodeURIComponent(id)}`,{
        method: 'DELETE'
    })

    if (!response.ok) {
        throw new Error('Erro ao excluir canonic')
    }

    if (response.status === 204) {
        return
    }
}