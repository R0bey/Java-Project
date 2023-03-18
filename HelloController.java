package com.example.lab4;

import com.example.lab4.entities.Friendship;
import com.example.lab4.entities.User;
import com.example.lab4.service.FriendshipService;
import com.example.lab4.service.MessageService;
import com.example.lab4.service.UserService;
import com.example.lab4.utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloController implements Observer {

    private ObservableList<User> friends = FXCollections.observableArrayList();
    private ObservableList<User> pending = FXCollections.observableArrayList();

    private ObservableList<User> users = FXCollections.observableArrayList();
    private UserService userService;
    private FriendshipService friendshipService;

    private MessageService messageService;
    private User currentUser;

    @FXML
    private Label welcomeText;

    @FXML
    private Label warnings;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private PasswordField password;

    @FXML
    private Button login;

    //@FXML
    //private Button logout;

    @FXML
    private Button register;

    /*@FXML
    private ListView<User> friendList;

    @FXML
    private ListView<User> pendingFriends;

    @FXML
    private ListView<User> others;


     */

    @FXML
    public void initialize(){
        //TODO
        //logout.setVisible(false);
    }

    @FXML
    public void loginClick() throws IOException {
        String fN= firstName.getText();
        String lN= lastName.getText();
        String  p= password.getText();
        if (userService.findUser(fN, lN)==null){
            warnings.setText("User doesn't exist! Try to register!");
            return;
        }
        //System.out.println(p);
        currentUser = userService.findUser(fN, lN);
        if (!(currentUser.getPassword().equals(p))){
            warnings.setText("Incorrect password");
            return;
        }
        //warnings.setText("Hello " + currentUser.getFirstName() + "!");
        // login.setVisible(false);
        //register.setVisible(false);
        //logout.setVisible(true);
        //init();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("user-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);


        //HelloController controller = new HelloController();

        //fxmlLoader.setController(controller);
        Stage newStage = new Stage();
        System.out.println(currentUser.getFirstName());
        UserController controller = fxmlLoader.getController();
        controller.setServices(userService, friendshipService, messageService, currentUser);
        newStage.setTitle("UserGUI");
        newStage.setScene(scene);
        newStage.show();
        //initialize();
    }

    /*
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

     */

    /*
    @FXML
    public void logoutClick(){
        firstName.clear();
        lastName.clear();
        password.clear();
        //friendList.
    }

     */

    public void setServices(UserService userService, FriendshipService friendshipService, MessageService messageService){
        this.userService=userService;
        this.friendshipService=friendshipService;
        this.messageService=messageService;
        this.userService.addObserver(this);
        this.friendshipService.addObserver(this);
    }

    @Override
    public void update(){
        //initialize();
    }

    /*@FXML
    public void init(){
        ArrayList<Friendship> friendsOfUser = friendshipService.getFriends(currentUser);
        //ArrayList<User> users = new ArrayList<>();
        for (Friendship f : friendsOfUser) {
            if (f.getFriend1().equals(currentUser) && f.getStatus().equals("friends"))
                friends.add(f.getFriend2());
            if (f.getFriend2().equals(currentUser) && f.getStatus().equals("friends"))
                friends.add(f.getFriend1());
            if (f.getFriend1().equals(currentUser) && f.getStatus().equals("pending"))
                pending.add(f.getFriend2());
            if (f.getFriend2().equals(currentUser) && f.getStatus().equals("pending"))
                pending.add(f.getFriend1());
        }
        friendList.setItems(friends);
        pendingFriends.setItems(pending);

        ArrayList<User> users1 = userService.getAll();
        for (var u : users1)
            if (friendshipService.findFriendship(currentUser,u)==null && !(u.equals(currentUser)))
                users.add(u);
        others.setItems(users);
    }
    */

    @FXML
    public void registerClick() throws IOException{
        String fN= firstName.getText();
        String lN= lastName.getText();
        String  p= password.getText();
        if (userService.findUser(fN, lN)!=null){
            warnings.setText("User already exists!");
            return;
        }
        if (fN.equals("") || lN.equals("") || p.equals("")){
            warnings.setText("Please complete all fields!");
            return;
        }
        userService.addUser(fN, lN, p);

        this.currentUser = userService.findUser(fN,lN);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("user-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);


        //HelloController controller = new HelloController();

        //fxmlLoader.setController(controller);
        Stage newStage = new Stage();
        System.out.println(currentUser.getFirstName());
        UserController controller = fxmlLoader.getController();
        controller.setServices(userService, friendshipService, messageService, currentUser);
        newStage.setTitle("UserGUI");
        newStage.setScene(scene);
        newStage.show();
    }
}