import { useState, type SyntheticEvent } from 'react'
import { Link, useNavigate } from 'react-router-dom'

import { authenticate } from '../services/loginService'
import { useAuth } from '../context/AuthContext'

import '../styles/Login.css'

function Login() {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')

    const { login } = useAuth()
    const navigate = useNavigate()

    async function submitLogin(event: SyntheticEvent<HTMLFormElement>) {
        event.preventDefault()

        try {
            const token = await authenticate(email, password)
            login(token)

            alert('Login realizado com sucesso!')
            setEmail('')
            setPassword('')

            navigate('/')

        } catch (error) {
            console.error(error)
            alert('Erro ao realizar o Login')
        }
    }

    return (
        <main className="login-page">
            <section className="login-header">
                <h1>Login</h1>
                <p>Seu arquivo digital de memórias e artefatos culturais.</p>
            </section>

            <section className="login-card">
                <form onSubmit={submitLogin} className="login-form">
                    <div className="login-field">
                        <label htmlFor="email">
                            E-mail
                        </label>

                        <input
                            type="email"
                            id="email"
                            placeholder="exemplo@email.com"
                            value={email}
                            onChange={(event) => setEmail(event.target.value)}
                            required
                        />
                    </div>

                    <div className="login-field">
                        <div className="login-password-header">
                            <label htmlFor="password">
                                Senha
                            </label>
                        </div>

                        <input
                            type="password"
                            id="password"
                            value={password}
                            onChange={(event) => setPassword(event.target.value)}
                            required
                        />

                        <div className="login-password-bottom">
                            <span className="forgot-password">
                                Esqueci minha senha
                            </span>
                        </div>
                    </div>

                    <button type="submit" className="login-button">
                        ENTRAR
                        <i className="bi bi-arrow-right"></i>
                    </button>
                </form>
            </section>

            <div className="login-register">
                <span>Não tem uma conta?</span>

                <Link to="/createCurator">
                    Criar nova conta
                </Link>
            </div>
        </main>
    )
}

export default Login