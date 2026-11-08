import { apiFetch } from "./api"
import API_URL from "./api"

export interface Category {
    id: number
    name: string
    curator: { id: number } | null
}

export async function getAllCategory(): Promise<Category[]> {
    const response = await apiFetch(`${API_URL}/category`)

    if (!response.ok) {
        throw new Error('Erro ao buscar categories')
    }

    return response.json()
}

export async function getCategoryById(id: number): Promise<Category> {
    const response = await apiFetch(`${API_URL}/category/id=${encodeURIComponent(id)}`)

    if (!response.ok) {
        throw new Error('Erro ao buscar category')
    }

    return response.json()
}

export async function getCategoryByName(name: string, curatorId: number): Promise<Category[]> {
    const response = await apiFetch(
        `${API_URL}/category/name?name=${encodeURIComponent(name)}&curatorId=${encodeURIComponent(curatorId)}`
    )

    if (!response.ok) {
        throw new Error("Erro ao buscar category")
    }

    return response.json()
}

export async function getCategoriesByCurator(curatorId: number): Promise<Category[]> {
    const response = await apiFetch(
        `${API_URL}/category/curator=${encodeURIComponent(curatorId)}`
    )

    if (!response.ok) {
        throw new Error('Erro ao buscar categories pelo curator')
    }

    return response.json()
}

export async function createCategory(
    name: string,
    curatorId: number
): Promise<Category> {
    const response = await apiFetch(`${API_URL}/category`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name,
            curator: { id: curatorId }
        })
    })

    if (!response.ok) {
        throw new Error('Erro ao criar category')
    }

    return response.json()
}

export async function updateCategory(
    id: number,
    name: string,
    curatorId: number
): Promise<Category> {
    const response = await apiFetch(`${API_URL}/category`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id,
            name,
            curator: { id: curatorId }
        })
    })

    if (!response.ok) {
        throw new Error('Erro ao atualizar category')
    }

    return response.json()
}

export async function deleteCategory(id: number) {
    const response = await apiFetch(`${API_URL}/category/${encodeURIComponent(id)}`, {
        method: 'DELETE'
    })

    if (!response.ok) {
        throw new Error('Erro ao excluir category')
    }

    if (response.status === 204) {
        return
    }
}