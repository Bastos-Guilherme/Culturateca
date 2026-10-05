import '../styles/NewItem.css'

export default function NewItem() {
    return(
        <button className='button'>
            <div className="icon">
                <i className='bi bi-plus' style={{fontSize: '32px'}}></i>
            </div>
            <div className="btn-content">
                <h3>Inserir novo item.</h3>
                <p>Cadastrar livros, mídias ou relíquias</p>
            </div>
            <div className="icon">
                <i className="bi bi-arrow-right ms-2"></i>
            </div>
        </button>
    )
}