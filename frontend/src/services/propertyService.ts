import { apiFetch } from "./api";

export async function getAllProperty() {
  const response = await apiFetch("http://localhost:8080/property");

  if (!response.ok) {
    throw new Error("Erro ao buscar properties");
  }

  return response.json();
}

export async function getPropertyById(id: number) {
  const response = await apiFetch(`http://localhost:8080/property/id=${id}`);

  if (!response.ok) {
    throw new Error("Erro ao buscar property");
  }

  return response.json();
}

export async function getPropertyByName(name: string) {
  const response = await apiFetch(`http://localhost:8080/property/name=${name}`);

  if (!response.ok) {
    throw new Error("Erro ao buscar property");
  }

  return response.json();
}

export async function createProperty(name: string) {

  const response = await apiFetch('http://localhost:8080/property', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      name: name,
    }),
  })

  if (!response.ok) {
    throw new Error('Erro ao cadastrar property')
  }

  return response.json()
}

export async function updateProperty(id: number, name: string) {
  const response = await apiFetch(`http://localhost:8080/property`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      id: id,
      name: name,
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