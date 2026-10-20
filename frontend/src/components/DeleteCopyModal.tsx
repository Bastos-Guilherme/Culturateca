import "../styles/DeleteCopyModal.css"

interface DeleteCopyModalProps {
    isOpen: boolean
    onClose: () => void
    onDelete: () => void
    deleting?: boolean
}

export default function DeleteCopyModal({
    isOpen,
    onClose,
    onDelete,
    deleting = false
}: DeleteCopyModalProps) {
    if (!isOpen) return null

    return (
        <div
            className="delete-copy-overlay"
            onMouseDown={event => {
                if (
                    event.target === event.currentTarget &&
                    !deleting
                ) {
                    onClose()
                }
            }}
        >
            <section
                className="delete-copy-modal"
                role="dialog"
                aria-modal="true"
                aria-labelledby="delete-copy-title"
            >
                <div className="delete-copy-icon">
                    <i className="bi bi-exclamation-triangle" />
                </div>
                <h2 id="delete-copy-title">
                    Excluir cópia?
                </h2>
                <p>
                    Tem certeza de que deseja excluir este exemplar?
                    Essa ação não pode ser desfeita.
                </p>

                <div className="delete-copy-buttons">
                    <button
                        type="button"
                        className="delete-copy-cancel"
                        onClick={onClose}
                        disabled={deleting}
                    >
                        Cancelar
                    </button>
                    <button
                        type="button"
                        className="delete-copy-confirm"
                        onClick={onDelete}
                        disabled={deleting}
                    >
                        <i className="bi bi-trash" />
                        {deleting ? "Excluindo..." : "Excluir cópia"}
                    </button>
                </div>
            </section>
        </div>
    )
}