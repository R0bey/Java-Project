package com.example.lab4;

import com.example.lab4.entities.Message;
import com.example.lab4.entities.MessageFormat;
import com.example.lab4.entities.User;
import com.example.lab4.service.FriendshipService;
import com.example.lab4.service.MessageService;
import com.example.lab4.service.UserService;
import com.example.lab4.utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;



public class MessageController implements Observer {

    MessageService messageService;

    User currentUser;
    User userTo;

    @FXML
    protected TableView<MessageFormat> tableView;

    @FXML
    protected TableColumn<MessageFormat, String> tableColumn1;

    @FXML
    protected TableColumn<MessageFormat,String> tableColumn2;

    @FXML
    private TextField textField;

    @FXML
    private Label label;

    @FXML
    private Button button;

    ObservableList<MessageFormat> list = FXCollections.observableArrayList();


    public void initialize(){
        //init();
        //String s = "You are now chatting with " + this.userTo.getFirstName();
        //this.label.setText(s);
        //this.tableColumn1.setText(this.userTo.getFirstName() + this.userTo.getLastName());
        this.tableColumn1.setCellValueFactory(new PropertyValueFactory<>("M2"));
        this.tableColumn2.setCellValueFactory(new PropertyValueFactory<>("M1"));
    }

    @FXML
    public void add(){
        String s = textField.getText();
        if(s.equals("")){
            this.label.setText("Please provide a valid message!");
            return;
        }
        this.messageService.add(currentUser,userTo,s);
        this.textField.clear();
    }


    public void setServices(MessageService messageService, User currentUser, User userTo){
        this.messageService=messageService;
        this.messageService.addObserver(this);
        this.currentUser=currentUser;
        this.userTo=userTo;
        init();
    }

    public void init(){
        String s = "You are now chatting with " + this.userTo.getFirstName() + "!";
        this.label.setText(s);
        this.tableColumn1.setText(this.userTo.getFirstName() + " "+ this.userTo.getLastName());
        list.clear();
        ArrayList<Message> messages = this.messageService.find(this.currentUser,this.userTo);
        for (var m : messages){
            if (m.getUser_from().equals(this.currentUser)) {
                MessageFormat mF= new MessageFormat("",m.getMessage());
                //tableView.getItems().add(mF);
                list.add(mF);
            }
            else {
                MessageFormat mF=new MessageFormat(m.getMessage(), "");
                //tableView.getItems().add(mF);
                list.add(mF);
            }
        }
        tableView.setItems(list);
        //tableView.getColumns().addAll(tableColumn1,tableColumn2);
    }

    @Override
    public void update() {
        init();
    }
}
