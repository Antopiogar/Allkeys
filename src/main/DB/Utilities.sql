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
SELECT DISTINCT
    a.*
FROM
    Articolo AS a
JOIN Chiave AS c ON a.idArticolo = c.FkArticolo
WHERE c.FkOrdine IS NULL
order by a.nome asc;

DROP VIEW IF EXISTS N_Chiavi_Disponibili;
CREATE VIEW N_Chiavi_Disponibili AS
SELECT
    c.FkArticolo AS idArticolo,
    COUNT(*) AS qta
FROM
    Chiave AS c
JOIN Articolo AS a ON c.FkArticolo = a.idArticolo
WHERE
    c.FkOrdine IS NULL
GROUP BY c.FkArticolo, a.nome;
