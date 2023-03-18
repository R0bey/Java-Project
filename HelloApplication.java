package com.example.lab4;

import com.example.lab4.repository.DBRepo.DBFriendshipRepository;
import com.example.lab4.repository.DBRepo.DBMessageRepo;
import com.example.lab4.repository.DBRepo.DBUserRepository;
import com.example.lab4.service.FriendshipService;
import com.example.lab4.service.MessageService;
import com.example.lab4.service.UserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    public static DBUserRepository repository = new DBUserRepository("jdbc:postgresql://localhost:5432/academic", "postgres", "postgres");
    public static DBFriendshipRepository friendshipRepository = new DBFriendshipRepository("jdbc:postgresql://localhost:5432/academic", "postgres", "postgres");
    public static DBMessageRepo messageRepo = new DBMessageRepo("jdbc:postgresql://localhost:5432/academic", "postgres", "postgres");
    public static UserService service = new UserService(repository, friendshipRepository);
    public static FriendshipService friendshipService = new FriendshipService(friendshipRepository, repository);

    public static MessageService messageService = new MessageService(messageRepo);

    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);


            //HelloController controller = new HelloController();

            //fxmlLoader.setController(controller);
            HelloController controller = fxmlLoader.getController();
            controller.setServices(service, friendshipService, messageService);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch();
    }
}