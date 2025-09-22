package com.pragma.users.domain.model;

import com.pragma.users.domain.exception.IllegalUserAgeException;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

    @Test
    void shouldSetBirthDateWhenUserIsAdult() {
        LocalDate date = LocalDate.now().minusYears(20);
        Date birthDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());

        User user = new User();
        user.setBirthDate(birthDate);

        assertEquals(birthDate, user.getBirthDate());
    }

    @Test
    void shouldThrowExceptionWhenUserIsUnderAge() {
        LocalDate date = LocalDate.now().minusYears(17);
        Date birthDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());

        User user = new User();

        assertThrows(IllegalUserAgeException.class, () -> user.setBirthDate(birthDate));
    }

    @Test
    void shouldHashPasswordCorrectly() {
        String plainPassword = "securePassword123";
        User user = new User();
        user.setPassword(plainPassword);

        String hashedPassword = user.getPassword();

        assertNotEquals(plainPassword, hashedPassword);
        assertTrue(BCrypt.checkpw(plainPassword, hashedPassword));
    }
}
