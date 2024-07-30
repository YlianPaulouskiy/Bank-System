package by.aston.bank.dao;

import by.aston.bank.model.BaseEntity;
import by.aston.bank.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface Dao<K, E extends BaseEntity> {

    Optional<E> findById(K id);

    List<E> findAll();

    Optional<E> save(E entity);

    Optional<E> update(K id, E entity);

    boolean delete(K id);

    E buildEntity(ResultSet resultSet) throws SQLException;

    void setSaveRows(PreparedStatement statement, E entity) throws SQLException;

    void setUpdateRows(PreparedStatement statement, E entity) throws SQLException;

    default Optional<E> findById(K id, String sqlQuery) {
        try (var connection = ConnectionManager.open()) {
            var statement = connection.prepareStatement(sqlQuery);
            statement.setObject(1, id);
            E entity = null;
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                entity = buildEntity(resultSet);
            }
            return Optional.ofNullable(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    default List<E> findAll(String sqlQuery) {
        try (var connection = ConnectionManager.open()) {
            var statement = connection.prepareStatement(sqlQuery);
            List<E> entityList = new ArrayList<>();
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                entityList.add(buildEntity(resultSet));
            }
            return entityList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    default boolean delete(K id, String sqlQuery) throws SQLException {
        var connection = ConnectionManager.open();
        try  {
            connection.setAutoCommit(false);
            var statement = connection.prepareStatement(sqlQuery);
            statement.setObject(1, id);
            int updateRows = statement.executeUpdate();
            connection.commit();
            return updateRows > 0;
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            connection.close();
        }
    }

    default Optional<E> save(E entity, String sqlQuery) throws SQLException {
        var connection = ConnectionManager.open();
        try {
            connection.setAutoCommit(false);
            var statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            setSaveRows(statement, entity);
            statement.executeUpdate();
            var resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getLong("id"));
            }
            if (entity.getId() != null) connection.commit();
            return entity.getId() == null ? Optional.empty() : Optional.of(entity);
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            connection.close();
        }
    }

    default Optional<E> update(K id, E entity, String sqlQuery) throws SQLException {
        var connection = ConnectionManager.open();
        entity.setId((Long) id);
        try {
            connection.setAutoCommit(false);
            var statement = connection.prepareStatement(sqlQuery);
            setUpdateRows(statement, entity);
            int updateRows = statement.executeUpdate();
            if (updateRows > 0) connection.commit();
            return updateRows > 0 ? Optional.of(entity) : Optional.empty();
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            connection.close();
        }
    }

}
