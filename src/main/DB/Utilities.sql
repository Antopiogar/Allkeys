
DELIMITER //

CREATE PROCEDURE createOrdine (
    IN p_IdUtente INT
)
BEGIN
    START TRANSACTION;

    INSERT INTO Ordine (dataAcquisto, conferma, fkUtente) 
    VALUES (NOW(), FALSE, p_IdUtente);

    SELECT LAST_INSERT_ID() AS last_Id;

    COMMIT;
END;
//

DELIMITER ;

DROP VIEW IF EXISTS ViewCatalogo;
CREATE VIEW ViewCatalogo as 
SELECT DISTINCT 
	a.*
FROM
	articolo as a
	join chiave as c on a.idArticolo = c.fkArticolo where c.fkOrdine is null;
	
DROP VIEW IF EXISTS N_Chiavi_Disponibili;
CREATE VIEW N_Chiavi_Disponibili AS
SELECT 
    c.FkArticolo AS idArticolo,
    COUNT(*) AS qta
FROM
   chiave as c
JOIN articolo as a ON c.FkArticolo = a.idArticolo
WHERE
    c.FkOrdine IS NULL
GROUP BY c.FkArticolo, a.nome;