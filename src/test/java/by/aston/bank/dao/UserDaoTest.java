package by.aston.bank.dao;

import by.aston.bank.model.User;
import by.aston.bank.utils.TestDBUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDaoTest {

    private final UserDao userDao = new UserDao();
    private final static Long FIND_ID = 1L;
    private final static Integer SIZE = 3;
    private User createUser;
    private User rightUser;

    @BeforeEach
    void setUp() {
        TestDBUtils.initDb();
        createUser = new User();
        createUser.setName("Mamkin");
        createUser.setLastName("Hacker");
        rightUser = new User();
        rightUser.setId(1L);
        rightUser.setName("Maxim");
        rightUser.setLastName("Yarosh");
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
        var userOptional = userDao.findById(FIND_ID);
        assertTrue(userOptional.isPresent());
        assertEquals(userOptional.get().getId(), rightUser.getId());
        assertEquals(userOptional.get().getName(), rightUser.getName());
        assertEquals(userOptional.get().getLastName(), rightUser.getLastName());
    }

    @Test
    public void findAllTest() {
        var users = userDao.findAll();
        assertEquals(users.size(), SIZE);
    }

    @Test
    public void saveTest() {
        var userOptional = userDao.save(createUser);
        assertTrue(userOptional.isPresent());
        assertEquals(userOptional.get().getName(), createUser.getName());
        assertEquals(userOptional.get().getLastName(), createUser.getLastName());
        assertEquals(userDao.findAll().size(), SIZE + 1);

    }

    @Test
    void updateTest() {
        var userOptional = userDao.update(FIND_ID, createUser);
        assertTrue(userOptional.isPresent());
        assertEquals(userOptional.get().getName(), createUser.getName());
        assertEquals(userOptional.get().getLastName(), createUser.getLastName());
        assertEquals(userDao.findAll().size(), SIZE);
    }

    @Test
    void deleteTest() {
        assertTrue(userDao.delete(FIND_ID));
        assertEquals(userDao.findAll().size(), SIZE - 1);
    }
}
