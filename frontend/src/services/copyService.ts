import { apiFetch } from "./api"

export interface Copy {
    id: number
    property: {
        [key: number]: string
    }
    canonic: {
        id: number
    } | null
    location: {
        id: number
    } | null
    collections: {
        id: number
    }[]
}

export async function getAllCopy(): Promise<Copy[]> {
    const response = await apiFetch(
        'http://localhost:8080/copy'
    )

    if (!response.ok) {
        throw new Error('Erro ao buscar copies')
    }

    return response.json()
}

export async function getCopyById(
    id: number
): Promise<Copy> {
    const response = await apiFetch(
        `http://localhost:8080/copy/id=${id}`
    )

    if (!response.ok) {
        throw new Error('Erro ao buscar copy')
    }

    return response.json()
}

export async function getCopiesByProperty(
    propertyId: number
): Promise<Copy[]> {
    const response = await apiFetch(
        `http://localhost:8080/copy/property=${propertyId}`
    )

    if (!response.ok) {
        throw new Error('Erro ao buscar copies pela property')
    }

    return response.json()
}

export async function getCopiesByCanonic(
    canonicId: number
): Promise<Copy[]> {
    const response = await apiFetch(
        `http://localhost:8080/copy/canonic=${canonicId}`
    )

    if (!response.ok) {
        throw new Error('Erro ao buscar copies pelo canonic')
    }

    return response.json()
}

export async function createCopy(
    property: { [key: number]: string },
    canonicId: number | null,
    locationId: number | null
) {
    const response = await apiFetch(
        'http://localhost:8080/copy',
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                property: property,
                canonic: canonicId !== null
                    ? { id: canonicId }
                    : null,
                location: locationId !== null
                    ? { id: locationId }
                    : null
            })
        }
    )

    if (!response.ok) {
        throw new Error('Erro ao criar copy')
    }

    return response.json()
}

export async function updateCopy(
    id: number,
    property: { [key: number]: string },
    canonicId: number | null,
    locationId: number | null
) {
    const response = await apiFetch(
        'http://localhost:8080/copy',
        {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: id,
                property: property,
                canonic: canonicId !== null
                    ? { id: canonicId }
                    : null,
                location: locationId !== null
                    ? { id: locationId }
                    : null
            })
        }
    )

    if (!response.ok) {
        throw new Error('Erro ao atualizar copy')
    }

    return response.json()
}

export async function deleteCopy(id: number) {
    const response = await apiFetch(
        `http://localhost:8080/copy/${id}`,
        {
            method: 'DELETE'
        }
    )

    if (!response.ok) {
        throw new Error('Erro ao excluir copy')
    }

    if (response.status === 204) {
        return
    }
}