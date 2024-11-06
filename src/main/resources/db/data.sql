

INSERT INTO address (country, city, postcode, street, house_number, info)
VALUES ('USA', 'Chicago', '60601', 'Michigan Ave', 233, NULL),
       ('USA', 'San Francisco', '94103', 'Market Street', 789, NULL),
       ('Germany', 'Frankfurt', '60311', 'Zeil', 45, NULL),
       ('Germany', 'Hamburg', '20095', 'Mönckebergstraße', 7, NULL),
       ('France', 'Paris', '75001', 'Rue de Rivoli', 99, NULL),
       ('Italy', 'Rome', '00184', 'Via Nazionale', 16, NULL),
       ('USA', 'New York', '10001', '5th Avenue', 1, NULL),
       ('USA', 'Los Angeles', '90001', 'Sunset Blvd', 123, NULL),
       ('Germany', 'Berlin', '10115', 'Unter den Linden', 12, NULL),
       ('Germany', 'Munich', '80331', 'Marienplatz', 5, NULL);

INSERT INTO banks (address_id, bank_name, swift)
VALUES (1, 'Bank of America', 'BOFAUS3N'),
       (2, 'Chase Bank', 'CHASUS33'),
       (3, 'Deutsche Bank', 'DEUTDEFF');


INSERT INTO private_info (first_name, last_name, date_of_birth, document_type, document_number, phone, email,
                          address_id)
VALUES ('John', 'Doe', '1985-07-21', 'PASSPORT_EU', 'ID1234567', '+1234567890', 'john.doe@example.com', 1),
       ('Anna', 'Smith', '1990-04-18', 'ID_CARD', 'EU9876543', '+0987654321', 'anna.smith@example.com', 2),
       ('Emma', 'Brown', '1988-09-09', 'PASSPORT_EU', 'NE6543210', '+1230984567', 'emma.brown@example.com', 3),
       ('Liam', 'Johnson', '1992-11-11', 'ID_CARD', 'ID1234987', '+5671239084', 'liam.johnson@example.com', 4),
       ('Olivia', 'Taylor', '1987-05-15', 'PASSPORT_EU', 'AN5678390', '+4560981234', 'olivia.taylor@example.com', 5),
       ('James', 'Williams', '1980-03-25', 'PASSPORT_EU', 'ID5432109', '+1456789123', 'james.williams@example.com', 6),
       ('Sophia', 'Davis', '1991-07-14', 'ID_CARD', 'EU1122334', '+2345678901', 'sophia.davis@example.com', 7),
       ('William', 'Miller', '1978-02-27', 'PASSPORT_EU', 'NE0098765', '+5678901234', 'william.miller@example.com', 8),
       ('Mia', 'Wilson', '1995-10-30', 'PASSPORT_EU', 'ID8765432', '+7890123456', 'mia.wilson@example.com', 9),
       ('Michael', 'Anderson', '1983-12-12', 'PASSPORT_NON_EU', 'AN0987654', '+9012345678', 'michael.anderson@example.com', 10);

-- Пользователи с привязкой к личной информации и ролями
INSERT INTO users (username, password, private_info_id, role, status, manager_id)
VALUES ('johndoe', 'password123', 1, 'ROLE_MANAGER', 'ACTIVE', NULL),
       ('annasmith', 'password234', 2, 'ROLE_MANAGER', 'ACTIVE', NULL),
       ('emmabrown', 'password345', 3, 'ROLE_SUPER_MANAGER', 'ACTIVE', NULL);

INSERT INTO users (username, password, private_info_id, role, status, manager_id)
VALUES ('liamjohnson', 'password456', 4, 'ROLE_CUSTOMER', 'ACTIVE', 2),
       ('oliviataylor', 'password567', 5, 'ROLE_CUSTOMER', 'ACTIVE', 1),
       ('jameswilliams', 'password678', 6, 'ROLE_ADMIN', 'ACTIVE', NULL),
       ('sophiadavis', 'password789', 7, 'ROLE_CUSTOMER', 'ACTIVE', 2),
       ('williammiller', 'password890', 8, 'ROLE_CUSTOMER', 'ACTIVE', 2),
       ('miawilson', 'password901', 9, 'ROLE_CUSTOMER', 'ACTIVE', 1),
       ('michaelanderson', 'password012', 10, 'ROLE_CUSTOMER', 'ACTIVE', 1);


INSERT INTO accounts (user_id, status, balance)
VALUES (1, 'ACTIVE', 1000.00),
       (2, 'ACTIVE', 2500.00),
       (3, 'ACTIVE', 1200.00),
       (4, 'ACTIVE', 1200.00),
       (5, 'ACTIVE', 800.00),
       (6, 'ACTIVE', 3000.00),
       (7, 'ACTIVE', 2000.00),
       (8, 'ACTIVE', 1500.00),
       (9, 'ACTIVE', 2200.00),
       (10, 'ACTIVE', 1300.00);

