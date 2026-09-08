import { useState, type SyntheticEvent } from 'react'
import { useNavigate } from 'react-router-dom'

import { createCurator } from '../services/curatorService'

function CreateCurator() {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [name, setName] = useState('')
    const [gender, setGender] = useState('')
    const [phone, setPhone] = useState('')
    const [locationId, setLocationId] = useState('')

    const navigate = useNavigate()

    async function submit(event: SyntheticEvent<HTMLFormElement>) {
        event.preventDefault()
        try {
            await createCurator(
                email,
                password,
                name,
                gender,
                phone,
                locationId === ''
                    ? null
                    : Number(locationId)
            )
            alert('Cadastro realizado com sucesso!')
            navigate('/login')
        } catch (error) {
            console.error(error)
            alert('Erro ao realizar cadastro')
        }
    }

    return (
        <div className="container mt-5">
            <h1>Criar conta</h1>
            <form onSubmit={submit}>
                <div className="mb-3">
                    <label htmlFor="email" className="form-label">
                        E-mail:
                    </label>
                    <input
                        type="email"
                        className="form-control"
                        id="email"
                        value={email}
                        onChange={(event) =>
                            setEmail(event.target.value)
                        }
                        required
                    />
                </div>
                <div className="mb-3">
                    <label htmlFor="password" className="form-label">
                        Senha:
                    </label>
                    <input
                        type="password"
                        className="form-control"
                        id="password"
                        value={password}
                        onChange={(event) =>
                            setPassword(event.target.value)
                        }
                        required
                    />
                </div>
                <div className="mb-3">
                    <label htmlFor="name" className="form-label">
                        Nome
                    </label>
                    <input
                        type="text"
                        className="form-control"
                        id="name"
                        value={name}
                        onChange={(event) =>
                            setName(event.target.value)
                        }
                        required
                    />
                </div>
                <div className="mb-3">
                    <label htmlFor="gender" className="form-label">
                        Gênero
                    </label>
                    <select
                        className="form-select"
                        id="gender"
                        value={gender}
                        onChange={(event) =>
                            setGender(event.target.value)
                        }
                        required
                    >
                        <option value="MALE">
                            Masculino
                        </option>
                        <option value="FEMALE">
                            Feminino
                        </option>
                    </select>
                </div>
                <div className="mb-3">
                    <label htmlFor="phone" className="form-label"
                    >
                        Telefone
                    </label>
                    <input
                        type="tel"
                        className="form-control"
                        id="phone"
                        value={phone}
                        onChange={(event) =>
                            setPhone(event.target.value)
                        }
                        required
                    />
                </div>
                <div className="mb-3">
                    <label htmlFor="location" className="form-label">
                        ID da localização
                    </label>
                    <input
                        type="number"
                        className="form-control"
                        id="location"
                        value={locationId}
                        onChange={(event) =>
                            setLocationId(event.target.value)
                        }
                    />
                    <div className="form-text">
                        Opcional. Deixe vazio caso não queira
                        cadastrar uma localização.
                    </div>
                </div>
                <button
                    type="submit"
                    className="btn btn-primary"
                >
                    Criar conta
                </button>
            </form>
        </div>
    )
}

export default CreateCurator