package by.aston.bank.service;

import by.aston.bank.service.dto.UserWriteDto;
import by.aston.bank.utils.TestDBUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private final UserService userService = new UserService();

    @BeforeEach
    public void setUp() throws Exception {
        TestDBUtils.initDb();
    }

    @AfterAll
    static void tearDown() {
        try {
            TestDBUtils.getConnection().close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    void shouldHaveSomeUserInListWhenFindAll() {
        assertEquals(userService.findAll().size(), 3);
    }

    @Test
    void shouldPresentWhenFindByUserId() {
        assertTrue(userService.findById(1L).isPresent());
    }

    @Test
    void shouldOptionalIsPresentWhenUserSave() {
        var user = new UserWriteDto("Andrey", "Nekrashevich");
        assertTrue(userService.create(user).isPresent());
    }

    @Test
    void shouldTrueWhenUserUpdate() {
        var user = new UserWriteDto("Andrey", "Nekrashevich");
        assertTrue(userService.update(1L, user).isPresent());
    }

    @Test
    void shouldTrueWhenUserDelete() {
        assertTrue(userService.delete(1L));
    }

}
