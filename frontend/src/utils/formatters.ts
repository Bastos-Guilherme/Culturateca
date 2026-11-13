export function formatPhone(value: string): string {
    const numbers = value.replace(/\D/g, '').slice(0, 11)

    if (numbers.length === 0) return ''
    if (numbers.length <= 2) return `(${numbers}`
    if (numbers.length <= 6) {
        return `(${numbers.slice(0, 2)}) ${numbers.slice(2)}`
    }
    if (numbers.length <= 10) {
        return `(${numbers.slice(0, 2)}) ${numbers.slice(2, 6)}-${numbers.slice(6)}`
    }

    return `(${numbers.slice(0, 2)}) ${numbers.slice(2, 7)}-${numbers.slice(7)}`
}

export function validatePhone(value: string): boolean {
    const numbers = value.replace(/\D/g, '')

    return numbers.length === 0 ||
        numbers.length === 10 ||
        numbers.length === 11
}

export function validateEmail(value: string): boolean {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value.trim())
}