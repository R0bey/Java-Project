package com.example.lab4.repository.DBRepo;

import com.example.lab4.entities.Friendship;
import com.example.lab4.entities.User;
import com.example.lab4.entities.validators.UserValidator;
import com.example.lab4.entities.validators.ValidationException;
import com.example.lab4.entities.validators.Validator;
import com.example.lab4.repository.MemoryRepo.UserRepository;

import java.sql.*;
import java.util.ArrayList;

public class DBUserRepository extends UserRepository {
    private String url;
    private String username;
    private String password;
    private Validator<User> validator;
    //private ArrayList<User> users;

    public DBUserRepository(String url, String username, String password){
        this.url=url;
        this.username=username;
        this.password=password;
        validator = new UserValidator();
        users = new ArrayList<>();
        save();
    }

    public void save(){
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from users");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String password = resultSet.getString("password");

                User user = new User(firstName, lastName, password);
                user.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<User> get_all(){

        //return this.users;
        //TODO
        ArrayList<User> useri = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from users");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                User user = new User(firstName, lastName);
                user.setId(id);
                useri.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return useri;
    }

    @Override
    public User findUser(String firstName, String lastName){
        //return super.findUser(firstName,lastName);
        //TODO
        String sql = "SELECT * FROM users WHERE first_name=? AND last_name=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            //ps.setInt(0,user.getId());
            ps.setString(1, firstName);
            ps.setString(2, lastName);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String fn = resultSet.getString("first_name");
                String ln = resultSet.getString("last_name");
                String p = resultSet.getString("password");
                User user = new User(fn,ln,p);
                user.setId(id);
                return user;
            }
            //ps.executeUpdate();
            //users.add(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void add(User user){
        String sql = "insert into users (first_name, last_name, password) values (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            //ps.setInt(0,user.getId());
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPassword());

            ps.executeUpdate();
            //users.add(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete (User user){
        //super.delete(user);
        String sql="DELETE FROM users WHERE id=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, user.getId());
            ps.executeUpdate();
            //users.remove(user);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update (User user1, User user2){
        //super.update(user1,user2);
        String sql="UPDATE users SET first_name=?, last_name=? WHERE id=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1,user2.getFirstName());
            ps.setString(2,user2.getLastName());
            ps.setInt(3,user1.getId());
            ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
