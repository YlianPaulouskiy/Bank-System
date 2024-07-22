package by.aston.bank.service;

import by.aston.bank.service.dto.BankWriteDto;
import by.aston.bank.utils.TestDBUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankServiceTest {

    private final BankService bankService = new BankService();

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
        assertEquals(bankService.findAll().size(), 4);
    }

    @Test
    void shouldPresentWhenFindByBankId() {
        assertTrue(bankService.findById(1L).isPresent());
    }

    @Test
    void shouldOptionalIsPresentWhenBankSave() {
        var bank = new BankWriteDto("new Bank");
        assertTrue(bankService.create(bank).isPresent());
    }

    @Test
    void shouldTrueWhenBankUpdate() {
        var bank = new BankWriteDto("new Bank");
        assertTrue(bankService.update(1L, bank).isPresent());
    }

    @Test
    void shouldTrueWhenBankDelete() {
        assertTrue(bankService.delete(1L));
    }

}
