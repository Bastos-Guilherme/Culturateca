import { apiFetch } from "./api"

export interface Collection {
    id: number
    curator: {
        email: string
    }
    name: string
    isPublic: boolean
    copies: {
        id: number
    }[]
}

export async function getAllCollection(): Promise<Collection[]> {
    const response = await apiFetch('http://localhost:8080/collection')

    if (!response.ok) {
        throw new Error('Erro ao buscar collections')
    }

    return response.json()
}

export async function getCollectionsByCurator(curator: string): Promise<Collection[]> {
    const response = await apiFetch(`http://localhost:8080/collection/curator=${curator}`)

    if (!response.ok) {
        throw new Error('Erro ao buscar collections do curator')
    }

    return response.json()
}

export async function getCollectionById(id: number): Promise<Collection> {
    const response = await apiFetch(`http://localhost:8080/collection/id=${id}`)

    if (!response.ok) {
        throw new Error('Erro ao buscar collection')
    }

    return response.json()
}

export async function getCollectionByName(name: string): Promise<Collection[]> {
    const response = await apiFetch(`http://localhost:8080/collection/name=${name}`)

    if (!response.ok) {
        throw new Error('Erro ao buscar collections')
    }

    return response.json()
}

export async function createCollection(
    curatorEmail: string,
    name: string,
    isPublic: boolean,
    copyIds: number[]
) {
    const response = await apiFetch('http://localhost:8080/collection',{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            curator: {
                email: curatorEmail
            },
            name: name,
            isPublic: isPublic,
            copies: copyIds.map(id => ({ id }))
        })
    })

    if (!response.ok) {
        throw new Error('Erro ao criar collection')
    }

    return response.json()
}

export async function updateCollection(
    id: number,
    curatorEmail: string,
    name: string,
    isPublic: boolean,
    copyIds: number[]
) {
    const response = await apiFetch('http://localhost:8080/collection',{
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: id,
            curator: {
                email: curatorEmail
            },
            name: name,
            isPublic: isPublic,
            copies: copyIds.map(id => ({ id }))
        })
    })

    if (!response.ok) {
        throw new Error('Erro ao atualizar collection')
    }

    return response.json()
}

export async function deleteCollection(id: number) {
    const response = await apiFetch(`http://localhost:8080/collection/${id}`,{
        method: 'DELETE'
    })

    if (!response.ok) {
        throw new Error('Erro ao excluir collection')
    }

    if (response.status === 204) {
        return
    }
}