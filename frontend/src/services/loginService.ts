export interface LoginMfaResponse {
  mfaRequired: true
  challengeId: string
  message: string
}

export type LoginResponse = string | LoginMfaResponse

export async function authenticate(email: string, password: string): Promise<LoginResponse> {
  const response = await fetch('http://localhost:8080/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      email,
      password,
    }),
  })

  if (!response.ok) {
    let message = 'Erro ao realizar login.'

    try {
      const data = await response.json()
      message = data.message || message
    } catch { }

    throw new Error(message)
  }

  const contentType = response.headers.get('content-type') || ''

  if (contentType.includes('application/json')) {
    return await response.json() as LoginMfaResponse
  }

  return await response.text()
}

export async function verifyLoginCode(challengeId: string, code: string): Promise<string> {
  const response = await fetch('http://localhost:8080/login/verify-code', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      challengeId,
      code,
    }),
  })

  if (!response.ok) {
    let message = 'Não foi possível verificar o código.'

    try {
      const data = await response.json()
      message = data.message || message
    } catch {}

    throw new Error(message)
  }

  return response.text()
}
