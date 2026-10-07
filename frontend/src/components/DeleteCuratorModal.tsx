import '../styles/DeleteCuratorModal.css'

interface DeleteCuratorModalProps {
  isOpen: boolean;
  onClose: () => void;
  onDelete: () => void;
}

function DeleteCuratorModal({ isOpen, onClose, onDelete }: DeleteCuratorModalProps){
    if (!isOpen) return null;

    return (
        <div
            className="logout-overlay"
            onClick={onClose}
        >
            <div
                className="logout-modal"
                onClick={event => event.stopPropagation()}
            >
                <i className="bi bi-exclamation-triangle settings-warning-icon"></i>
                <h3>
                    Excluir conta?
                </h3>
                <p>
                    Essa ação não pode ser desfeita.
                    Todos os dados da sua conta serão excluídos.
                </p>
                <div className="logout-buttons">
                    <button
                        className="btn-cancel"
                        onClick={onClose}
                    >
                        Cancelar
                    </button>
                    <button
                        className="btn-confirm"
                        onClick={onDelete}
                    >
                        Excluir conta
                    </button>
                </div>
            </div>
        </div>
    )
}

export default DeleteCuratorModal