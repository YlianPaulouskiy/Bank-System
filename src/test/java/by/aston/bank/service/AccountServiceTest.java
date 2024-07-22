package by.aston.bank.service;

import by.aston.bank.service.dto.AccountWriteDto;
import by.aston.bank.service.dto.BankWriteDto;
import by.aston.bank.utils.TestDBUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountServiceTest {

    private final AccountService accountService = new AccountService();

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
    void shouldHaveSomeBankInListWhenFindAll() {
        assertEquals(accountService.findAll().size(), 9);
    }

    @Test
    void shouldPresentWhenFindByBankId() {
        assertTrue(accountService.findById(1L).isPresent());
    }

    @Test
    void shouldOptionalIsPresentWhenBankSave() {
        var account = new AccountWriteDto("1231231231", new BigDecimal("00.0"), 1L, 1L);
        assertTrue(accountService.create(account).isPresent());
    }

    @Test
    void shouldTrueWhenBankUpdate() {
        var account = new AccountWriteDto("1231231231", new BigDecimal("00.0"), 1L, 1L);
        assertTrue(accountService.update(1L, account).isPresent());
    }

    @Test
    void shouldTrueWhenBankDelete() {
        assertTrue(accountService.delete(1L));
    }

}
