import API_URL, { apiFetch } from "./api";

export interface Location {
    id: number;
    name: string;
    latitude: number | null;
    longitude: number | null;
    location: {
        id: number;
    } | null;
    curator: {
        id: number;
    };
}

export async function getAllLocation() {
    const response = await apiFetch(`${API_URL}/location`);

    if (!response.ok) {
        throw new Error("Erro ao buscar locations");
    }

    return response.json();
}

export async function getLocationById(id: number) {
    const response = await apiFetch(`${API_URL}/location/id=${encodeURIComponent(id)}`);

    if (!response.ok) {
        throw new Error("Erro ao buscar location");
    }

    return response.json();
}

export async function getLocationByName(name: string) {
    const response = await apiFetch(`${API_URL}/location/name=${encodeURIComponent(name)}`);

    if (!response.ok) {
        throw new Error("Erro ao buscar locations");
    }

    return response.json();
}

export async function getLocationsByCurator(curatorId: number) {
    const response = await apiFetch(`${API_URL}/location/curator=${encodeURIComponent(curatorId)}`);

    if (!response.ok) {
        throw new Error("Erro ao buscar location");
    }
    return response.json();
}

export async function createLocation(
    name: string,
    latitude: number | null,
    longitude: number | null,
    location?: number,
    curatorId?: number
): Promise<Location> {
    const response = await apiFetch(`${API_URL}/location`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            name,
            latitude,
            longitude,
            location: location != null ? { id: location } : null,
            curator: curatorId != null ? { id: curatorId } : null
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
    latitude: number | null,
    longitude: number | null,
    location?: number,
    curatorId?: number
): Promise<Location> {
    const response = await apiFetch(`${API_URL}/location`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            id,
            name,
            latitude,
            longitude,
            location: location != null ? { id: location } : null,
            curator: curatorId != null ? { id: curatorId } : null
        }),
    });

    if (!response.ok) {
        throw new Error("Erro ao atualizar location");
    }

    return response.json();
}

export async function deleteLocation(id: number) {
    const response = await apiFetch(`${API_URL}/location/${encodeURIComponent(id)}`, {
        method: "DELETE",
    });

    if (!response.ok) {
        throw new Error("Erro ao excluir location");
    }

    if (response.status === 204) {
        return;
    }
}
