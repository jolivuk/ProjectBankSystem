INSERT INTO users (username, password, status, role, manager_id) VALUES
                                                                     ('BANKaccount', 'password123', 'ACTIVE', 'BANK', NULL),
                                                                     ('manager1', 'password1', 'ACTIVE', 'MANAGER', NULL),
                                                                     ('customer1', 'password2', 'ACTIVE', 'CUSTOMER', 2),
                                                                     ('customer2', 'password3', 'INACTIVE', 'CUSTOMER', 2),
                                                                     ('customer3', 'password4', 'ACTIVE', 'CUSTOMER', 2);

-- Вставляем данные в таблицу private_info
INSERT INTO private_info (private_info_id,first_name, last_name, email, phone, date_of_birth, document_type, document_number, comment, user_id) VALUES
                        (2,'John', 'Doe', 'john.doe@example.com', '+1234567890', '1990-01-01', 'PASSPORT_EU', 'A12345678', 'Test comment for user1', 2),
                        (3,'Jane', 'Smith', 'jane.smith@example.com', '+9876543210', '1985-05-15', 'ID_CARD', 'B98765432', 'Test comment for user2', 3),
                        (4,'Erika', 'Mustermann', 'erika@example.com','491234567891','1985-05-10', 'PASSPORT_EU', 'D87654321', NULL ,  4),
                        (5,'Hans', 'Müller', 'hans@example.com','491234567892','1975-08-15','ID_CARD', 'ID123456',NULL,5);


-- Вставка данных в таблицу адресов
INSERT INTO address (address_id, country, city, postcode, street, house_number, info, private_info_id)
VALUES
    (2,'Germany', 'Berlin', '10115', 'Marienplatz', 7, NULL, 2),
    (3,'Germany', 'Berlin', '10115', 'Alexanderplatz', 5, NULL,3),
    (4,'Germany', 'Munich', '80331', 'Marienplatz', 10, NULL,4),
    (5,'Germany', 'Frankfurt', '60311', 'Zeil', 20, NULL,5);

-- Вставка данных в таблицу счетов
-- Акканут банка
INSERT INTO accounts (account_id,user_id, iban, swift, status, balance)
VALUES
    (1,1, 'DE89370400440532013000', 'DEUTDEFF', 'ACTIVE', 0.00);

INSERT INTO accounts (user_id, iban, swift, status, balance)
VALUES
    (3, 'DE12345678901234567890', 'COMMDEFF', 'ACTIVE', 7500.00),
    (4, 'DE89370400440532013858', 'DEUTDEFF', 'ACTIVE', 3000.00),
    (5, 'DE89370400440532013001', 'DEUTDEFF', 'ACTIVE', 3000.00),
    (5, 'DE89370400440532013002', 'DEUTDEFF', 'ACTIVE', 7000.00);

-- Вставка типов транзакций
INSERT INTO transaction_type (transaction_type_name, transaction_type_fee, transaction_type_description)
VALUES
    ('TRANSFER', 2.50, 'Standard bank transfer'),
    ('WITHDRAWAL', 3.00, 'Cash withdrawal'),
    ('DEPOSIT', 0.00, 'Account deposit');

-- Вставка транзакций
INSERT INTO transactions (sender_id, receiver_id, transaction_type_id, amount, fee, comment, transaction_date, transaction_status)
VALUES
    (4, 2, 1, 200.00, 2.50, 'Monthly transfer', NOW() - INTERVAL FLOOR(RAND() * 365) DAY, 'COMPLETED'),
    (3, 4, 2, 500.00, 3.00, 'ATM withdrawal', NOW() - INTERVAL FLOOR(RAND() * 365) DAY, 'COMPLETED'),
    (2, 4, 3, 1500.00, 0.00, 'Salary deposit', NOW() - INTERVAL FLOOR(RAND() * 365) DAY, 'COMPLETED'),
    (4, 3, 1, 300.00, 2.50, 'Bill payment', NOW() - INTERVAL FLOOR(RAND() * 365) DAY, 'COMPLETED'),
    (2, 4, 2, 700.00, 3.00, 'Cash withdrawal', NOW() - INTERVAL FLOOR(RAND() * 365) DAY, 'COMPLETED'),
    (2, 3, 1, 450.00, 1.50, 'Service fee', NOW() - INTERVAL FLOOR(RAND() * 365) DAY, 'COMPLETED');

