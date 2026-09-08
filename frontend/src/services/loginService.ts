export async function authenticate(email:string, password:string) {
    const response = await fetch('http://localhost:8080/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      email: email,
      password: password,
    }),
  })

    if (!response.ok) {
    throw new Error("Erro ao reakizar login");
  }

  return response.text();
}