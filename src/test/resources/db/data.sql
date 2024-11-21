-- Вставка данных в таблицу адресов
INSERT INTO address (country, city, postcode, street, house_number, info)
VALUES
    ('Germany', 'Berlin', '10115', 'Marienplatz', 7, NULL),
    ('Germany', 'Berlin', '10115', 'Alexanderplatz', 5, NULL),
    ('Germany', 'Munich', '80331', 'Marienplatz', 10, NULL),
    ('Germany', 'Frankfurt', '60311', 'Zeil', 20, NULL),
    ('Germany', 'Hamburg', '20095', 'Mönckebergstraße', 15, NULL),
    ('Germany', 'Cologne', '50667', 'Domkloster', 2, NULL);

-- Вставка личной информации
INSERT INTO private_info (first_name, last_name, date_of_birth, document_type, document_number, phone, email, address_id, created_at, last_update)
VALUES
    ('Max', 'Mustermann', PARSEDATETIME('1980-01-01', 'yyyy-MM-dd'), 'PASSPORT_EU', 'D12345678', '491234567890', 'max@example.com', 1, PARSEDATETIME('2024-05-20 10:00:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-05-20 10:00:00', 'yyyy-MM-dd HH:mm:ss')),
    ('Erika', 'Mustermann', PARSEDATETIME('1985-05-10', 'yyyy-MM-dd'), 'PASSPORT_EU', 'D87654321', '491234567891', 'erika@example.com', 2, PARSEDATETIME('2024-05-20 10:15:23', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-05-20 10:15:23', 'yyyy-MM-dd HH:mm:ss')),
    ('Hans', 'Müller', PARSEDATETIME('1975-08-15', 'yyyy-MM-dd'), 'ID_CARD', 'ID123456', '491234567892', 'hans@example.com', 3, PARSEDATETIME('2024-05-20 11:30:45', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-05-20 11:30:45', 'yyyy-MM-dd HH:mm:ss')),
    ('Julia', 'Schmidt', PARSEDATETIME('1990-12-20', 'yyyy-MM-dd'), 'ID_CARD', 'ID654321', '491234567893', 'julia@example.com', 4, PARSEDATETIME('2024-05-20 12:45:12', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-05-20 12:45:12', 'yyyy-MM-dd HH:mm:ss')),
    ('Lukas', 'Weber', PARSEDATETIME('1988-03-05', 'yyyy-MM-dd'), 'PASSPORT_EU', 'D11223344', '491234567894', 'lukas@example.com', 5, PARSEDATETIME('2024-05-20 14:20:35', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-05-20 14:20:35', 'yyyy-MM-dd HH:mm:ss'));

-- Вставка пользователей
INSERT INTO users (username, password, private_info_id, role, status, manager_id, created_at, last_update)
VALUES
    ('BANKaccount', 'password123', null, 'BANK', 'ACTIVE', NULL, PARSEDATETIME('2024-05-20 10:00:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-05-20 10:00:00', 'yyyy-MM-dd HH:mm:ss')),
    ('manager1', 'password123', 1, 'MANAGER', 'ACTIVE', NULL, PARSEDATETIME('2024-05-20 10:00:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-05-20 10:00:00', 'yyyy-MM-dd HH:mm:ss')),
    ('manager2', 'password123', 2, 'MANAGER', 'ACTIVE', NULL, PARSEDATETIME('2024-05-20 10:15:23', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-05-20 10:15:23', 'yyyy-MM-dd HH:mm:ss')),
    ('client1', 'password123', 3, 'CUSTOMER', 'ACTIVE', 2, PARSEDATETIME('2024-05-20 11:30:45', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-05-20 11:30:45', 'yyyy-MM-dd HH:mm:ss')),
    ('client2', 'password123', 4, 'CUSTOMER', 'ACTIVE', 2, PARSEDATETIME('2024-05-20 12:45:12', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-05-20 12:45:12', 'yyyy-MM-dd HH:mm:ss')),
    ('client3', 'password123', 5, 'CUSTOMER', 'ACTIVE', 3, PARSEDATETIME('2024-05-20 14:20:35', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-05-20 14:20:35', 'yyyy-MM-dd HH:mm:ss'));

-- Вставка данных в таблицу счетов
-- Акканут банка
INSERT INTO accounts (user_id, iban, swift, status, balance, created_at, last_update)
VALUES
    (1, 'DE89370400440532013000', 'DEUTDEFF', 'ACTIVE', 0.00, PARSEDATETIME('2024-05-20 10:00:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-05-20 10:00:00', 'yyyy-MM-dd HH:mm:ss')),
    (4, 'DE12345678901234567890', 'COMMDEFF', 'ACTIVE', 7500.00, PARSEDATETIME('2024-05-20 10:00:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-05-20 10:00:00', 'yyyy-MM-dd HH:mm:ss')),
    (4, 'DE89370400440532013858', 'DEUTDEFF', 'ACTIVE', 3000.00, PARSEDATETIME('2024-05-20 10:00:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-05-20 10:00:00', 'yyyy-MM-dd HH:mm:ss')),
    (5, 'DE89370400440532013001', 'DEUTDEFF', 'ACTIVE', 3000.00, PARSEDATETIME('2024-05-20 10:00:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-05-20 10:00:00', 'yyyy-MM-dd HH:mm:ss')),
    (5, 'DE89370400440532013002', 'DEUTDEFF', 'ACTIVE', 7000.00, PARSEDATETIME('2024-05-20 10:00:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-05-20 10:00:00', 'yyyy-MM-dd HH:mm:ss')),
    (6, 'DE89370400440532013003', 'DEUTDEFF', 'ACTIVE', 1500.00, PARSEDATETIME('2024-05-20 10:00:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-05-20 10:00:00', 'yyyy-MM-dd HH:mm:ss')),
    (6, 'DE89370400440532013004', 'DEUTDEFF', 'ACTIVE', 4000.00, PARSEDATETIME('2024-05-20 10:00:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-05-20 10:00:00', 'yyyy-MM-dd HH:mm:ss'));

-- Вставка типов транзакций
INSERT INTO transaction_type (transaction_type_name, transaction_type_fee, transaction_type_description)
VALUES
    ('Transfer', 2.50, 'Standard bank transfer'),
    ('Withdrawal', 3.00, 'Cash withdrawal'),
    ('Deposit', 0.00, 'Account deposit');

-- Вставка транзакций
INSERT INTO transactions (sender_id, receiver_id, transaction_type_id, amount, fee, comment, transaction_date, transaction_status, created_at, last_update)
VALUES
    (5, 2, 1, 200.00, 2.50, 'Monthly transfer', DATEADD('DAY', -CAST(RAND() * 365 AS INT), CURRENT_TIMESTAMP()), 'COMPLETED', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (3, 4, 2, 500.00, 3.00, 'ATM withdrawal', DATEADD('DAY', -CAST(RAND() * 365 AS INT), CURRENT_TIMESTAMP()), 'COMPLETED', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (2, 5, 3, 1500.00, 0.00, 'Salary deposit', DATEADD('DAY', -CAST(RAND() * 365 AS INT), CURRENT_TIMESTAMP()), 'COMPLETED', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (6, 3, 1, 300.00, 2.50, 'Bill payment', DATEADD('DAY', -CAST(RAND() * 365 AS INT), CURRENT_TIMESTAMP()), 'COMPLETED', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (5, 4, 2, 700.00, 3.00, 'Cash withdrawal', DATEADD('DAY', -CAST(RAND() * 365 AS INT), CURRENT_TIMESTAMP()), 'COMPLETED', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (2, 3, 1, 450.00, 1.50, 'Service fee', DATEADD('DAY', -CAST(RAND() * 365 AS INT), CURRENT_TIMESTAMP()), 'COMPLETED', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (4, 3, 3, 1200.00, 0.00, 'Bonus', DATEADD('DAY', -CAST(RAND() * 365 AS INT), CURRENT_TIMESTAMP()), 'COMPLETED', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (2, 5, 2, 600.00, 2.00, 'Loan payment', DATEADD('DAY', -CAST(RAND() * 365 AS INT), CURRENT_TIMESTAMP()), 'COMPLETED', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (3, 2, 1, 50.00, 0.50, 'Gift transfer', DATEADD('DAY', -CAST(RAND() * 365 AS INT), CURRENT_TIMESTAMP()), 'COMPLETED', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (5, 4, 2, 750.00, 2.50, 'Cash deposit', DATEADD('DAY', -CAST(RAND() * 365 AS INT), CURRENT_TIMESTAMP()), 'COMPLETED', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());