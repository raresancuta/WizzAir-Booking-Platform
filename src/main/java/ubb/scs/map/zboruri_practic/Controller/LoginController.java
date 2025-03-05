package ubb.scs.map.zboruri_practic.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ubb.scs.map.zboruri_practic.Domain.Client;
import ubb.scs.map.zboruri_practic.Service.Service;

import java.io.IOException;
import java.util.Optional;

public class LoginController {
    private Stage stage;
    private Service service;

    @FXML
    private TextField usernameField;

    public void setServ(Stage stage, Service service) {
        this.stage = stage;
        this.service = service;
    }

    public void handleLogin() throws IOException {
        String username = usernameField.getText();
        Optional<Client> clientOptional = service.verifyLoginCredentials(username);
        if(clientOptional.isEmpty()) {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR,"Error","User doesn't exist");
        }
        else {
            Client client = clientOptional.get();
            stage.close();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ubb/scs/map/zboruri_practic/client-view.fxml"));
                AnchorPane loginView = (AnchorPane) fxmlLoader.load();
                Scene scene = new Scene(loginView);
                stage.setScene(scene);
                stage.setTitle("WizzAir");
                stage.show();
                ClientController clientController = fxmlLoader.getController();
                clientController.setServ(stage, service,client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
