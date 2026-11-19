import '../styles/DeleteCheckModal.css'

interface DeleteCheckModalProps {
    itemName: string
    itemType: string
    deleting?: boolean
    onCancel: () => void
    onConfirm: () => void
}

export default function DeleteCheckModal({
    itemName,
    itemType,
    deleting = false,
    onCancel,
    onConfirm
}: DeleteCheckModalProps) {
    return (
        <div className="delete-modal-overlay">
            <div className="delete-modal">
                <div className="delete-modal-icon">
                    <i className="bi bi-trash"></i>
                </div>

                <h2>Excluir {itemType}?</h2>

                <p>
                    Tem certeza de que deseja excluir
                    <strong> "{itemName}"</strong>?
                </p>

                <p className="delete-modal-warning">
                    Essa ação não poderá ser desfeita.
                </p>

                <div className="delete-modal-actions">
                    <button
                        type="button"
                        className="delete-modal-cancel"
                        onClick={onCancel}
                        disabled={deleting}
                    >
                        Cancelar
                    </button>

                    <button
                        type="button"
                        className="delete-modal-confirm"
                        onClick={onConfirm}
                        disabled={deleting}
                    >
                        {deleting ? 'Excluindo...' : `Excluir ${itemType.toLowerCase()}`}
                    </button>
                </div>
            </div>
        </div>
    )
}
