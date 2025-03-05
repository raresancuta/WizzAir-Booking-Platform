package ubb.scs.map.zboruri_practic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ubb.scs.map.zboruri_practic.Controller.LoginController;
import ubb.scs.map.zboruri_practic.Domain.Client;
import ubb.scs.map.zboruri_practic.Repository.ClientRepository;
import ubb.scs.map.zboruri_practic.Repository.FlightRepository;
import ubb.scs.map.zboruri_practic.Repository.TicketRepository;
import ubb.scs.map.zboruri_practic.Service.Service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HelloApplication extends Application {
    private Service service;
    @Override
    public void start(Stage stage) throws IOException {
        String username = "postgres";
        String password = "postgres";
        String url = "jdbc:postgresql://localhost:5432/Zboruri";
        ClientRepository clientRepository = new ClientRepository( username,password,url);
        FlightRepository flightRepository = new FlightRepository( username,password,url);
        TicketRepository ticketRepository = new TicketRepository( username,password,url);
        service = new Service(clientRepository,flightRepository, ticketRepository);
        initLoginView(stage);

    }
    public void initLoginView(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ubb/scs/map/zboruri_practic/login-view.fxml"));
        AnchorPane loginView = (AnchorPane) fxmlLoader.load();
        Scene scene = new Scene(loginView);
        stage.setScene(scene);
        stage.setTitle("Zboruri");
        stage.show();
        LoginController loginController = fxmlLoader.getController();
        loginController.setServ(stage,service);
    }

    public static void main(String[] args) {
        launch();
    }
}