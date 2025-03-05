package ubb.scs.map.zboruri_practic.Controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ubb.scs.map.zboruri_practic.Domain.Client;
import ubb.scs.map.zboruri_practic.Domain.Ticket;
import ubb.scs.map.zboruri_practic.Service.Service;
import ubb.scs.map.zboruri_practic.Utils.Observer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClientController implements Observer {
    private Stage stage;
    private Service service;
    private Client client;
    ObservableList<Ticket> model = FXCollections.observableArrayList();

    @FXML
    private TableView<Ticket> ticketsTable;
    @FXML
    private TableColumn<Ticket,Long> flightIdTableColumn;
    @FXML
    private TableColumn<Ticket, LocalDateTime> purchaseTimeTableColumn;
    @FXML
    private Label clientNameLabel;
    @FXML

    public void setServ(Stage stage, Service service, Client client) {
        this.stage = stage;
        this.service = service;
        this.client = client;
        service.addObserver(this);
        initModel();
        initLabels();
    }

    public void initialize(){
        flightIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("flightId"));
        purchaseTimeTableColumn.setCellValueFactory(new PropertyValueFactory<>("purchaseTime"));
        ticketsTable.setItems(model);
    }

    public void initModel(){
        Iterable<Ticket> tickets = service.allTicketsByUsername(client.getUsername());
        model.clear();
        List<Ticket> tickets1 = StreamSupport.stream(tickets.spliterator(),false).collect(Collectors.toList());
        model.setAll(tickets1);
    }

    public void initLabels(){
        clientNameLabel.setText(client.getName());
    }

    public void handleTicketsFrom24Jan() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ubb/scs/map/zboruri_practic/tickets-from-24-Jan.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Add Friend");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);

        TicketsFrom24JanController ticketsController = loader.getController();
        ticketsController.setServ(stage,service);
        dialogStage.show();
    }

    public void handleFlights() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ubb/scs/map/zboruri_practic/flights-view.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("WizzAir");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);
        FlightsController ticketsController = loader.getController();
        ticketsController.setServ(stage,service,client);
        dialogStage.show();
    }

    public void update(){
        initModel();
        initLabels();
    }

}
