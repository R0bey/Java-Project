package com.example.lab4.repository.DBRepo;

import com.example.lab4.entities.Friendship;
import com.example.lab4.entities.User;
import com.example.lab4.entities.validators.FriendshipValidator;
import com.example.lab4.entities.validators.ValidationException;
import com.example.lab4.entities.validators.Validator;
import com.example.lab4.repository.MemoryRepo.FriendshipRepository;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

public class DBFriendshipRepository extends FriendshipRepository {
    private String url;

    private String username;

    private String password;

    private Validator<Friendship> validator;

    //private ArrayList<Friendship> friendships;

    public DBFriendshipRepository(String url, String username, String password){
        this.url=url;
        this.username=username;
        this.password=password;
        validator = new FriendshipValidator();
        friendships = new ArrayList<>();
        this.save();
    }
    public void save(){
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from friendships");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int id1 = resultSet.getInt("id_friend1");
                String firstName1 = resultSet.getString("first_name_friend1");
                String lastName1 = resultSet.getString("last_name_friend1");
                int id2 = resultSet.getInt("id_friend2");
                String firstName2 = resultSet.getString("first_name_friend2");
                String lastName2 = resultSet.getString("last_name_friend2");

                User user1 = new User(firstName1, lastName1);
                user1.setId(id1);

                User user2 = new User(firstName2, lastName2);
                user2.setId(id2);

                Friendship friendship = new Friendship(user1, user2);
                friendship.setId(id);

                friendships.add(friendship);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Friendship> get_all(){
        //return super.get_all();
        //TODO
        ArrayList<Friendship> prietenii = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from friendships");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int id1 = resultSet.getInt("id_friend1");
                String firstName1 = resultSet.getString("first_name_friend1");
                String lastName1 = resultSet.getString("last_name_friend1");
                int id2 = resultSet.getInt("id_friend2");
                String firstName2 = resultSet.getString("first_name_friend2");
                String lastName2 = resultSet.getString("last_name_friend2");
                java.util.Date  utilDate = new java.util.Date(resultSet.getDate("frm").getTime());

                User user1 = new User(firstName1, lastName1);
                user1.setId(id1);

                User user2 = new User(firstName2, lastName2);
                user2.setId(id2);

                Friendship friendship = new Friendship(user1, user2);
                friendship.setId(id);

                prietenii.add(friendship);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prietenii;
    }

    @Override
    public void add(Friendship friendship){
        this.validator.validate(friendship);
        String sql = "insert into friendships(id_friend1,first_name_friend1,last_name_friend1,id_friend2,first_name_friend2,last_name_friend2,frm,status) values (?,?,?,?,?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            User user1 = friendship.getFriend1();
            User user2 = friendship.getFriend2();
            ps.setInt(1,user1.getId());
            ps.setString(2,user1.getFirstName());
            ps.setString(3,user1.getLastName());
            ps.setInt(4,user2.getId());
            ps.setString(5,user2.getFirstName());
            ps.setString(6,user2.getLastName());
            LocalDateTime localDateTime = LocalDateTime.now();
            Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
            LocalDateTime now = LocalDateTime.now();
            java.sql.Date sqlDate = new java.sql.Date(java.util.Date.from(instant).getTime());
            ps.setDate(7, sqlDate);
            ps.setString(8, friendship.getStatus());
            //LocalDate date = LocalDate.now();
            //ps.setString(7,date.toString());

            ps.executeUpdate();


            //friendships.add(friendship);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Friendship friendship){
        this.validator.validate(friendship);
        String sql = "DELETE FROM friendships WHERE id=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1,friendship.getId());
            ps.executeUpdate();
            //friendships.remove(friendship);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Friendship friendship1, Friendship friendship2){
        this.validator.validate(friendship1);
        this.validator.validate(friendship2);
        super.update(friendship1,friendship2);
        String sql="UPDATE friendships SET id_friend1=?, first_name_friend1=?, last_name_friend1=?,id_friend2=?, first_name_friend2=?, last_name_friend2=? WHERE id=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1,friendship2.getFriend1().getId());
            ps.setString(2,friendship2.getFriend1().getFirstName());
            ps.setString(3,friendship2.getFriend1().getLastName());
            ps.setInt(4,friendship2.getFriend2().getId());
            ps.setString(5,friendship2.getFriend2().getFirstName());
            ps.setString(6,friendship2.getFriend2().getLastName());

            //LocalDate date = LocalDate.now();
            //ps.setString(7,date.toString());

            ps.setInt(7,friendship1.getId());

            ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Friendship findFriendship(User user1, User user2){
        //TODO
        String sql="SELECT * FROM friendships WHERE (id_friend1=? AND id_friend2=?) OR (id_friend1=? AND id_friend2=?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1,user1.getId());
            ps.setInt(2,user2.getId());
            ps.setInt(3,user2.getId());
            ps.setInt(4,user1.getId());
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int id1 = resultSet.getInt("id_friend1");
                String first_name_friend1 = resultSet.getString("first_name_friend1");
                String last_name_friend1 = resultSet.getString("last_name_friend1");
                int id2 = resultSet.getInt("id_friend2");
                String first_name_friend2 = resultSet.getString("first_name_friend2");
                String last_name_friend2 = resultSet.getString("last_name_friend2");
                int id = resultSet.getInt("id");
                String status = resultSet.getString("status");
                User u1 = new User(first_name_friend1, last_name_friend1);
                u1.setId(id1);
                User u2 = new User(first_name_friend2, last_name_friend2);
                u2.setId(id2);
                Friendship friendship = new Friendship(u1,u2,status);
                friendship.setId(id);
                return friendship;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //return super.findFriendship(user1,user2);
        return null;
    }

    @Override
    public void userUpdate(User user1, User user2){
        //super.userUpdate(user1,user2);
        //TODO
        String sql = "UPDATE friendships SET first_name_friend1=?, last_name_friend1=? WHERE id_friend1=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            //ps.setInt(1,user2.getId());
            ps.setString(1,user2.getFirstName());
            ps.setString(2,user2.getLastName());
            ps.setInt(3,user1.getId());
            ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        sql = "UPDATE friendships SET first_name_friend2=?, last_name_friend2=? WHERE id_friend2=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            //ps.setInt(1,user2.getId());
            ps.setString(1,user2.getFirstName());
            ps.setString(2,user2.getLastName());
            ps.setInt(3,user1.getId());
            ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void userRemove(User user) {
        //super.userRemove(user);
        //TODO
        String sql = "DELETE FROM friendships WHERE id_friend1=? OR id_friend2=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1,user.getId());
            ps.setInt(2,user.getId());
            ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Friendship> getFriends(User user){
        //return super.get_all();
        //TODO
        ArrayList<Friendship> prietenii = new ArrayList<>();
        String sql="SELECT * from friendships where (first_name_friend1=? and last_name_friend1=?) or (first_name_friend2=? and last_name_friend2=?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1,user.getFirstName());
            ps.setString(2,user.getLastName());
            ps.setString(3,user.getFirstName());
            ps.setString(4,user.getLastName());
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                int id=resultSet.getInt("id");
                int id1=resultSet.getInt("id_friend1");
                String firstName1 = resultSet.getString("first_name_friend1");
                String lastName1 = resultSet.getString("last_name_friend1");
                int id2 = resultSet.getInt("id_friend2");
                String firstName2 = resultSet.getString("first_name_friend2");
                String lastName2 = resultSet.getString("last_name_friend2");
                String status = resultSet.getString("status");
                java.util.Date  utilDate = new java.util.Date(resultSet.getDate("frm").getTime());

                User user1 = new User(firstName1, lastName1);
                user1.setId(id1);

                User user2 = new User(firstName2, lastName2);
                user2.setId(id2);

                Friendship friendship = new Friendship(user1, user2, status, utilDate);
                friendship.setId(id);

                prietenii.add(friendship);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prietenii;
}
}
