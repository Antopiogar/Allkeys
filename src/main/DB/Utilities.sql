DELIMITER $$

CREATE PROCEDURE Create_Order (
    IN p_IdUtente INT,
    IN p_dataAcquisto date,
    IN p_conferma boolean,
    IN p_IdArticolo INT,
    OUT last_Id INT
)
BEGIN
    DECLARE exit handler for sqlexception
        ROLLBACK;  -- Effettua il rollback in caso di errore
    
    START TRANSACTION;  -- Inizia la transazione

    INSERT INTO Ordine (dataAcquisto, conferma, fkUtente) value(
    	p_dataAcquisto,p_conferma,p_IdUtente
    );
   	    -- Recupera l'ID generato
    SET p_last_Id = LAST_INSERT_ID();

    
    COMMIT;  -- Esegui il commit al termine

END $$

DELIMITER ;

DROP VIEW IF EXISTS ViewCatalogo;
CREATE VIEW ViewCatalogo AS
SELECT DISTINCT a.*, c.*
FROM articolo AS a
JOIN chiave AS c ON a.idArticolo = c.fkArticolo
WHERE c.fkOrdine IS NULL;
