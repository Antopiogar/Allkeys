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
CREATE VIEW ViewCatalogo AS
SELECT DISTINCT a.*, c.*
FROM articolo AS a
JOIN chiave AS c ON a.idArticolo = c.fkArticolo
WHERE c.fkOrdine IS NULL;


DROP VIEW IF EXISTS N_Chiavi_Disponibili;
create view N_Chiavi_Disponibili as
    SELECT
        count(*) as disponibilità,
        c.idChiave,
        a.nome
    FROM 
        chiave as c
        JOIN articolo as a
            on c.FkArticolo = a.idArticolo
    WHERE
        c.FkOrdine is null
    GROUP by 
        c.FkArticolo