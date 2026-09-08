import { apiFetch } from "./api";

export interface Curator{
    email: string
    name: string
    password: string
    gender: string
    phone: string
    isPublic: boolean
    location:{
        id: number
    }
}

export async function getCurrentCurator(): Promise<Curator> {
    const reponse = await apiFetch('http://localhost:8080/curator/me')

    if(!reponse.ok){
        throw new Error('Erro ao buscar usuário')
    }

    return reponse.json()
}

export async function createCurator(
    email: string, 
    password: string, 
    name: string, 
    gender: string, 
    phone: string,
    locationId: number | null
) {
    const response = await fetch('http://localhost:8080/curator/register',{
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
                location: locationId !== null ? { id: locationId } : null
            })
        }
    )

    if(!response.ok){
        throw new Error('Erro ao criar curator')
    }

    return response.json()
}