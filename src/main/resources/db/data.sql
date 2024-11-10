-- Вставка данных в таблицу адресов
INSERT INTO address (country, city, postcode, street, house_number, info)
VALUES
    ('Germany', 'Berlin', '10115', 'Alexanderplatz', 5, NULL),
    ('Germany', 'Munich', '80331', 'Marienplatz', 10, NULL),
    ('Germany', 'Frankfurt', '60311', 'Zeil', 20, NULL),
    ('Germany', 'Hamburg', '20095', 'Mönckebergstraße', 15, NULL),
    ('Germany', 'Cologne', '50667', 'Domkloster', 2, NULL);

-- Вставка данных в таблицу личной информации
INSERT INTO private_info (first_name, last_name, date_of_birth, document_type, document_number, phone, email, address_id)
VALUES
    ('Max', 'Mustermann', '1980-01-01', 'PASSPORT_EU', 'D12345678', '491234567890', 'max@example.com', 1),
    ('Erika', 'Mustermann', '1985-05-10', 'PASSPORT_EU', 'D87654321', '491234567891', 'erika@example.com', 2),
    ('Hans', 'Müller', '1975-08-15', 'ID_CARD', 'ID123456', '491234567892', 'hans@example.com', 3),
    ('Julia', 'Schmidt', '1990-12-20', 'ID_CARD', 'ID654321', '491234567893', 'julia@example.com', 4),
    ('Lukas', 'Weber', '1988-03-05', 'PASSPORT_EU', 'D11223344', '491234567894', 'lukas@example.com', 5);

-- Вставка пользователей (два менеджера и три клиента)
INSERT INTO users (username, password, private_info_id, role, status, manager_id)
VALUES
    ('manager1', 'password123', 1, 'MANAGER', 'ACTIVE', NULL),
    ('manager2', 'password123', 2, 'MANAGER', 'ACTIVE', NULL),
    ('client1', 'password123', 3, 'CUSTOMER', 'ACTIVE', 1),
    ('client2', 'password123', 4, 'CUSTOMER', 'ACTIVE', 1),
    ('client3', 'password123', 5, 'CUSTOMER', 'ACTIVE', 2);

-- Вставка данных в таблицу счетов
INSERT INTO accounts (user_id, iban, swift, status, balance)
VALUES
    (3, 'DE89370400440532013000', 'DEUTDEFF', 'ACTIVE', 5000.00),
    (3, 'DE89370400440532013001', 'DEUTDEFF', 'ACTIVE', 3000.00),
    (4, 'DE89370400440532013002', 'DEUTDEFF', 'ACTIVE', 7000.00),
    (4, 'DE89370400440532013003', 'DEUTDEFF', 'ACTIVE', 1500.00),
    (5, 'DE89370400440532013004', 'DEUTDEFF', 'ACTIVE', 4000.00);

-- Вставка типов транзакций
INSERT INTO transaction_type (transaction_type_name, transaction_type_fee, transaction_type_description)
VALUES
    ('Transfer', 2.50, 'Standard bank transfer'),
    ('Withdrawal', 3.00, 'Cash withdrawal'),
    ('Deposit', 0.00, 'Account deposit');

-- Вставка транзакций
INSERT INTO transactions (sender_id, receiver_id, transaction_type_id, amount, fee, comment, transaction_status)
VALUES
    (1, 2, 1, 200.00, 2.50, 'Monthly transfer', 'COMPLETED'),
    (3, 4, 2, 500.00, 3.00, 'ATM withdrawal', 'COMPLETED'),
    (2, 5, 3, 1500.00, 0.00, 'Salary deposit', 'COMPLETED'),
    (1, 3, 1, 300.00, 2.50, 'Bill payment', 'COMPLETED'),
    (5, 4, 2, 700.00, 3.00, 'Cash withdrawal', 'COMPLETED');
