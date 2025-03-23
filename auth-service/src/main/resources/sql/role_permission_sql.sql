-- 역할(role) 데이터 삽입
INSERT INTO p_role (role_id, name, description, created_at, created_by, updated_at, updated_by)
VALUES (1, 'MASTER_ADMIN', '마스터 관리자', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
       (2, 'HUB_ADMIN', '허브 관리자', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
       (3, 'DELIVERY_PERSONNEL', '배송 담당자', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
       (4, 'COMPANY_PERSONNEL', '업체 담당자', NOW(), 'SYSTEM', NOW(), 'SYSTEM');

-- 권한(permission) 데이터 삽입
INSERT INTO p_permission (permission_id, resource_type, action_type, description, created_at, created_by, updated_at,
                          updated_by)
VALUES
-- HUB 리소스 권한
(1, 'HUB', 'CREATE', '허브 생성 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(2, 'HUB', 'READ', '허브 조회 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(3, 'HUB', 'UPDATE', '허브 수정 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(4, 'HUB', 'DELETE', '허브 삭제 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),

-- HUB_DELIVERY_HISTORY 리소스 권한 (hubDeliveryHistories에 매핑)
(5, 'HUB_DELIVERY_HISTORY', 'CREATE', '허브 배송 이력 생성 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(6, 'HUB_DELIVERY_HISTORY', 'READ', '허브 배송 이력 조회 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(7, 'HUB_DELIVERY_HISTORY', 'UPDATE', '허브 배송 이력 수정 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(8, 'HUB_DELIVERY_HISTORY', 'DELETE', '허브 배송 이력 삭제 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),

-- DRIVER 리소스 권한 (drivers에 매핑)
(9, 'DRIVER', 'CREATE', '드라이버 생성 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(10, 'DRIVER', 'READ', '드라이버 조회 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(11, 'DRIVER', 'UPDATE', '드라이버 수정 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(12, 'DRIVER', 'DELETE', '드라이버 삭제 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),

-- COMPANY 리소스 권한 (companies에 매핑)
(13, 'COMPANY', 'CREATE', '업체 생성 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(14, 'COMPANY', 'READ', '업체 조회 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(15, 'COMPANY', 'UPDATE', '업체 수정 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(16, 'COMPANY', 'DELETE', '업체 삭제 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),

-- PRODUCT 리소스 권한 (products에 매핑)
(17, 'PRODUCT', 'CREATE', '상품 생성 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(18, 'PRODUCT', 'READ', '상품 조회 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(19, 'PRODUCT', 'UPDATE', '상품 수정 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(20, 'PRODUCT', 'DELETE', '상품 삭제 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),

-- ORDER 리소스 권한 (orders에 매핑)
(21, 'ORDER', 'CREATE', '주문 생성 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(22, 'ORDER', 'READ', '주문 조회 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(23, 'ORDER', 'UPDATE', '주문 수정 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(24, 'ORDER', 'DELETE', '주문 삭제 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),

-- DELIVERY 리소스 권한 (deliveries에 매핑)
(25, 'DELIVERY', 'CREATE', '배송 생성 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(26, 'DELIVERY', 'READ', '배송 조회 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(27, 'DELIVERY', 'UPDATE', '배송 수정 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(28, 'DELIVERY', 'DELETE', '배송 삭제 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),

-- SLACK_MESSAGE 리소스 권한 (slack에 매핑)
(29, 'SLACK_MESSAGE', 'CREATE', '슬랙 메시지 생성 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(30, 'SLACK_MESSAGE', 'READ', '슬랙 메시지 조회 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(31, 'SLACK_MESSAGE', 'UPDATE', '슬랙 메시지 수정 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(32, 'SLACK_MESSAGE', 'DELETE', '슬랙 메시지 삭제 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),

-- USER 리소스 권한 (users에 매핑)
(33, 'USER', 'CREATE', '사용자 생성 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(34, 'USER', 'READ', '사용자 조회 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(35, 'USER', 'UPDATE', '사용자 수정 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(36, 'USER', 'DELETE', '사용자 삭제 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM');

-- 역할별 권한 매핑 (role_permission) - MASTER_ADMIN에게 모든 권한 부여
INSERT INTO p_role_permission (role_id, permission_id, created_at, created_by, updated_at, updated_by)
SELECT 1, permission_id, NOW(), 'SYSTEM', NOW(), 'SYSTEM'
FROM p_permission;

-- HUB_ADMIN 권한 설정
INSERT INTO p_role_permission (role_id, permission_id, created_at, created_by, updated_at, updated_by)
VALUES
-- 허브 READ
(2, 2, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
-- 허브 배송 이력 READ
(2, 6, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
-- 드라이버 모든 권한
(2, 9, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(2, 10, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(2, 11, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(2, 12, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
-- 업체 모든 권한
(2, 13, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(2, 14, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(2, 15, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(2, 16, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
-- 상품 모든 권한
(2, 17, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(2, 18, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(2, 19, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(2, 20, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
-- 주문 모든 권한
(2, 21, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(2, 22, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(2, 23, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(2, 24, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
-- 배송 READ, UPDATE, DELETE
(2, 26, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(2, 27, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(2, 28, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
-- 슬랙 메시지 CREATE
(2, 29, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
-- 사용자 READ
(2, 34, NOW(), 'SYSTEM', NOW(), 'SYSTEM');

-- DELIVERY_PERSONNEL 권한 설정
INSERT INTO p_role_permission (role_id, permission_id, created_at, created_by, updated_at, updated_by)
VALUES
-- 허브 READ
(3, 2, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
-- 허브 배송 이력 READ
(3, 6, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
-- 드라이버 READ
(3, 10, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
-- 업체 READ
(3, 14, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
-- 상품 READ
(3, 18, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
-- 주문 CREATE, READ
(3, 21, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(3, 22, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
-- 배송 READ, UPDATE
(3, 26, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(3, 27, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
-- 슬랙 메시지 CREATE
(3, 29, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
-- 사용자 READ
(3, 34, NOW(), 'SYSTEM', NOW(), 'SYSTEM');

-- COMPANY_PERSONNEL 권한 설정
INSERT INTO p_role_permission (role_id, permission_id, created_at, created_by, updated_at, updated_by)
VALUES
-- 허브 READ
(4, 2, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
-- 허브 배송 이력 READ
(4, 6, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
-- 업체 READ, UPDATE
(4, 14, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(4, 15, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
-- 상품 CREATE, READ, UPDATE
(4, 17, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(4, 18, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(4, 19, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
-- 주문 CREATE, READ
(4, 21, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
(4, 22, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
-- 배송 READ
(4, 26, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
-- 슬랙 메시지 CREATE
(4, 29, NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
-- 사용자 READ
(4, 34, NOW(), 'SYSTEM', NOW(), 'SYSTEM');