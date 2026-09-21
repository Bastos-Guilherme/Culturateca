import { apiFetch } from "./api"

export interface Category {
    id: number
    name: string
}

export async function getAllCategory(): Promise<Category[]> {
    const response = await apiFetch('http://localhost:8080/category')

    if (!response.ok) {
        throw new Error('Erro ao buscar categories')
    }

    return response.json()
}

export async function getCategoryById(id: number): Promise<Category> {
    const response = await apiFetch(`http://localhost:8080/category/id=${id}`)

    if (!response.ok) {
        throw new Error('Erro ao buscar category')
    }

    return response.json()
}

export async function getCategoryByName(name: string): Promise<Category[]> {
    const response = await apiFetch(`http://localhost:8080/category/name=${name}`)

    if (!response.ok) {
        throw new Error('Erro ao buscar categories')
    }

    return response.json()
}

export async function createCategory(name: string) {
    const response = await apiFetch('http://localhost:8080/category',{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: name
        })
    })

    if (!response.ok) {
        throw new Error('Erro ao criar category')
    }

    return response.json()
}

export async function updateCategory(id: number,name: string) {
    const response = await apiFetch('http://localhost:8080/category',{
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: id,
            name: name
        })
    })

    if (!response.ok) {
        throw new Error('Erro ao atualizar category')
    }

    return response.json()
}

export async function deleteCategory(id: number) {
    const response = await apiFetch(`http://localhost:8080/category/${id}`,{
        method: 'DELETE'
    })

    if (!response.ok) {
        throw new Error('Erro ao excluir category')
    }

    if (response.status === 204) {
        return
    }
}