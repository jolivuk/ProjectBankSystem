CREATE TABLE document_type (
                               document_type_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               document_type_name VARCHAR(16) NOT NULL
);
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
                              document_type_id BIGINT,
                              document_number VARCHAR(64) NOT NULL,
                              phone VARCHAR(15) NOT NULL,
                              email VARCHAR(64) NOT NULL,
                              comment VARCHAR(255),
                              address_id BIGINT,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              FOREIGN KEY (document_type_id) REFERENCES document_type(document_type_id),
                              FOREIGN KEY (address_id) REFERENCES address(address_id)
);
CREATE TABLE users (
                       user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(64) NOT NULL,
                       password VARCHAR(64) NOT NULL,
                       private_info_id BIGINT,
                       role VARCHAR(16),
                       status VARCHAR(16),
                       manager_id BIGINT,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       FOREIGN KEY (private_info_id) REFERENCES private_info(private_info_id),
                       FOREIGN KEY (role_id) REFERENCES role(role_id),

                       FOREIGN KEY (manager_id) REFERENCES users(user_id)
);
CREATE TABLE accounts (
                          account_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          user_id BIGINT,
                          status VARCHAR(16),
                          balance DECIMAL(10,2) NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          FOREIGN KEY (user_id) REFERENCES users(user_id)

);
CREATE TABLE fee_schedule (
                              fee_schedule_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              fee_schedule_name VARCHAR(64) NOT NULL,
                              fee_schedule_description VARCHAR(255),
                              is_active BOOLEAN NOT NULL,
                              valid_from DATETIME,
                              valid_to DATETIME,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
CREATE TABLE fee_type (
                          fee_type_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          fee_code VARCHAR(32) NOT NULL,
                          fee_name VARCHAR(64) NOT NULL,
                          fee_description VARCHAR(255),
                          calculation_method VARCHAR(32)
);
CREATE TABLE fee_value (
                           fee_value_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           fixed_amount DECIMAL(15,2),
                           percentage_rate DECIMAL(5,2),
                           min_amount DECIMAL(15,2),
                           max_amount DECIMAL(15,2),
                           fee_type_id BIGINT,
                           fee_schedule_id BIGINT,
                           created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                           last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           FOREIGN KEY (fee_type_id) REFERENCES fee_type(fee_type_id),
                           FOREIGN KEY (fee_schedule_id) REFERENCES fee_schedule(fee_schedule_id)
);
CREATE TABLE account_fee_schedule (
                                      account_fee_schedule_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      account_id BIGINT,
                                      fee_schedule_id BIGINT,
                                      assigned_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                      FOREIGN KEY (account_id) REFERENCES accounts(account_id),
                                      FOREIGN KEY (fee_schedule_id) REFERENCES fee_schedule(fee_schedule_id)
);
CREATE TABLE transaction_status (
                                    transaction_status_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    transaction_status_name VARCHAR(64) NOT NULL
);
CREATE TABLE transaction_type (
                                  transaction_type_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  fee_type_id BIGINT,
                                  transaction_type_name VARCHAR(64) NOT NULL,
                                  transaction_fee DECIMAL(15,2),
                                  transaction_type_description VARCHAR(255),
                                  FOREIGN KEY (fee_type_id) REFERENCES fee_type(fee_type_id)
);
CREATE TABLE banks (
                       bank_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       address_id BIGINT,
                       bank_name VARCHAR(64) NOT NULL,
                       swift VARCHAR(16),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       FOREIGN KEY (address_id) REFERENCES address(address_id)
);
CREATE TABLE requisites (
                            requisites_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            bank_id BIGINT,
                            account_id BIGINT,
                            iban VARCHAR(34) NOT NULL,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            FOREIGN KEY (bank_id) REFERENCES banks(bank_id),
                            FOREIGN KEY (account_id) REFERENCES accounts(account_id)
);
CREATE TABLE cards (
                       card_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       account_id BIGINT,
                       card_number VARCHAR(16) NOT NULL,
                       card_type VARCHAR(32),
                       status VARCHAR(16),
                       expiration_date DATE,
                       security_code VARCHAR(3),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       FOREIGN KEY (account_id) REFERENCES accounts(account_id)

);
CREATE TABLE transactions (
                              transaction_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              sender_id BIGINT,
                              receiver_id BIGINT,
                              amount DECIMAL(10,2) NOT NULL,
                              fee DECIMAL(10,2),
                              comment VARCHAR(255),
                              transaction_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                              transaction_status_id BIGINT,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              FOREIGN KEY (sender_id) REFERENCES requisites(requisites_id),
                              FOREIGN KEY (receiver_id) REFERENCES requisites(requisites_id),
                              FOREIGN KEY (transaction_status_id) REFERENCES transaction_status(transaction_status_id)
);
