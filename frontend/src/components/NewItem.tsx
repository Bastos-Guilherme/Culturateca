import { useNavigate } from 'react-router-dom'
import '../styles/NewItem.css'

interface NewItemProps {
    icon: string
    title: string
    description: string
    route?: string
    className?: string
    onClick?: () => void
}

function NewItem({
    icon,
    title,
    description,
    route,
    className = '',
    onClick
}: NewItemProps) {
    const navigate = useNavigate()
    function handleClick() {
        if (onClick) {
            onClick()
            return
        }

        if (route) {
            navigate(route)
        }
    }

    return (
        <button
            type="button"
            className={`button ${className}`}
            onClick={handleClick}
        >
            <div className="icon">
                <i className={`bi ${icon}`}></i>
            </div>
            <div className="btn-content">
                <h3>{title}</h3>
                <p>{description}</p>
            </div>
            <div className="icon">
                <i className="bi bi-arrow-right ms-2"></i>
            </div>
        </button>
    )
}

export default NewItem