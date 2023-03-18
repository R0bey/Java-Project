package com.example.lab4;
import com.example.lab4.entities.Friendship;
import com.example.lab4.entities.User;
import com.example.lab4.service.FriendshipService;
import com.example.lab4.service.MessageService;
import com.example.lab4.service.UserService;
import com.example.lab4.utils.Observable;
import com.example.lab4.utils.Observer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserController implements Observer {


    private ObservableList<User> friends = FXCollections.observableArrayList();
    //private ObservableList<User> pending = FXCollections.observableArrayList();

    private ObservableList<User> users = FXCollections.observableArrayList();
    private UserService userService;
    private FriendshipService friendshipService;

    private MessageService messageService;
    private User currentUser;
    private User friend_to;

    @FXML
    private Label helloLabel;

    @FXML
    private Label info;

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<User> friendList;

    @FXML
    private Button searchButton;

    @FXML
    private ListView<User> others;

    @FXML
    private Button logoutButton;

    @FXML
    private Button add;

    @FXML
    private Button cancel;

    @FXML
    public void initialize(){
        //TODO
        cancel.setVisible(false);
    }

    @Override
    public void update() {
        /*String search = searchBar.getText();
        if (search.equals(""))
            return;
        ArrayList<User> users1 = userService.getAll();
        users.clear();
        for (var u : users1)
            if (friendshipService.findFriendship(currentUser,u)==null && (u.getFirstName().startsWith(search) || u.getLastName().startsWith(search)))
                users.add(u);
        others.setItems(users);*/
        init();
    }

    @FXML
    private void selected(){ //triggered at the onMouseClicked event
        //System.out.println("hey");
        String selectedItem = String.valueOf(others.getSelectionModel().getSelectedItem());
        if (selectedItem.equals("null"))
            return;

        for (var u : users){
            if ((u.getFirstName() + " " + u.getLastName()).equals(selectedItem)){
                if (friendshipService.findFriendship(u, currentUser)!=null) {
                    this.add.setVisible(false);
                    this.cancel.setVisible(true);
                    return;
                }
            }
        }
        this.add.setVisible(true);
        this.cancel.setVisible(false);
        return;
    }
    public void setServices(UserService userService, FriendshipService friendshipService, MessageService messageService, User currentUser){
        this.userService=userService;
        this.userService.addObserver(this);
        this.friendshipService=friendshipService;
        this.friendshipService.addObserver(this);
        this.messageService = messageService;
        this.currentUser=currentUser;
        init();
    }


    @FXML
    public void cancel(){
        String selectedItem = String.valueOf(others.getSelectionModel().getSelectedItem());
        for (var u : users){
            if ((u.getFirstName() + " " + u.getLastName()).equals(selectedItem)){
                this.friendshipService.deletefriendship(currentUser.getFirstName(),u.getFirstName(),currentUser.getLastName(),u.getLastName());
                //init();
                return;
            }
        }
    }

    @FXML
    public void init(){
        String string = "Hello, " + this.currentUser.getFirstName() + "!";
        helloLabel.setText(string);
        friends.clear();
        ArrayList<Friendship> friendsOfUser = friendshipService.getFriends(currentUser);
        //ArrayList<User> users = new ArrayList<>();
        for (Friendship f : friendsOfUser) {
            if (f.getFriend1().equals(currentUser) && f.getStatus().equals("friends"))
                friends.add(f.getFriend2());
            if (f.getFriend2().equals(currentUser) && f.getStatus().equals("friends"))
                friends.add(f.getFriend1());
        }
        friendList.setItems(friends);

        users.clear();
        ArrayList<User> users1 = userService.getAll();
        for (var u : users1){
            Friendship f = friendshipService.findFriendship(currentUser,u);
            if (f==null && !(u.equals(currentUser)))
                users.add(u);
            else if (f!=null && f.getStatus().equals("pending"))
                users.add(u);
        }
        others.setItems(users);
    }

    @FXML
    public void searchClick(){
        String search = searchBar.getText();
        if (search.equals("")) {
            ArrayList<User> users1 = userService.getAll();
            users.clear();
            for (var u : users1)
                if (friendshipService.findFriendship(currentUser,u)==null && !(u.equals(currentUser)))
                    users.add(u);
            others.setItems(users);
            return;
        }
        ArrayList<User> users1 = userService.getAll();
        users.clear();
        for (var u : users1)
            if (friendshipService.findFriendship(currentUser,u)==null && (u.getFirstName().startsWith(search) || u.getLastName().startsWith(search)) && !(u.equals(currentUser)))
                users.add(u);
        others.setItems(users);
    }

    @FXML
    public void infoClick(){
        String selectedItem = String.valueOf(friendList.getSelectionModel().getSelectedItem());
        if (selectedItem.equals("null")) {
            this.info.setText("Please select a friend first!");
            return;
        }
        //System.out.println(selectedItem);

        ArrayList<Friendship> friendsOfUser = friendshipService.getFriends(currentUser);
        for (var f : friendsOfUser)
        {
            String fN = f.getFriend1().getFirstName();
            String lN = f.getFriend1().getLastName();
            String name = fN+" "+lN;

            //System.out.println(name);

            if (name.equals(selectedItem)) {
                String s = f.getFriendsFrom().toString();
                this.info.setText(s);
                return;
            }
            fN=f.getFriend2().getFirstName();
            lN=f.getFriend2().getLastName();
            name=fN+" "+lN;
            if (name.equals(selectedItem)){
                String s = f.getFriendsFrom().toString();
                this.info.setText(s);
                return;
            }
        }
    }

    @FXML
    public void addFriend(){
        String selectedItem = String.valueOf(others.getSelectionModel().getSelectedItem());
        if (selectedItem.equals("null")) {
            this.info.setText("Please select a friend first!");
            return;
        }
        //System.out.println(selectedItem);

        ArrayList<User> users1 = userService.getAll();
        for (var u : users1){
            if ((u.getFirstName() + " " + u.getLastName()).equals(selectedItem)){
                this.friendshipService.add(currentUser.getFirstName(), u.getFirstName(), currentUser.getLastName(), u.getLastName(), "pending");
                init();
            }
         }
    }

    @FXML
    public void deleteFriend(){
        String selectedItem = String.valueOf(friendList.getSelectionModel().getSelectedItem());
        if (selectedItem.equals("null")) {
            this.info.setText("Please select a friend first!");
            return;
        }

        for (var u : friends){
            if ((u.getFirstName() + " " + u.getLastName()).equals(selectedItem)){
                this.friendshipService.deletefriendship(currentUser.getFirstName(),u.getFirstName(),currentUser.getLastName(),u.getLastName());
                init();
                return;
            }
        }
    }

    public void logout(){
        Window window = this.searchButton.getScene().getWindow();
        ((Stage) window).close();
    }

    public void deleteAccount(){
        this.userService.removeUser(currentUser.getFirstName(), currentUser.getLastName());
        Platform.exit();
    }

    public void requestClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("requests-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);


        //HelloController controller = new HelloController();

        //fxmlLoader.setController(controller);
        Stage newStage = new Stage();
        //System.out.println(currentUser.getFirstName());
        RequestsController controller = fxmlLoader.getController();
        controller.setServices(userService, friendshipService, currentUser);
        newStage.setTitle("RequestsGUI");
        newStage.setScene(scene);
        newStage.show();
    }

    public void chat() throws IOException {
        String selectedItem = String.valueOf(friendList.getSelectionModel().getSelectedItem());
        if (selectedItem.equals("null")) {
            this.info.setText("Please select a friend first!");
            return;
        }
        //System.out.println(selectedItem);

        ArrayList<Friendship> friendsOfUser = friendshipService.getFriends(currentUser);
        for (var f : friendsOfUser)
        {
            String fN = f.getFriend1().getFirstName();
            String lN = f.getFriend1().getLastName();
            String name = fN+" "+lN;

            //System.out.println(name);

            if (name.equals(selectedItem)) {
                this.friend_to=f.getFriend1();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("messages-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);


                //HelloController controller = new HelloController();

                //fxmlLoader.setController(controller);
                Stage newStage = new Stage();
                //System.out.println(currentUser.getFirstName());
                MessageController controller = fxmlLoader.getController();
                controller.setServices(messageService, currentUser, friend_to);
                newStage.setTitle("Chat");
                newStage.setScene(scene);
                newStage.show();
                return;
            }
            fN=f.getFriend2().getFirstName();
            lN=f.getFriend2().getLastName();
            name=fN+" "+lN;
            if (name.equals(selectedItem)){
                this.friend_to=f.getFriend2();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("messages-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);


                //HelloController controller = new HelloController();

                //fxmlLoader.setController(controller);
                Stage newStage = new Stage();
                //System.out.println(currentUser.getFirstName());
                MessageController controller = fxmlLoader.getController();
                controller.setServices(messageService, currentUser, friend_to);
                newStage.setTitle("Chat");
                newStage.setScene(scene);
                newStage.show();
                return;
            }
        }
    }
}
