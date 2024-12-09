# Technical Specification for Developing the Backend of a Banking Application

## Project Description
This project is a banking application designed to manage bank accounts and facilitate interactions 
between users and the bank. It enables clients to manage their accounts, perform transfers, check 
balances, and generate account statements for specified date ranges.

## Key Features
- **Authentication and Authorization System**: Supports roles (Admin, Manager, Client).
- **Bank Account Management**: Create new accounts, block accounts, view balances, and close accounts.
- **Money Transfers**: Facilitate transfers between accounts.
- **Transaction History**: View transaction history with date filters and export data to PDF.
- **User Profile Management**: Create and edit profiles.
- **Notifications**: Notify users about operations and account activities.
- **Integration with Third-Party Services**: Support for payments through external services.

## Technologies Used
- **Spring Boot**: Core framework for building the web application, creating REST APIs, managing 
dependencies, ensuring security, and validating data.
- **JPA + MySQL**: For database operations using Java Persistence API and MySQL as the database.
- **Liquibase**: Manages database migrations and schema version control.
- **JWT (JSON Web Token)**: Provides secure user authentication and authorization.
- **SpringDoc OpenAPI**: Automatically generates API documentation using Swagger.
- **Lombok**: Reduces boilerplate code by generating getters, setters, constructors, etc.
- **iText PDF**: Generates PDF documents, such as reports and transaction histories.
- **H2**: Lightweight database used for testing and development.
- **Jackson**: Handles JSON data during REST API exchanges.
- **Testing Tools**:
    - **JUnit**: For unit testing.
    - **Mockito**: For creating mock objects.
    - **Spring Security Test**: For testing security features.

## User Classes and Characteristics
- **Clients**:
    - Individual users managing their accounts.
    - Can create accounts, perform transfers, and generate statistics for a selected period.
- **Managers**:
    - Bank staff managing accounts, transactions, and associated users.
    - Responsible for adding and removing clients.
- **Administrator**:
    - Staff with full access and the ability to manage permissions.
    - Can view and manage all clients, not limited to their own.

## Functional Requirements
- **User Account Management**:
    - Registration, authorization, profile editing, and the ability to block or delete accounts.
- **Bank Account Management**:
    - Create, close, view balances, and list all client accounts.
    - Create new transactions.
- **Money Transfers**:
    - Transfers between the user’s own accounts.
    - Transfers to other clients’ accounts within the bank.
- **Transaction History**:
    - Filter transactions by date.
    - Export transaction history to PDF.

## Technology Stack Requirements
- **Programming Language**: Java 17 or later.
- **Framework**: Spring Boot 3.x.
- **Security**: Spring Security.
- **Data Management**: Spring Data JPA/Hibernate.
- **Database**: MySQL.
- **Containerization**: Docker.

## Interfaces and Documentation
- **RESTful API**: Interaction with the frontend via HTTP methods (GET, POST, PUT, DELETE) using
JSON format.
- **Authentication and Authorization**: JWT for security.
- **API Documentation**: Automatically generated with Swagger using SpringDoc OpenAPI.

## Additional Technologies and Tools
- **Liquibase**: Tracks and manages database schema changes, ensuring compatibility across development 
- stages.
- **H2**: Used as a test database during development and testing.
- **Testing**:
    - **JUnit** and **Mockito** for unit tests and mock objects.
    - **Spring Security Test** for security testing.

## Future Project Development

### Planned Features
1. **Enhanced Confirmation System**:
    - Implement confirmation of critical actions (e.g., transactions, profile updates) through:
        - SMS notifications.
        - Email verification.
        - Telegram bot integration for real-time confirmations.

2. **Integration of Credit Card Entities**:
    - Add support for credit cards linked to bank accounts.
    - Features include:
        - Viewing linked credit cards and their details.
        - Managing credit card limits and transactions.
        - Supporting credit card payments and balance checks.

3. **External Bank Transfers**:
    - Enable transfers to accounts in external banks.
    - Include:
        - Validation of external bank account details (e.g., IBAN, SWIFT).
        - Support for transfer fees and exchange rates where applicable.
        - Secure processing of external transfers with user confirmation mechanisms.

