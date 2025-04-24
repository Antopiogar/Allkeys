DELIMITER $$

CREATE PROCEDURE CreazioneOrdine (
    IN p_IdUtente INT,
    INOUT last_Id INT
)
BEGIN
    DECLARE exit handler for SQLEXCEPTION 
        BEGIN 
            ROLLBACK;
        END;

    START TRANSACTION;

    INSERT INTO Ordine (dataAcquisto, conferma, fkUtente) 
    VALUES (now(),false, p_IdUtente);

    SET last_Id = LAST_INSERT_ID();

    COMMIT;
END $$

DELIMITER ;
