-- Inserimento di dati di esempio per testare l'applicazione
INSERT INTO tasks (title, description, status, priority, due_date) VALUES
('Configurare ambiente di sviluppo', 'Installare e configurare tutti gli strumenti necessari per lo sviluppo del progetto', 'COMPLETED', 'HIGH', '2024-01-15 18:00:00'),
('Creare database schema', 'Progettare e implementare lo schema del database per il task manager', 'COMPLETED', 'HIGH', '2024-01-16 12:00:00'),
('Implementare API REST', 'Sviluppare le API REST per la gestione delle task', 'IN_PROGRESS', 'HIGH', '2024-01-20 17:00:00'),
('Creare interfaccia utente', 'Sviluppare l interfaccia web per la gestione delle task', 'PENDING', 'MEDIUM', '2024-01-25 16:00:00'),
('Implementare autenticazione', 'Aggiungere sistema di login e gestione utenti', 'PENDING', 'MEDIUM', '2024-01-30 15:00:00'),
('Scrivere documentazione', 'Creare documentazione tecnica e manuale utente', 'PENDING', 'LOW', '2024-02-05 14:00:00'),
('Test di integrazione', 'Eseguire test completi dell applicazione', 'PENDING', 'MEDIUM', '2024-02-10 13:00:00'),
('Deploy in produzione', 'Configurare e pubblicare l applicazione in produzione', 'PENDING', 'HIGH', '2024-02-15 12:00:00'),
('Ottimizzare performance', 'Analizzare e migliorare le performance dell applicazione', 'PENDING', 'LOW', '2024-02-20 11:00:00'),
('Backup e monitoraggio', 'Implementare sistema di backup e monitoraggio', 'PENDING', 'MEDIUM', '2024-02-25 10:00:00'),
('Task urgente di esempio', 'Questa è una task con priorità urgente per testare il sistema', 'PENDING', 'URGENT', '2024-01-18 20:00:00'),
('Task scaduta di esempio', 'Questa task è già scaduta per testare la funzionalità di ricerca', 'PENDING', 'HIGH', '2024-01-10 15:00:00'),
('Task completata di esempio', 'Questa task è già stata completata', 'COMPLETED', 'MEDIUM', '2024-01-12 14:00:00'),
('Task cancellata di esempio', 'Questa task è stata cancellata', 'CANCELLED', 'LOW', '2024-01-14 13:00:00'),
('Ricerca e analisi', 'Condurre ricerca approfondita sui requisiti del progetto', 'IN_PROGRESS', 'MEDIUM', '2024-01-22 16:30:00');
