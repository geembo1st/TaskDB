package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final String USERNAME = "root1";
    private final String PASSWORD  = "root";
    private final String URL  = "jdbc:mysql://localhost:3306/mydb";
    private final String INSERT = "INSERT INTO user (name, lastname, age) VALUES (?,?,?)";
    private final String CREATE = "CREATE TABLE user ( id int, name varchar(45), lastName varchar(45), age int)";
    private final String REMOVE_BY_ID = "DELETE FROM user WHERE id = ?";
    private final String DROP = "DROP TABLE user";
    private final String CLEAN = "DELETE FROM user";
    private final String GET_ALL = "SELECT * FROM user";
    private Connection connection;
    public UserDaoJDBCImpl() {

    }

    private Connection getConnection() {
        try {
            if(connection == null || connection.isClosed()) {
                return connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            }
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public void createUsersTable()  {
        try(Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(CREATE)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try(Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(DROP)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(INSERT)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try(Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(REMOVE_BY_ID)) {
            statement.setInt(1, (int) id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(GET_ALL)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                users.add(new User(resultSet.getString("name"), resultSet.getString("lastName"),
                        resultSet.getByte("age")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try(Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(CLEAN)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
