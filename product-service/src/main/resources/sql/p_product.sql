alter table p_product
    auto_increment = 1;

DELIMITER //

CREATE PROCEDURE InsertProducts()
BEGIN
    DECLARE i INT DEFAULT 1;

    WHILE i <= 170
        DO
            INSERT INTO p_product (created_at, company_id, is_deleted, name, quantity)
            VALUES ('2025-03-30 00:00:00', i, 0, CONCAT('상품', i), 1000);
            SET i = i + 1;
        END WHILE;
END//

DELIMITER ;

CALL InsertProducts();
