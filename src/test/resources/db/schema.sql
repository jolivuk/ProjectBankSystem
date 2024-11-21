DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS transaction_type;
DROP TABLE IF EXISTS banks;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS private_info;
DROP TABLE IF EXISTS address;

CREATE TABLE address (
                         address_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         country VARCHAR(64) NOT NULL,
                         city VARCHAR(64) NOT NULL,
                         postcode VARCHAR(6) NOT NULL,
                         street VARCHAR(64) NOT NULL,
                         house_number INT NOT NULL,
                         info VARCHAR(64)
);

CREATE TABLE private_info (
                              private_info_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              first_name VARCHAR(64) NOT NULL,
                              last_name VARCHAR(64) NOT NULL,
                              date_of_birth DATE NOT NULL,
                              document_type VARCHAR(32),
                              document_number VARCHAR(64) NOT NULL,
                              phone VARCHAR(15) NOT NULL,
                              email VARCHAR(64) NOT NULL,
                              comment VARCHAR(255),
                              address_id BIGINT,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              FOREIGN KEY (address_id) REFERENCES address(address_id)
);
CREATE TABLE users (
                       user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(64) NOT NULL,
                       password VARCHAR(64) NOT NULL,
                       private_info_id BIGINT,
                       role VARCHAR(32),
                       status VARCHAR(16),
                       manager_id BIGINT,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       FOREIGN KEY (private_info_id) REFERENCES private_info(private_info_id),
                       FOREIGN KEY (manager_id) REFERENCES users(user_id)
);
CREATE TABLE accounts (
                          account_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          user_id BIGINT,
                          iban VARCHAR(34) NOT NULL,
                          swift VARCHAR(16),
                          status VARCHAR(16),
                          balance DECIMAL(10,2) NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE transaction_type (
                                  transaction_type_id INT AUTO_INCREMENT PRIMARY KEY,
                                  transaction_type_name VARCHAR(16) NOT NULL,
                                  transaction_type_fee DECIMAL(10,2),
                                  transaction_type_description VARCHAR(64)
);

CREATE TABLE transactions (
                              transaction_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              sender_id BIGINT,
                              receiver_id BIGINT,
                              transaction_type_id INT,
                              amount DECIMAL(10,2) NOT NULL,
                              fee DECIMAL(10,2),
                              comment VARCHAR(255),
                              transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              transaction_status VARCHAR(16),
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              FOREIGN KEY (sender_id) REFERENCES accounts(account_id),
                              FOREIGN KEY (receiver_id) REFERENCES accounts(account_id),
                              FOREIGN KEY (transaction_type_id) REFERENCES transaction_type(transaction_type_id)
);



