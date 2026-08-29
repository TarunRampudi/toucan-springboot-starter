package com.example.transactionstarter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransactionControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldCreateTransaction() {

        Transaction transaction = new Transaction();

        transaction.setTransactionId("TEST001");
        transaction.setCustomerId("CUSTOMER001");
        transaction.setAmount(new BigDecimal("1000.00"));
        transaction.setCurrency("INR");
        transaction.setTransactionType("PAYMENT");
        transaction.setTransactionStatus("PENDING");

        ResponseEntity<Transaction> response =
                restTemplate.postForEntity(
                        "http://localhost:" + port + "/api/transactions",
                        transaction,
                        Transaction.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("TEST001",
                response.getBody().getTransactionId());
    }

    @Test
    void shouldGetTransaction() {

        Transaction transaction = new Transaction();

        transaction.setTransactionId("TEST002");
        transaction.setCustomerId("CUSTOMER001");
        transaction.setAmount(new BigDecimal("500.00"));
        transaction.setCurrency("INR");
        transaction.setTransactionType("PAYMENT");
        transaction.setTransactionStatus("PENDING");

        restTemplate.postForEntity(
                "http://localhost:" + port + "/api/transactions",
                transaction,
                Transaction.class);

        ResponseEntity<Transaction> response =
                restTemplate.getForEntity(
                        "http://localhost:" + port +
                                "/api/transactions/TEST002",
                        Transaction.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("TEST002",
                response.getBody().getTransactionId());
    }

    @Test
    void shouldUpdateTransactionStatus() {

        Transaction transaction = new Transaction();

        transaction.setTransactionId("TEST003");
        transaction.setCustomerId("CUSTOMER001");
        transaction.setAmount(new BigDecimal("750.00"));
        transaction.setCurrency("INR");
        transaction.setTransactionType("PAYMENT");
        transaction.setTransactionStatus("PENDING");

        restTemplate.postForEntity(
                "http://localhost:" + port + "/api/transactions",
                transaction,
                Transaction.class);

        ResponseEntity<Transaction> response =
                restTemplate.exchange(
                        "http://localhost:" + port +
                                "/api/transactions/TEST003/status?status=COMPLETED",
                        HttpMethod.PUT,
                        null,
                        Transaction.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("COMPLETED",
                response.getBody().getTransactionStatus());
    }

    @Test
    void shouldGetTransactionsByCustomer() {

        Transaction transaction = new Transaction();

        transaction.setTransactionId("TEST004");
        transaction.setCustomerId("CUSTOMER002");
        transaction.setAmount(new BigDecimal("1200.00"));
        transaction.setCurrency("INR");
        transaction.setTransactionType("PAYMENT");
        transaction.setTransactionStatus("PENDING");

        restTemplate.postForEntity(
                "http://localhost:" + port + "/api/transactions",
                transaction,
                Transaction.class);

        ResponseEntity<Transaction[]> response =
                restTemplate.getForEntity(
                        "http://localhost:" + port +
                                "/api/transactions/customer/CUSTOMER002",
                        Transaction[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length >= 1);
    }
    @Test
void shouldReturnNotFoundForInvalidTransactionId() {

    ResponseEntity<Transaction> response =
            restTemplate.getForEntity(
                    "http://localhost:" + port +
                            "/api/transactions/INVALID001",
                    Transaction.class);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
}
@Test
void shouldRejectInvalidTransaction() {

    Transaction transaction = new Transaction();

    transaction.setTransactionId("TEST005");
    transaction.setCustomerId("");
    transaction.setAmount(new BigDecimal("-100.00"));
    transaction.setCurrency("INR");
    transaction.setTransactionType("PAYMENT");
    transaction.setTransactionStatus("PENDING");

    ResponseEntity<String> response =
            restTemplate.postForEntity(
                    "http://localhost:" + port + "/api/transactions",
                    transaction,
                    String.class);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
}
@Test
void shouldNotAllowStatusChangeFromCompleted() {

    Transaction transaction = new Transaction();

    transaction.setTransactionId("TEST006");
    transaction.setCustomerId("CUSTOMER003");
    transaction.setAmount(new BigDecimal("500.00"));
    transaction.setCurrency("INR");
    transaction.setTransactionType("PAYMENT");
    transaction.setTransactionStatus("PENDING");

    restTemplate.postForEntity(
            "http://localhost:" + port + "/api/transactions",
            transaction,
            Transaction.class);

    restTemplate.exchange(
            "http://localhost:" + port +
                    "/api/transactions/TEST006/status?status=COMPLETED",
            HttpMethod.PUT,
            null,
            Transaction.class);

    ResponseEntity<String> response =
            restTemplate.exchange(
                    "http://localhost:" + port +
                            "/api/transactions/TEST006/status?status=FAILED",
                    HttpMethod.PUT,
                    null,
                    String.class);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
            response.getStatusCode());
}
@Test
void shouldRejectInvalidStatus() {

    Transaction transaction = new Transaction();

    transaction.setTransactionId("TEST007");
    transaction.setCustomerId("CUSTOMER004");
    transaction.setAmount(new BigDecimal("500.00"));
    transaction.setCurrency("INR");
    transaction.setTransactionType("PAYMENT");
    transaction.setTransactionStatus("PENDING");

    restTemplate.postForEntity(
            "http://localhost:" + port + "/api/transactions",
            transaction,
            Transaction.class);

    ResponseEntity<String> response =
            restTemplate.exchange(
                    "http://localhost:" + port +
                            "/api/transactions/TEST007/status?status=INVALID",
                    HttpMethod.PUT,
                    null,
                    String.class);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
            response.getStatusCode());
}
}