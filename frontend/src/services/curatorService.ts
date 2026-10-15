import { apiFetch } from "./api"

export interface Curator {
    id: number
    email: string
    name: string
    password: string
    gender: string | null
    phone: string | null
    isPublic: boolean
    emailMfaEnabled: boolean
    bio: string | null
    following: number[] | null
    profilePicture: string | null
    location: {
        id: number
        name: string
    } | null
}

export interface UpdateCuratorData {
    email?: string
    password?: string
    name?: string
    gender?: string | null
    phone?: string | null
    isPublic?: boolean
    bio?: string | null
    following?: number[] | null
    profilePicture?: string | null
    location?: {
        id: number
    } | null
}

export interface RegisterCuratorData {
    email: string
    password: string
    name: string
    gender: string | null
    phone: string | null
    bio: string | null
    isPublic: boolean
    profilePicture: string | null
    location: {
        id: number
    } | null
}

export interface ApiMessage {
    message: string
}

async function getApiMessage( response: Response, fallback: string ): Promise<string> {
    try {
        const data = await response.json() as ApiMessage
        return data.message || fallback
    } catch {
        return fallback
    }
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

export async function updateEmailMfaEnabled(enabled: boolean): Promise<void> {
    const response = await apiFetch('http://localhost:8080/curator/me/email-mfa', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ enabled })
    })

    if (!response.ok) {
        throw new Error(
            await getApiMessage(
                response,
                'Não foi possível alterar a autenticação em duas etapas.'
            )
        )
    }
}

export async function getCuratorById(id: number): Promise<Curator> {
    const response = await apiFetch(
        `http://localhost:8080/curator/id=${id}`
    )

    if (!response.ok) {
        throw new Error('Erro ao buscar curator')
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

export async function requestRegistrationCode(data: RegisterCuratorData): Promise<ApiMessage> {
    const response = await fetch('http://localhost:8080/curator/register/request-code', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: data.name,
            email: data.email,
            password: data.password,
            gender: data.gender,
            phone: data.phone,
            bio: data.bio,
            locationId: data.location?.id ?? null,
            isPublic: data.isPublic,
            profilePicture: data.profilePicture
        })
    })

    if (!response.ok) {
        throw new Error(
            await getApiMessage(
                response,
                'Não foi possível enviar o código de verificação.'
            )
        )
    }

    return response.json()
}

export async function verifyRegistrationCode(email: string, code: string): Promise<ApiMessage> {
    const response = await fetch('http://localhost:8080/curator/register/verify-code', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            email,
            code
        })
    })

    if (!response.ok) {
        throw new Error(
            await getApiMessage(
                response,
                'Não foi possível verificar o código.'
            )
        )
    }

    return response.json()
}

export async function updateCurator(id: number, data: UpdateCuratorData) {
    const response = await apiFetch('http://localhost:8080/curator', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: id,
            ...data
        })
    })

    if (!response.ok) {
        throw new Error('Erro ao atualizar curator')
    }

    return response.json()
}

export async function deleteCurator(id: number) {
    const response = await apiFetch(
        `http://localhost:8080/curator/${id}`,
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