-- Карты, привязанные к счетам
INSERT INTO cards (account_id, card_number, card_type, status, expiration_date, security_code)
VALUES (1, '1234567812345678', 'VISA', 'ACTIVE', '2026-05-01', '123'),
       (1, '8765432187654321', 'MasterCard', 'ACTIVE', '2025-08-01', '456'),
       (2, '2345678923456789', 'VISA', 'ACTIVE', '2026-11-15', '789'),
       (3, '3456789034567890', 'AMEX', 'INACTIVE', '2024-02-10', '012'),
       (4, '4567890145678901', 'VISA', 'ACTIVE', '2027-09-25', '345'),
       (5, '5678901256789012', 'MasterCard', 'ACTIVE', '2026-03-12', '678'),
       (6, '6789012367890123', 'VISA', 'ACTIVE', '2027-08-30', '901'),
       (7, '7890123478901234', 'AMEX', 'INACTIVE', '2024-06-21', '234'),
       (8, '8901234589012345', 'VISA', 'ACTIVE', '2026-04-18', '567'),
       (9, '9012345690123456', 'MasterCard', 'ACTIVE', '2025-07-07', '890'),
       (10, '0123456701234567', 'AMEX', 'ACTIVE', '2024-12-31', '123');


INSERT INTO requisites (bank_id, account_id, iban)
VALUES (1, 1, 'US64SVBKUS6S3300958879'),
       (1, 2, 'US64SVBKUS6S3300958880'),
       (2, 3, 'DE75512108001245126199'),
       (2, 4, 'DE75512108001245126200'),
       (3, 5, 'DE75512108001245126201'),
       (1, 6, 'US64SVBKUS6S3300958881'),
       (2, 7, 'DE75512108001245126202'),
       (3, 8, 'DE75512108001245126203'),
       (1, 9, 'US64SVBKUS6S3300958882'),
       (2, 10, 'DE75512108001245126204');

INSERT INTO fee_schedule (fee_schedule_name, fee_schedule_description, is_active, valid_from, valid_to)
VALUES ('Standard Fee', 'Standard fee schedule for regular accounts', TRUE, '2023-01-01 00:00:00',
        '2023-12-31 23:59:59'),
       ('Premium Fee', 'Fee schedule for premium accounts', TRUE, '2023-01-01 00:00:00', '2023-12-31 23:59:59');

-- Данные для таблицы fee_type
INSERT INTO fee_type (fee_code, fee_name, fee_description, calculation_method)
VALUES ('TXN_FLAT', 'Flat Transaction Fee', 'Fixed fee for each transaction', 'flat'),
       ('TXN_PERCENT', 'Percentage Transaction Fee', 'Percentage fee for each transaction', 'percentage');

-- Данные для таблицы fee_value, связывающей fee_type и fee_schedule
INSERT INTO fee_value (fixed_amount, percentage_rate, min_amount, max_amount, fee_type_id, fee_schedule_id)
VALUES (2.50, NULL, NULL, NULL, 1, 1),
       (NULL, 1.5, 0.50, NULL, 2, 1),
       (3.00, NULL, NULL, NULL, 1, 2),
       (NULL, 2.0, 0.75, NULL, 2, 2);

INSERT INTO account_fee_schedule (account_id, fee_schedule_id, assigned_at)
VALUES (1, 1, '2023-01-01 10:00:00'),
       (2, 1, '2023-01-02 10:30:00'),
       (3, 1, '2023-01-03 11:00:00'),
       (4, 2, '2023-01-04 11:30:00'),
       (5, 2, '2023-01-05 12:00:00');

INSERT INTO transaction_type (fee_type_id, transaction_type_name, transaction_fee, transaction_type_description)
VALUES (1, 'Domestic Transfer', 2.50, 'Fixed fee for domestic transactions'),
       (2, 'International Transfer', NULL, 'Percentage fee for international transactions'),
       (1, 'ATM Withdrawal', 3.00, 'Fixed fee for ATM withdrawals'),
       (2, 'Foreign Transaction', NULL, 'Percentage fee for transactions in foreign currency');

INSERT INTO transactions (sender_id, receiver_id, amount, fee, comment, transaction_status)
VALUES (1, 2, 100.00, 2.50, 'Payment for services', 'COMLETED'),
       (3, 4, 250.00, 3.75, 'Gift', 'COMLETED'),
       (5, 6, 500.00, 7.50, 'Invoice payment', 'COMLETED'),
       (7, 8, 150.00, 2.50, 'Loan repayment', 'DECLINED'),
       (9, 10, 300.00, 6.00, 'Rent payment', 'FAILED');


