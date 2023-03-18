package com.example.lab4.repository.DBRepo;

import com.example.lab4.entities.Message;
import com.example.lab4.entities.User;

import java.sql.*;
import java.util.ArrayList;

public class DBMessageRepo {
    private String url;
    private String username;
    private String password;
    protected ArrayList<Message> messages;

    public DBMessageRepo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.messages = new ArrayList<>();
    }

    public ArrayList<Message> findMessages(User u1, User u2){
        ArrayList<Message> mesaje = new ArrayList<>();

        String sql="SELECT * from messages where (first_name_friend1=? AND last_name_friend1=? and first_name_friend2=? AND last_name_friend2=?) or (first_name_friend1=? AND last_name_friend1=? and first_name_friend2=? AND last_name_friend2=?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            //ps.setInt(0,user.getId());
            ps.setString(1, u1.getFirstName());
            ps.setString(2, u1.getLastName());
            ps.setString(3, u2.getFirstName());
            ps.setString(4, u2.getLastName());
            ps.setString(5, u2.getFirstName());
            ps.setString(6, u2.getLastName());
            ps.setString(7, u1.getFirstName());
            ps.setString(8, u1.getLastName());

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String fn1 = resultSet.getString("first_name_friend1");
                String ln1 = resultSet.getString("last_name_friend1");
                String fn2 = resultSet.getString("first_name_friend2");
                String ln2 = resultSet.getString("last_name_friend2");
                String content = resultSet.getString("content");
                if (u1.getFirstName().equals(fn1) && u1.getLastName().equals(ln1)){
                    Message message = new Message(u1,u2,content);
                    mesaje.add(message);
                }
                else {
                    Message message = new Message(u2,u1,content);
                    mesaje.add(message);
                }

            }
            //ps.executeUpdate();
            //users.add(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mesaje;
    }

    public void addMessage(Message m){
        String sql = "insert into messages (first_name_friend1, last_name_friend1, first_name_friend2,last_name_friend2, content) values (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            //ps.setInt(0,user.getId());
            ps.setString(1, m.getUser_to().getFirstName());
            ps.setString(2, m.getUser_to().getLastName());
            ps.setString(3, m.getUser_from().getFirstName());
            ps.setString(4, m.getUser_from().getLastName());
            ps.setString(5, m.getMessage());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
