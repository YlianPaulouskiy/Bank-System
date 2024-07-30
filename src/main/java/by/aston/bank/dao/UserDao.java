package by.aston.bank.dao;

import by.aston.bank.model.Account;
import by.aston.bank.model.Bank;
import by.aston.bank.model.User;
import by.aston.bank.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserDao implements Dao<Long, User> {

    private static final String FIND_ALL = """
            SELECT id, name, last_name
            FROM users
            """;

    private static final String FIND_BY_ID = FIND_ALL.concat(" WHERE id = ?");

    private static final String FIND_ACCOUNTS_BY_USER_ID = """
            SELECT a.id as a_id, a.number, a.cash, b.id as b_id, b.title
            FROM accounts a
            JOIN banks b ON a.bank_id = b.id
            WHERE user_id = ?
            """;

    private static final String CREATE_USER = """
            INSERT INTO users(name, last_name)
            VALUES(?,?)
            """;

    private static final String UPDATE_BY_ID = """
            UPDATE users
            SET name = ?,
             last_name = ?
            WHERE id = ?
            """;

    private static final String DELETE_BY_ID = """
            DELETE
            FROM users
            WHERE id = ?
            """;


    @Override
    public Optional<User> findById(Long id) {
        return findById(id, FIND_BY_ID);
    }

    @Override
    public List<User> findAll() {
        return findAll(FIND_ALL);
    }

    @Override
    public Optional<User> save(User entity) {
        try {
        return save(entity, CREATE_USER);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> update(Long id, User entity) {
        try {
            return update(id, entity, UPDATE_BY_ID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            return delete(id, DELETE_BY_ID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setSaveRows(PreparedStatement statement, User entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getLastName());
    }

    @Override
    public void setUpdateRows(PreparedStatement statement, User entity) throws SQLException {
        setSaveRows(statement, entity);
        statement.setLong(3, entity.getId());
    }

    @Override
    public User buildEntity(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setName(resultSet.getString("name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setAccounts(createAccountList(user.getId()));
        return user;
    }

    private List<Account> createAccountList(Long userId) {
        try (var connection = ConnectionManager.open()) {
            List<Account> accountList = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ACCOUNTS_BY_USER_ID);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                accountList.add(createAccount(resultSet));
            }
            return accountList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Account createAccount(ResultSet resultSet) {
        try {
            Account account = new Account();
            account.setId(resultSet.getLong("a_id"));
            account.setNumber(resultSet.getString("number"));
            account.setBank(createBank(resultSet));
            return account;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Bank createBank(ResultSet resultSet) {
        try {
            Bank bank = new Bank();
            bank.setId(resultSet.getLong("b_id"));
            bank.setTitle(resultSet.getString("title"));
            return bank;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
