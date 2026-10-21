interface ApiMessage {
    message: string
}

interface RequestCodeResponse extends ApiMessage {
    challengeId: string
}

async function readResponse<T>(response: Response): Promise<T> {
    const data = await response.json().catch(() => ({}))

    if (!response.ok) {
        throw new Error(
            data.message ?? 'Ocorreu um erro. Tente novamente.'
        )
    }

    return data as T
}

export async function requestPasswordRecoveryCode(
    email: string
): Promise<RequestCodeResponse> {
    const response = await fetch(`http://localhost:8080/password-recovery/request-code`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email })
    })

    return readResponse<RequestCodeResponse>(response)
}

export async function verifyPasswordRecoveryCode(
    challengeId: string,
    code: string
): Promise<ApiMessage> {
    const response = await fetch(`http://localhost:8080/password-recovery/verify-code`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ challengeId, code })
    })

    return readResponse<ApiMessage>(response)
}

export async function resetPassword(
    challengeId: string,
    code: string,
    newPassword: string
): Promise<ApiMessage> {
    const response = await fetch(`http://localhost:8080/password-recovery/reset-password`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ challengeId, code, newPassword })
    })

    return readResponse<ApiMessage>(response)
}
