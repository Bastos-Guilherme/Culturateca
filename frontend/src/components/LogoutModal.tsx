import '../styles/LogoutModal.css';

interface logoutModalProps {
    isOpen: boolean;
    onClose: () => void;
    onLogout: () => void;
}

export default function LogoutModal({ isOpen, onClose, onLogout }: logoutModalProps) {
    if (!isOpen) return null;

    return (
        <div className="logout-overlay">
            <div className="logout-modal">
                <h3>Confirmar Logout</h3>
                <p>Tem certeza que deseja sair?</p>
                <div className="logout-buttons">
                    <button className="btn-cancel" onClick={onClose}>
                        Cancelar
                    </button>
                    <button className="btn-confirm" onClick={onLogout}>
                        Sair
                    </button>
                </div>
            </div>
        </div>
    );
}