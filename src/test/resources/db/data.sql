INSERT INTO users ( username, password, role, status, manager_id, created_at, last_update)
VALUES
    ( 'BANKAccount', 'password123', 'BANK', 'ACTIVE', NULL, PARSEDATETIME('2024-11-21 09:30:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-11-21 09:30:00', 'yyyy-MM-dd HH:mm:ss')),
    ( 'manager1', 'password123', 'MANAGER', 'ACTIVE', NULL, PARSEDATETIME('2024-11-21 10:00:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-11-21 10:00:00', 'yyyy-MM-dd HH:mm:ss')),
    ( 'client1', 'password123', 'CUSTOMER', 'ACTIVE', 2, PARSEDATETIME('2024-11-21 10:15:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-11-21 10:15:00', 'yyyy-MM-dd HH:mm:ss')),
    ( 'client2', 'password123', 'CUSTOMER', 'ACTIVE', 2, PARSEDATETIME('2024-11-21 10:30:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-11-21 10:30:00', 'yyyy-MM-dd HH:mm:ss')),
    ( 'client3', 'password3', 'CUSTOMER', 'ACTIVE', 2, PARSEDATETIME('2024-11-21 10:30:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-11-21 10:30:00', 'yyyy-MM-dd HH:mm:ss'));

---- Вставка личной информации
INSERT INTO private_info (private_info_id, first_name, last_name, date_of_birth, document_type, document_number, phone, email, created_at, last_update, user_id)
VALUES
    (2, 'Max', 'Mustermann', PARSEDATETIME('1980-01-01', 'yyyy-MM-dd'), 'PASSPORT_EU', 'D12345678', '491234567890', 'max@example.com', PARSEDATETIME('2024-11-21 10:00:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-11-21 10:00:00', 'yyyy-MM-dd HH:mm:ss'), 2),
    (3, 'Erika', 'Mustermann', PARSEDATETIME('1985-05-10', 'yyyy-MM-dd'), 'PASSPORT_EU', 'D87654321', '491234567891', 'erika@example.com', PARSEDATETIME('2024-11-21 10:15:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-11-21 10:15:00', 'yyyy-MM-dd HH:mm:ss'), 3),
    (4, 'Hans', 'Muller', PARSEDATETIME('1975-08-15', 'yyyy-MM-dd'), 'ID_CARD', 'ID123456', '491234567892', 'hans@example.com', PARSEDATETIME('2024-11-21 10:30:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-11-21 10:30:00', 'yyyy-MM-dd HH:mm:ss'), 4);

-- Вставка данных в таблицу адресов
INSERT INTO address (address_id, country, city, postcode, street, house_number, info, private_info_id)
VALUES
    (2, 'Germany', 'Berlin', '10115', 'Marienplatz', '7', NULL, 2),
    (3, 'Germany', 'Berlin', '10115', 'Alexanderplatz', '5', NULL, 3),
    (4, 'Germany', 'Munich', '80331', 'Marienplatz', '10', NULL, 4);

-- Вставка данных в таблицу счетов
INSERT INTO accounts (user_id, iban, swift, status, balance, created_at, last_update)
VALUES
    (1, 'DE89370400440532013000', 'DEUTDEFF', 'ACTIVE', 0.00, PARSEDATETIME('2024-11-21 09:30:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-11-21 09:30:00', 'yyyy-MM-dd HH:mm:ss')),
    (2, 'DE12345678901234567890', 'COMMDEFF', 'ACTIVE', 7500.00, PARSEDATETIME('2024-11-21 11:00:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-11-21 11:00:00', 'yyyy-MM-dd HH:mm:ss')),
    (2, 'DE89370400440532013858', 'DEUTDEFF', 'ACTIVE', 3000.00, PARSEDATETIME('2024-11-21 11:05:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-11-21 11:05:00', 'yyyy-MM-dd HH:mm:ss')),
    (3, 'DE89370400440532013001', 'DEUTDEFF', 'ACTIVE', 3000.00, PARSEDATETIME('2024-11-21 11:10:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-11-21 11:10:00', 'yyyy-MM-dd HH:mm:ss')),
    (3, 'DE89370400440532013002', 'DEUTDEFF', 'ACTIVE', 7000.00, PARSEDATETIME('2024-11-21 11:15:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-11-21 11:15:00', 'yyyy-MM-dd HH:mm:ss'));

-- Вставка типов транзакций
INSERT INTO transaction_type (transaction_type_name, transaction_type_fee, transaction_type_description)
VALUES
    ('Transfer', 2.50, 'Standard bank transfer'),
    ('Withdrawal', 3.00, 'Cash withdrawal'),
    ('Deposit', 0.00, 'Account deposit');

-- Вставка транзакций
INSERT INTO transactions (sender_id, receiver_id, transaction_type_id, amount, fee, comment, transaction_date, transaction_status, created_at, last_update)
VALUES
    (2, 3, 1, 200.00, 2.50, 'Monthly transfer', PARSEDATETIME('2024-11-21 11:20:00', 'yyyy-MM-dd HH:mm:ss'), 'COMPLETED', PARSEDATETIME('2024-11-21 11:20:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-11-21 11:20:00', 'yyyy-MM-dd HH:mm:ss')),
    (2, 3, 2, 500.00, 3.00, 'ATM withdrawal', PARSEDATETIME('2024-11-21 11:30:00', 'yyyy-MM-dd HH:mm:ss'), 'COMPLETED', PARSEDATETIME('2024-11-21 11:30:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-11-21 11:30:00', 'yyyy-MM-dd HH:mm:ss')),
    (3, 2, 3, 1500.00, 0.00, 'Salary deposit', PARSEDATETIME('2024-11-21 11:40:00', 'yyyy-MM-dd HH:mm:ss'), 'COMPLETED', PARSEDATETIME('2024-11-21 11:40:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-11-21 11:40:00', 'yyyy-MM-dd HH:mm:ss')),
    (3, 1, 1, 1000.00, 2.50, 'Company transfer', PARSEDATETIME('2024-11-21 12:00:00', 'yyyy-MM-dd HH:mm:ss'), 'COMPLETED', PARSEDATETIME('2024-11-21 12:00:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-11-21 12:00:00', 'yyyy-MM-dd HH:mm:ss')),
    (1, 2, 3, 5000.00, 0.00, 'Bank deposit', PARSEDATETIME('2024-11-21 12:30:00', 'yyyy-MM-dd HH:mm:ss'), 'COMPLETED', PARSEDATETIME('2024-11-21 12:30:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2024-11-21 12:30:00', 'yyyy-MM-dd HH:mm:ss'));
