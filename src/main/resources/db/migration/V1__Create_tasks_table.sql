-- Creazione della tabella tasks
CREATE TABLE tasks (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    priority VARCHAR(20) NOT NULL DEFAULT 'MEDIUM',
    due_date TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Creazione degli indici per migliorare le performance
CREATE INDEX idx_tasks_status ON tasks(status);
CREATE INDEX idx_tasks_priority ON tasks(priority);
CREATE INDEX idx_tasks_due_date ON tasks(due_date);
CREATE INDEX idx_tasks_created_at ON tasks(created_at);
CREATE INDEX idx_tasks_title ON tasks USING gin(to_tsvector('italian', title));

-- Creazione di un trigger per aggiornare automaticamente updated_at
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_tasks_updated_at 
    BEFORE UPDATE ON tasks 
    FOR EACH ROW 
    EXECUTE FUNCTION update_updated_at_column();

-- Aggiunta di commenti per documentare la tabella
COMMENT ON TABLE tasks IS 'Tabella per memorizzare le task del task manager';
COMMENT ON COLUMN tasks.id IS 'Identificatore univoco della task';
COMMENT ON COLUMN tasks.title IS 'Titolo della task';
COMMENT ON COLUMN tasks.description IS 'Descrizione dettagliata della task';
COMMENT ON COLUMN tasks.status IS 'Stato della task: PENDING, IN_PROGRESS, COMPLETED, CANCELLED';
COMMENT ON COLUMN tasks.priority IS 'Priorità della task: LOW, MEDIUM, HIGH, URGENT';
COMMENT ON COLUMN tasks.due_date IS 'Data di scadenza della task';
COMMENT ON COLUMN tasks.created_at IS 'Data e ora di creazione della task';
COMMENT ON COLUMN tasks.updated_at IS 'Data e ora dell\'ultimo aggiornamento della task';
