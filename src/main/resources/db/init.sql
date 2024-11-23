use banksystem;

DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS transaction_type;
DROP TABLE IF EXISTS banks;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS private_info;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       status VARCHAR(255),
                       role VARCHAR(255),
                       manager_id BIGINT,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       CONSTRAINT fk_manager FOREIGN KEY (manager_id) REFERENCES users(user_id) ON DELETE SET NULL
);

CREATE TABLE private_info (
                              private_info_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              first_name VARCHAR(255),
                              last_name VARCHAR(255),
                              email VARCHAR(255),
                              phone VARCHAR(255),
                              date_of_birth DATE,
                              document_type VARCHAR(255),
                              document_number VARCHAR(255),
                              comment TEXT,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              user_id BIGINT NOT NULL UNIQUE,
                              CONSTRAINT fk_private_info_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE address (
                         address_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         country VARCHAR(255),
                         city VARCHAR(255),
                         postcode VARCHAR(255),
                         street VARCHAR(255),
                         house_number VARCHAR(255),
                         info TEXT,
                         private_info_id BIGINT NOT NULL UNIQUE,
                         CONSTRAINT fk_address_private_info FOREIGN KEY (private_info_id) REFERENCES private_info(private_info_id) ON DELETE CASCADE
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