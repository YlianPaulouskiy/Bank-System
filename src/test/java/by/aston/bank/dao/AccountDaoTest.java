package by.aston.bank.dao;

import by.aston.bank.model.Account;
import by.aston.bank.model.Bank;
import by.aston.bank.model.User;
import by.aston.bank.utils.TestDBUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountDaoTest {

    private final AccountDao accountDao = new AccountDao();
    private final static Long FIND_ID = 1L;
    private final static Integer SIZE = 9;
    private Account created;


    @BeforeEach
    void setUp() {
        TestDBUtils.initDb();
        created = new Account();
        created.setNumber("1111112332");
        created.setCash(new BigDecimal("200.00"));
        var user= new User();
        user.setId(1L);
        var bank = new Bank();
        bank.setId(1L);
        created.setUser(user);
        created.setBank(bank);
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
    public void findByIdTest() {
        var optional = accountDao.findById(FIND_ID);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getId(), FIND_ID);
        assertEquals(optional.get().getNumber(), "12376589567");
        assertEquals(optional.get().getCash(), new BigDecimal("70.77"));
    }

    @Test
    public void findAllTest() {
        var entities = accountDao.findAll();
        assertEquals(entities.size(), SIZE);
    }

    @Test
    public void saveTest() {
        var optional = accountDao.save(created);
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getNumber(), created.getNumber());
        assertEquals(optional.get().getCash(), created.getCash());
        assertEquals(accountDao.findAll().size(), SIZE + 1);
    }

    @Test
    void updateTest() {
        var userOptional = accountDao.update(FIND_ID, created);
        assertTrue(userOptional.isPresent());
        assertEquals(userOptional.get().getNumber(), created.getNumber());
        assertEquals(userOptional.get().getCash(), created.getCash());
        assertEquals(accountDao.findAll().size(), SIZE);
    }

    @Test
    void deleteTest() {
        assertTrue(accountDao.delete(FIND_ID));
        assertEquals(accountDao.findAll().size(), SIZE - 1);
    }

}
