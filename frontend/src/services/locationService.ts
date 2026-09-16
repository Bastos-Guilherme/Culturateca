import { apiFetch } from "./api";

export interface Location { 
    id: number 
    name: string 
    latitude: number 
    longitude: number
    location: {
        id: number
    } | null
}

export async function getAllLocation() {
    const response = await fetch("http://localhost:8080/location");

    if (!response.ok) {
        throw new Error("Erro ao buscar locations");
    }

    return response.json();
}

export async function getLocationById(id: number) {
    const response = await apiFetch(`http://localhost:8080/location/id=${id}`);

    if (!response.ok) {
        throw new Error("Erro ao buscar location");
    }

    return response.json();
}

export async function getLocationByName(name: string) {
    const response = await apiFetch(`http://localhost:8080/location/name=${name}`);

    if (!response.ok) {
        throw new Error("Erro ao buscar locations");
    }

    return response.json();
}

export async function createLocation(
    name: string,
    latitude: number,
    longitude: number,
    location?: number
) {
    const response = await fetch("http://localhost:8080/location", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            name: name,
            latitude: latitude,
            longitude: longitude,
            location: location ? { id: location } : null,
        }),
    });

    if (!response.ok) {
        throw new Error("Erro ao cadastrar location");
    }

    return response.json();
}

export async function updateLocation(
    id: number,
    name: string,
    latitude: number,
    longitude: number,
    location?: number
) {
    const response = await apiFetch("http://localhost:8080/location", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            id: id,
            name: name,
            latitude: latitude,
            longitude: longitude,
            location: location ? { id: location } : null,
        }),
    });

    if (!response.ok) {
        throw new Error("Erro ao atualizar location");
    }

    return response.json();
}

export async function deleteLocation(id: number) {
    const response = await apiFetch(`http://localhost:8080/location/${id}`, {
        method: "DELETE",
    });

    if (!response.ok) {
        throw new Error("Erro ao excluir location");
    }

    if (response.status === 204) {
        return;
    }
}