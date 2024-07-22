package by.aston.bank.dao;

import by.aston.bank.model.Account;
import by.aston.bank.model.Bank;
import by.aston.bank.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AccountDao implements Dao<Long, Account> {

    private static final String FIND_ALL = """
            SELECT a.id as a_id, number, cash, b.id as b_id, title, u.id as u_id, name, last_name
            FROM accounts a
                       JOIN banks b on a.bank_id = b.id
                       JOIN  users u on a.user_id = u.id
            """;

    private static final String FIND_BY_ID = FIND_ALL.concat(" WHERE a.id = ?");

    private static final String CREATE_ACCOUNT = """
            INSERT INTO accounts(number, cash, bank_id, user_id)
            VALUES(?, ?, ?, ?)
            """;

    private static final String UPDATE_BY_ID = """
            UPDATE accounts
            SET number = ?,
                   cash = ?,
                   bank_id = ?,
                   user_id = ?
            WHERE id = ?
            """;

    private static final String DELETE_BY_ID = """
            DELETE
            FROM accounts
            WHERE id = ?
            """;

    @Override
    public Optional<Account> findById(Long id) {
        return findById(id, FIND_BY_ID);
    }

    @Override
    public List<Account> findAll() {
        return findAll(FIND_ALL);
    }

    @Override
    public Optional<Account> save(Account entity) {
        try {
            return save(entity, CREATE_ACCOUNT);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Account> update(Long id, Account entity) {
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
    public Account buildEntity(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getLong("a_id"));
        account.setNumber(resultSet.getString("number"));
        account.setCash(resultSet.getBigDecimal("cash"));
        account.setBank(createBank(resultSet));
        account.setUser(createUser(resultSet));
        return account;
    }

    private Bank createBank(ResultSet resultSet) throws SQLException {
        Bank bank = new Bank();
        bank.setId(resultSet.getLong("b_id"));
        bank.setTitle(resultSet.getString("title"));
        return bank;
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("u_id"));
        user.setName(resultSet.getString("name"));
        user.setLastName(resultSet.getString("last_name"));
        return user;
    }

    @Override
    public void setSaveRows(PreparedStatement statement, Account entity) throws SQLException {
        statement.setString(1, entity.getNumber());
        statement.setBigDecimal(2, entity.getCash());
        statement.setLong(3, entity.getBank().getId());
        statement.setLong(4, entity.getUser().getId());
    }

    @Override
    public void setUpdateRows(PreparedStatement statement, Account entity) throws SQLException {
        setSaveRows(statement, entity);
        statement.setLong(5, entity.getId());
    }
}
