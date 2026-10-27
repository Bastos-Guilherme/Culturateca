
import { apiFetch } from "./api";

const API_URL = "http://localhost:8080/item-registration";

export interface ItemRegistrationRequest {
    categoryId: number;
    name: string;
    property: Record<number, string>;
    locationId: number | null;
    collectionId: number | null;
    hiddenProperties: number[];
}

export async function registerItem(
    request: ItemRegistrationRequest
) {
    const response = await apiFetch(API_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(request),
    });

    if (!response.ok) {
        const message = await response.text();

        throw new Error(
            message || "Não foi possível cadastrar o item."
        );
    }

    return response.json();
}
