package com.example.individualprojectbe.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExceptionsTestSuite {
    @Test
    void cartNotFoundExceptionTest() {
        CartNotFoundException cartNotFoundException = assertThrows(CartNotFoundException.class, () -> {
            throw new CartNotFoundException();
        });

        assertNotNull(cartNotFoundException);
    }

    @Test
    void flightNotFoundExceptionTest() {
        FlightNotFoundException flightNotFoundException = assertThrows(FlightNotFoundException.class, () -> {
            throw new FlightNotFoundException();
        });

        assertNotNull(flightNotFoundException);
    }

    @Test
    void loginTokenNotFoundExceptionTest() {
        LoginTokenNotFoundException loginTokenNotFoundException = assertThrows(LoginTokenNotFoundException.class, () -> {
            throw new LoginTokenNotFoundException();
        });

        assertNotNull(loginTokenNotFoundException);
    }

    @Test
    void orderNotFoundExceptionTest() {
        OrderNotFoundException orderNotFoundException = assertThrows(OrderNotFoundException.class, () -> {
            throw new OrderNotFoundException();
        });

        assertNotNull(orderNotFoundException);
    }

    @Test
    void userNotFoundExceptionTest() {
        UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class, () -> {
            throw new UserNotFoundException();
        });

        assertNotNull(userNotFoundException);
    }
}
