package by.aston.bank.dao;

import by.aston.bank.model.Account;
import by.aston.bank.model.Bank;
import by.aston.bank.model.User;
import by.aston.bank.utils.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BankDao implements Dao<Long, Bank> {

    private static final String FIND_ALL = """
            SELECT id, title
            FROM banks
            """;

    private static final String FIND_BY_ID = FIND_ALL.concat(" WHERE id = ?");

    private static final String FIND_USERS_BY_BANK_ID = """
            SELECT u.id as u_id, name, last_name
            FROM accounts a
                JOIN users u on a.user_id = u.id
            WHERE a.bank_id = ?
            """;

    private static final String FIND_ACCOUNTS_BY_BANK_ID = """
            SELECT a.id as id, number, cash, u.id as u_id, u.name, u.last_name
            FROM accounts a
                JOIN users u on a.user_id = u.id
            WHERE a.bank_id = ?
            """;

    private static final String CREATE_BANK = """
            INSERT INTO banks(title)
            VALUES(?)
            """;

    private static final String UPDATE_BY_ID = """
            UPDATE banks
            SET title = ?
            WHERE id = ?
            """;

    private static final String DELETE_BY_ID = """
            DELETE
            FROM banks
            WHERE id = ?
            """;


    @Override
    public Optional<Bank> findById(Long id) {
        return findById(id, FIND_BY_ID);
    }

    @Override
    public List<Bank> findAll() {
        return findAll(FIND_ALL);
    }

    @Override
    public Optional<Bank> save(Bank entity) {
        try {
        return save(entity, CREATE_BANK);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Bank> update(Long id, Bank entity) {
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
    public void setSaveRows(PreparedStatement statement, Bank bank) throws SQLException {
        statement.setString(1, bank.getTitle());
    }

    @Override
    public void setUpdateRows(PreparedStatement statement, Bank bank) throws SQLException {
        setSaveRows(statement, bank);
        statement.setLong(2, bank.getId());
    }

    @Override
    public Bank buildEntity(ResultSet resultSet) throws SQLException {
        Bank bank = new Bank();
        bank.setId(resultSet.getLong("id"));
        bank.setTitle(resultSet.getString("title"));
        bank.setUsers(createUsers(bank.getId()));
        bank.setAccounts(createAccounts(bank.getId()));
        return bank;
    }

    private List<User> createUsers(Long bankId) {
        try (var connection = ConnectionManager.open()) {
            List<User> userList = new ArrayList<>();
            var statement = connection.prepareStatement(FIND_USERS_BY_BANK_ID);
            statement.setLong(1, bankId);
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                userList.add(createUser(resultSet));
            }
            return userList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Account> createAccounts(Long bankId) {
        try (var connection = ConnectionManager.open()) {
            List<Account> accountList = new ArrayList<>();
            var statement = connection.prepareStatement(FIND_ACCOUNTS_BY_BANK_ID);
            statement.setLong(1, bankId);
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                accountList.add(creacteAccount(resultSet));
            }
            return accountList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Account creacteAccount(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getLong("id"));
        account.setNumber(resultSet.getString("number"));
        account.setUser(createUser(resultSet));
        return account;
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("u_id"));
        user.setName(resultSet.getString("name"));
        user.setLastName(resultSet.getString("last_name"));
        return user;
    }
}
