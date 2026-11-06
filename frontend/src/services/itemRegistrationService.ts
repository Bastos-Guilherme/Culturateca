
import { apiFetch } from "./api";

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
    const response = await apiFetch("http://localhost:8080/item-registration", {
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
