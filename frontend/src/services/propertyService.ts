
import { apiFetch } from "./api";

export interface Property {
  id: number
  name: string
  curator: { id: number } | null
}

export async function getAllProperty(): Promise<Property[]> {
  const response = await apiFetch("http://localhost:8080/property");

  if (!response.ok) {
    throw new Error("Erro ao buscar properties");
  }

  return response.json();
}

export async function getPropertyById(id: number): Promise<Property> {
  const response = await apiFetch(`http://localhost:8080/property/id=${id}`);

  if (!response.ok) {
    throw new Error("Erro ao buscar property");
  }

  return response.json();
}

export async function getPropertyByName(name: string): Promise<Property[]> {
  const response = await apiFetch(
    `http://localhost:8080/property/name=${encodeURIComponent(name)}`
  );

  if (!response.ok) {
    throw new Error("Erro ao buscar property");
  }

  return response.json();
}

export async function createProperty(
  name: string,
  curatorId: number
): Promise<Property> {

  const response = await apiFetch('http://localhost:8080/property', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      name,
      curator: { id: curatorId }
    }),
  });

  if (!response.ok) {
    throw new Error('Erro ao cadastrar property');
  }

  return response.json();
}

export async function updateProperty(
  id: number,
  name: string,
  curatorId: number
): Promise<Property> {
  const response = await apiFetch(`http://localhost:8080/property`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      id,
      name,
      curator: { id: curatorId }
    }),
  });

  if (!response.ok) {
    throw new Error("Erro ao atualizar property");
  }

  return response.json();
}

export async function deleteProperty(id: number) {
  const response = await apiFetch(`http://localhost:8080/property/${id}`, {
    method: "DELETE",
  });

  if (!response.ok) {
    throw new Error("Erro ao excluir property");
  }

  if (response.status === 204) {
    return;
  }
}