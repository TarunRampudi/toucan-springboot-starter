# Changes Made to the Project

This document describes the changes and improvements I have made to the project.

### 1. Project Setup
- Forked the original project.
- Cloned the repository.
- Installed and configured Java 17.
- Configured the `JAVA_HOME` environment variable.
- Opened the project in VS Code.

### 2.Project Verification
- Ran the Maven clean test command.
- Verified that the sample test passed successfully.
- Confirmed that the project build was successful.
- Ran the Spring Boot application.
- Verified that the application starts successfully on port 8080.
- Tested the sample REST endpoint `GET /api/sample`.
- Confirmed that the sample endpoint returned the expected response.

### 3. Transaction Entity
- Created the `Transaction` entity.
- Added the required transaction fields:
  - Transaction ID
  - Customer ID
  - Amount
  - Currency
  - Transaction Type
  - Transaction Status
- Configured `Transaction ID` as the entity identifier using JPA.

### 4. Transaction Repository

- Created the `TransactionRepository` interface.
- Extended `JpaRepository` to provide database operations for transactions.
- Configured the repository to use `Transaction` with `String` as the transaction ID type.

### 5. Create Transaction API

- Added the transaction service layer.
- Implemented the create transaction operation.
- Added the transaction REST controller.
- Implemented the `POST /api/transactions` endpoint.
- Connected the controller, service, repository, and H2 database.
- Tested the create transaction API successfully.

### 6. Get Transaction API

- Implemented the get transaction operation.
- Added the `GET /api/transactions/{transactionId}` endpoint.
- Implemented transaction lookup using the transaction ID.
- Returns the transaction when the ID exists.
- Returns `404 Not Found` when the transaction does not exist.
- Tested both existing and non-existing transaction IDs.


### 7. Update Transaction Status API

- Implemented the update transaction status operation.
- Added the `PUT /api/transactions/{transactionId}/status` endpoint.
- Implemented status update through the service layer.
- Updated the transaction status and saved the changes to the H2 database.
- Returns `404 Not Found` when the transaction does not exist.
- Tested updating transaction status from `PENDING` to `COMPLETED`.

### 8. Get Customer Transactions API

- Implemented the get all transactions for a customer operation.
- Added the `GET /api/transactions/customer/{customerId}` endpoint.
- Implemented customer-based transaction lookup using Spring Data JPA.
- Returns all transactions associated with the specified customer.
- Tested the endpoint with multiple transactions for the same customer.


### 10. Error Handling

- Added a global exception handler for validation errors.
- Validation failures return HTTP 400 Bad Request.
- Added clear validation error messages for invalid transaction data.
- Centralized validation error handling using `@RestControllerAdvice`.


### 11. Automated Tests

- Added integration tests for transaction creation.
- Added integration tests for retrieving a transaction.
- Added integration tests for updating transaction status.
- Added integration tests for retrieving transactions by customer.
- Verified the REST APIs using Spring Boot test infrastructure.
- All automated tests pass successfully.

### 12. Error Case Testing

- Tested retrieval of a transaction that does not exist.
- Tested validation failure for invalid transaction data.
- Tested invalid transaction status handling.
- Tested transaction status update restrictions.
- Verified appropriate error responses are returned.

### 13. Final Test Verification

- Executed the complete Maven test suite.
- Verified transaction creation, retrieval, status update, and customer transaction lookup.
- Verified invalid transaction handling.
- Verified transaction status transition restrictions.
- All tests passed successfully with zero failures and zero errors.