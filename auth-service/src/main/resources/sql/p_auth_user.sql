INSERT INTO p_auth_user (user_id, created_at, created_by, deleted_at, deleted_by, updated_at, updated_by, password,
                         role_id)
VALUES ('master0001', NOW(), 'admin', NULL, NULL, NOW(), 'admin',
        '$2a$10$/OuXtJxHEg7aW6ocDRVzbuzxAs85cAzyqB5oGw19Xw2nRshLjXk8u', 1);

DELIMITER $$

CREATE PROCEDURE InsertHubAdmin()
BEGIN
    DECLARE i INT DEFAULT 1;

    WHILE i <= 17
        DO
            INSERT INTO p_auth_user (user_id, created_at, created_by, deleted_at, deleted_by, updated_at, updated_by,
                                     password, role_id)
            VALUES (CONCAT('hubadmin', i),
                    NOW(),
                    'admin',
                    NULL,
                    NULL,
                    NOW(),
                    'admin',
                    '$2a$10$/OuXtJxHEg7aW6ocDRVzbuzxAs85cAzyqB5oGw19Xw2nRshLjXk8u',
                    2);
            SET i = i + 1;
        END WHILE;
END$$

DELIMITER ;

CALL InsertHubAdmin();
DROP PROCEDURE IF EXISTS InsertHubAdmin;


DELIMITER $$

CREATE PROCEDURE InsertCompanyAdmin()
BEGIN
    DECLARE i INT DEFAULT 1;

    WHILE i <= 170
        DO
            INSERT INTO p_auth_user (user_id, created_at, created_by, deleted_at, deleted_by, updated_at, updated_by,
                                     password, role_id)
            VALUES (CONCAT('companyadmin', i),
                    NOW(),
                    'admin',
                    NULL,
                    NULL,
                    NOW(),
                    'admin',
                    '$2a$10$/OuXtJxHEg7aW6ocDRVzbuzxAs85cAzyqB5oGw19Xw2nRshLjXk8u',
                    3);
            SET i = i + 1;
        END WHILE;
END$$

DELIMITER ;

CALL InsertCompanyAdmin();
DROP PROCEDURE IF EXISTS InsertCompanyAdmin;

DELIMITER $$

CREATE PROCEDURE InsertHubDrivers()
BEGIN
    DECLARE i INT DEFAULT 1;

    WHILE i <= 17
        DO
            INSERT INTO p_auth_user (user_id, created_at, created_by, deleted_at, deleted_by, updated_at, updated_by,
                                     password, role_id)
            VALUES (CONCAT('hubdriver', i),
                    NOW(),
                    'admin',
                    NULL,
                    NULL,
                    NOW(),
                    'admin',
                    '$2a$10$/OuXtJxHEg7aW6ocDRVzbuzxAs85cAzyqB5oGw19Xw2nRshLjXk8u',
                    4);
            SET i = i + 1;
        END WHILE;
END$$

DELIMITER ;

CALL InsertHubDrivers();
DROP PROCEDURE IF EXISTS InsertHubDrivers;

DELIMITER $$

CREATE PROCEDURE InsertCompanyDrivers()
BEGIN
    DECLARE i INT DEFAULT 1;

    WHILE i <= 170
        DO
            INSERT INTO p_auth_user (user_id, created_at, created_by, deleted_at, deleted_by, updated_at, updated_by,
                                     password, role_id)
            VALUES (CONCAT('companydriver', i),
                    NOW(),
                    'admin',
                    NULL,
                    NULL,
                    NOW(),
                    'admin',
                    '$2a$10$/OuXtJxHEg7aW6ocDRVzbuzxAs85cAzyqB5oGw19Xw2nRshLjXk8u',
                    4);
            SET i = i + 1;
        END WHILE;
END$$

DELIMITER ;

CALL InsertCompanyDrivers();
DROP PROCEDURE IF EXISTS InsertCompanyDrivers;