import { useState, type SyntheticEvent } from 'react'
import { Link, useNavigate } from 'react-router-dom'

import { authenticate } from '../services/loginService'
import { useAuth } from '../context/AuthContext'

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
        <div className="container mt-5">
            <h1>Login</h1>
            <form onSubmit={submitLogin}>
                <div className="mb-3 pt-4">
                    <label htmlFor="email" className="form-label">
                        Digite seu e-mail:
                    </label><br/>
                    <input
                        type="text"
                        className="form-control"
                        id="email"
                        value={email}
                        onChange={(event) => setEmail(event.target.value)}
                    />
                </div>
                <div className="mb-3 pt-4">
                    <label htmlFor="password" className="form-label">
                        Digite sua senha:
                    </label><br/>
                    <input
                        type="password"
                        className="form-control"
                        id="password"
                        value={password}
                        onChange={(event) => setPassword(event.target.value)}
                    />
                </div>
                <button type="submit" className="btn btn-primary">
                    Login
                </button>
            </form>

            <hr className="my-5" />

            <h3>Não está cadastrado? </h3>
            <Link to="/createCurator">Fazer cadastro</Link>
        </div>
    )
}

export default Login