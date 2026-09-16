import { apiFetch } from "./api"

export interface Curator {
    email: string
    name: string
    password: string
    gender: string
    phone: string
    isPublic: boolean
    bio: string
    following: string[]
    profilePicture: string
    location: {
        id: number
    } | null
}

export async function getAllCurator(): Promise<Curator[]> {
    const response = await apiFetch('http://localhost:8080/curator')

    if (!response.ok) {
        throw new Error('Erro ao buscar curators')
    }

    return response.json()
}

export async function getCurrentCurator(): Promise<Curator> {
    const response = await apiFetch('http://localhost:8080/curator/me')

    if (!response.ok) {
        throw new Error('Erro ao buscar usuário')
    }

    return response.json()
}

export async function getCuratorByEmail(email: string): Promise<Curator> {
    const response = await apiFetch(
        `http://localhost:8080/curator/email=${email}`
    )

    if (!response.ok) {
        throw new Error('Erro ao buscar curator')
    }

    return response.json()
}

export async function getCuratorByName(name: string): Promise<Curator[]> {
    const response = await apiFetch(
        `http://localhost:8080/curator/name=${name}`
    )

    if (!response.ok) {
        throw new Error('Erro ao buscar curators')
    }

    return response.json()
}

export async function createCurator(
    email: string,
    password: string,
    name: string,
    gender: string | null,
    phone: string,
    isPublic: boolean,
    bio: string,
    following: string[],
    profilePicture: string,
    locationId: number | null
) {
    const response = await fetch(
        'http://localhost:8080/curator/register',
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                email: email,
                password: password,
                name: name,
                gender: gender,
                phone: phone,
                isPublic: isPublic,
                bio: bio,
                following: following,
                profilePicture: profilePicture,
                location: locationId !== null
                    ? { id: locationId }
                    : null
            })
        }
    )

    if (!response.ok) {
        throw new Error('Erro ao criar curator')
    }

    return response.json()
}

export async function updateCurator(
    email: string,
    password: string,
    name: string,
    gender: string | null,
    phone: string,
    isPublic: boolean,
    bio: string,
    following: string[],
    profilePicture: string,
    locationId: number | null
) {
    const response = await apiFetch(
        'http://localhost:8080/curator',
        {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                email: email,
                password: password,
                name: name,
                gender: gender,
                phone: phone,
                isPublic: isPublic,
                bio: bio,
                following: following,
                profilePicture: profilePicture,
                location: locationId !== null
                    ? { id: locationId }
                    : null
            })
        }
    )

    if (!response.ok) {
        throw new Error('Erro ao atualizar curator')
    }

    return response.json()
}

export async function deleteCurator(email: string) {
    const response = await apiFetch(
        `http://localhost:8080/curator/${email}`,
        {
            method: 'DELETE'
        }
    )

    if (!response.ok) {
        throw new Error('Erro ao excluir curator')
    }

    if (response.status === 204) {
        return
    }
}