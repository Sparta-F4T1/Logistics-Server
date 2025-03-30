INSERT INTO p_driver (driver_id, created_at, updated_at, arrival_hub_id, depart_hub_id, is_deleted, status, type)
VALUES ('hubdriver1', NOW(), NOW(), 1, 3, 0, 'AVAILABLE', 'HUB'),
       ('hubdriver2', NOW(), NOW(), 2, 3, 0, 'AVAILABLE', 'HUB'),
       ('hubdriver3', NOW(), NOW(), 3, 5, 0, 'AVAILABLE', 'HUB'),
       ('hubdriver4', NOW(), NOW(), 4, 5, 0, 'AVAILABLE', 'HUB'),
       ('hubdriver5', NOW(), NOW(), 5, 8, 0, 'AVAILABLE', 'HUB'),
       ('hubdriver6', NOW(), NOW(), 6, 3, 0, 'AVAILABLE', 'HUB'),
       ('hubdriver7', NOW(), NOW(), 7, 8, 0, 'AVAILABLE', 'HUB'),
       ('hubdriver8', NOW(), NOW(), 8, 3, 0, 'AVAILABLE', 'HUB'),
       ('hubdriver9', NOW(), NOW(), 9, 5, 0, 'AVAILABLE', 'HUB'),
       ('hubdriver10', NOW(), NOW(), 10, 8, 0, 'AVAILABLE', 'HUB'),
       ('hubdriver11', NOW(), NOW(), 11, 3, 0, 'AVAILABLE', 'HUB'),
       ('hubdriver12', NOW(), NOW(), 12, 8, 0, 'AVAILABLE', 'HUB'),
       ('hubdriver13', NOW(), NOW(), 13, 8, 0, 'AVAILABLE', 'HUB'),
       ('hubdriver14', NOW(), NOW(), 14, 8, 0, 'AVAILABLE', 'HUB'),
       ('hubdriver15', NOW(), NOW(), 15, 8, 0, 'AVAILABLE', 'HUB'),
       ('hubdriver16', NOW(), NOW(), 16, 5, 0, 'AVAILABLE', 'HUB'),
       ('hubdriver17', NOW(), NOW(), 17, 5, 0, 'AVAILABLE', 'HUB');


DELIMITER $$

CREATE PROCEDURE InsertCompanyDrivers()
BEGIN
    DECLARE i INT DEFAULT 1; -- depart_hub_id
    DECLARE j INT DEFAULT 1; -- 드라이버 개수 (10개씩)

    WHILE i <= 17
        DO
            SET j = 1; -- 각 depart_hub_id에 대해 드라이버 10개 생성

            WHILE j <= 10
                DO
                    INSERT INTO p_driver (driver_id, created_at, updated_at, arrival_hub_id, depart_hub_id, is_deleted,
                                          status, type)
                    VALUES (CONCAT('companydriver', (i - 1) * 10 + j),
                            NOW(),
                            NOW(),
                            i,
                            i,
                            0,
                            'AVAILABLE',
                            'COMPANY');
                    SET j = j + 1;
                END WHILE;

            SET i = i + 1;
        END WHILE;
END$$

DELIMITER ;

-- 프로시저 호출
CALL InsertCompanyDrivers();

-- 프로시저 삭제
DROP PROCEDURE IF EXISTS InsertCompanyDrivers;