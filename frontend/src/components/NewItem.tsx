import { useNavigate } from 'react-router-dom'
import '../styles/NewItem.css'

interface NewItemProps {
    icon: string
    title: string
    description: string
    route: string
    className?: string
}

export default function NewItem({
    icon,
    title,
    description,
    route,
    className = ''
}: NewItemProps) {

    const navigate = useNavigate()

    return (
        <button
            className={`button ${className}`}
            onClick={() => navigate(route)}
        >
            <div className="icon">
                <i
                    className={`bi ${icon}`}
                ></i>
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