package com.example.lab4;
import com.example.lab4.entities.Friendship;
import com.example.lab4.entities.User;
import com.example.lab4.service.FriendshipService;
import com.example.lab4.service.UserService;
import com.example.lab4.utils.Observable;
import com.example.lab4.utils.Observer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RequestsController implements Observer {

    //private ObservableList<User> friends = FXCollections.observableArrayList();
    private ObservableList<User> pending = FXCollections.observableArrayList();

    //private ObservableList<User> users = FXCollections.observableArrayList();
    private UserService userService;
    private FriendshipService friendshipService;

    private User currentUser;

    @FXML
    private ListView list;

    @FXML
    private Button accept;

    @FXML
    private Button decline;

    @FXML
    private Label messages;

    @Override
    public void update(){
        //initialize();
    }

    public void setServices(UserService userService, FriendshipService friendshipService, User currentUser){
        this.userService=userService;
        this.userService.addObserver(this);
        this.friendshipService=friendshipService;
        this.friendshipService.addObserver(this);
        this.currentUser=currentUser;
        init();
    }

    public void init(){
        ArrayList<Friendship> friendsOfUser = friendshipService.getFriends(currentUser);
        //ArrayList<User> users = new ArrayList<>();
        for (Friendship f : friendsOfUser) {
            if (f.getFriend2().equals(currentUser) && f.getStatus().equals("pending"))
                pending.add(f.getFriend1());
        }
        list.setItems(pending);
    }

    public void decline(){
        String selectedItem = String.valueOf(list.getSelectionModel().getSelectedItem());
        if (selectedItem.equals("null")) {
            this.messages.setText("Please select a user!");
            return;
        }

        for (var u : pending){
            if ((u.getFirstName() + " " + u.getLastName()).equals(selectedItem)){
                friendshipService.deletefriendship(currentUser.getFirstName(),u.getFirstName(), currentUser.getLastName(),u.getLastName());
                pending.remove(u);
                return;
            }
        }
    }

    public void accept(){
        String selectedItem = String.valueOf(list.getSelectionModel().getSelectedItem());
        if (selectedItem.equals("null")) {
            this.messages.setText("Please select a user!");
            return;
        }

        for (var u : pending){
            if ((u.getFirstName() + " " + u.getLastName()).equals(selectedItem)){
                friendshipService.deletefriendship(currentUser.getFirstName(),u.getFirstName(), currentUser.getLastName(),u.getLastName());
                friendshipService.add(currentUser.getFirstName(),u.getFirstName(), currentUser.getLastName(),u.getLastName(),"friends");
                pending.remove(u);
                return;
            }
        }
    }
}
