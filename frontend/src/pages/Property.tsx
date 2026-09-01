import '../styles/Property.css'
import { useEffect, useState, type SyntheticEvent } from 'react'
import { createProperty, deleteProperty, getAllProperty, getPropertyById, getPropertyByName, updateProperty } from '../services/propertyService'

interface Property {
  id: number
  name: string
}

function Property() {
  const [id, setId] = useState<number>(0)
  const [name, setName] = useState('')
  const [properties, setProperties] = useState<Property[]>([])
  const [searchResults, setSearchResults] = useState<Property[]>([])
  const [searchType, setSearchType] = useState<'id' | 'name'>('id')

  async function loadProperties() {
    try {
      const data = await getAllProperty()
      setProperties(data)
    } catch (error) {
      console.error(error)
      alert('Erro ao buscar properties')
    }
  }

  useEffect(() => {
    loadProperties()
  }, [])

  async function createProp(event: SyntheticEvent<HTMLFormElement>) {
    event.preventDefault()

    try {
      await createProperty(name)

      alert('Property cadastrada!')
      setName('')

      loadProperties()

    } catch (error) {
      console.error(error)
      alert('Erro ao cadastrar property')
      loadProperties()
    }
  }

  async function search(event: SyntheticEvent<HTMLFormElement>) {
    event.preventDefault()

    try {
      if (searchType === 'id') {
        const data = await getPropertyById(id)
        setSearchResults([data])
      } else {
        const data = await getPropertyByName(name)
        setSearchResults(data)
      }
    } catch (error) {
      console.error(error)
      alert('Property não encontrada')
    }
  }

  async function updateProp(event: SyntheticEvent<HTMLFormElement>) {
    event.preventDefault()

    try {
      await updateProperty(id, name)

      alert('Property atualizada!')
      setId(0)
      setName('')

      loadProperties()

    } catch (error) {
      console.error(error)
      alert('Erro ao atualizar property')
      loadProperties()
    }
  }

  async function deleteProp(event: SyntheticEvent<HTMLFormElement>) {
    event.preventDefault()

    try{
      await deleteProperty(id)

      alert('Property Deletada')
      setId(0)

      loadProperties()

    } catch (error) {
      console.error(error)
      alert('Erro ao deletar property')
      loadProperties()
    }
  }

  return (
    <div className="container mt-5">
      <h1>Cadastro de Property</h1>

      <form onSubmit={createProp}>
        <div className="mb-3">
          <label htmlFor="name" className="form-label">
            Nome:
          </label>
          <input
            type="text"
            className="form-control"
            id="name"
            value={name}
            onChange={(event) => setName(event.target.value)}
          />
        </div>

        <button type="submit" className="btn btn-primary">
          Cadastrar
        </button>
      </form>

      <hr className="my-5" />

      <div className="d-flex justify-content-between align-items-center mb-3">
        <h2>Properties cadastradas</h2>
        <button className="btn btn-secondary" onClick={loadProperties}>
          Atualizar
        </button>
      </div>

      <table className="table table-striped table-hover">
        <thead>
          <tr>
            <th>ID</th>
            <th>Nome</th>
          </tr>
        </thead>
        <tbody>
          {properties.map((property) => (
            <tr key={property.id}>
              <td>{property.id}</td>
              <td>{property.name}</td>
            </tr>
          ))}
        </tbody>
      </table>

      <hr className="my-5" />

      <h1>Pesquisar</h1>

      <form onSubmit={search}>
        <div className="mb-3">
          <label htmlFor="searchType" className="form-label">
            Pesquisar por:
          </label>

          <select
            id="searchType"
            className="form-select"
            value={searchType}
            onChange={(event) =>
              setSearchType(event.target.value as 'id' | 'name')
            }
          >
            <option value="id">ID</option>
            <option value="name">Nome</option>
          </select>
        </div>

        {searchType === 'id' ? (
          <div className="mb-3">
            <label htmlFor="id" className="form-label">
              ID:
            </label>

            <input
              type="number"
              className="form-control"
              id="id"
              value={id}
              onChange={(event) => setId(Number(event.target.value))}
            />
          </div>
        ) : (
          <div className="mb-3">
            <label htmlFor="name" className="form-label">
              Nome:
            </label>

            <input
              type="text"
              className="form-control"
              id="name"
              value={name}
              onChange={(event) => setName(event.target.value)}
            />
          </div>
        )}

        <button type="submit" className="btn btn-primary">
          Buscar
        </button>
      </form>

      {searchResults.length > 0 && (
        <div className="mt-4">
          <h3>Properties encontradas</h3>

          <table className="table table-striped table-hover">
            <thead>
              <tr>
                <th>ID</th>
                <th>Nome</th>
              </tr>
            </thead>

            <tbody>
              {searchResults.map((property) => (
                <tr key={property.id}>
                  <td>{property.id}</td>
                  <td>{property.name}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      <hr className="my-5" />

      <h2>Atualizar proprerty</h2>

      <form onSubmit={updateProp}>
        <div className="mb-3">
          <label htmlFor="updateId" className="form-label">
            ID:
          </label>

          <input
            type="number"
            className="form-control"
            id="updateId"
            value={id}
            onChange={(event) => setId(Number(event.target.value))}
          />
        </div>

        <div className="mb-3">
          <label htmlFor="updateName" className="form-label">
            Nome:
          </label>

          <input
            type="text"
            className="form-control"
            id="updateName"
            value={name}
            onChange={(event) => setName(event.target.value)}
          />
        </div>

        <button type="submit" className="btn btn-warning">
          Atualizar
        </button>
      </form>

      <hr className="my-5" />
        
      <h2>Deletar Property</h2>

      <form onSubmit={deleteProp}>
        <div className="mb-3">
          <label htmlFor="name" className="form-label">
            ID:
          </label>
          <input
            type="number"
            className="form-control"
            id="updateId"
            value={id}
            onChange={(event) => setId(Number(event.target.value))}
          />
        </div>

        <button type="submit" className="btn btn-primary">
          Deletar
        </button>
      </form>

    </div>
  )
}

export default Property
