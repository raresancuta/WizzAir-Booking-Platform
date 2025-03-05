package ubb.scs.map.zboruri_practic.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ubb.scs.map.zboruri_practic.Domain.Client;
import ubb.scs.map.zboruri_practic.Domain.Ticket;
import ubb.scs.map.zboruri_practic.Service.Service;
import ubb.scs.map.zboruri_practic.Utils.Observer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TicketsFrom24JanController implements Observer {
    private Stage stage;
    private Service service;
    ObservableList<Ticket> model = FXCollections.observableArrayList();

    @FXML
    private TableView<Ticket> ticketsFrom24JanTable;
    @FXML
    private TableColumn<Ticket,String> usernameTableColumn;
    @FXML
    private TableColumn<Ticket,Long> flightIdTableColumn;
    @FXML
    private TableColumn<Ticket, LocalDateTime> purchaseTimeTableColumn;
    @FXML

    public void setServ(Stage stage, Service service) {
        this.stage = stage;
        this.service = service;
        service.addObserver(this);
        initModel();
    }

    public void initialize(){
        usernameTableColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        flightIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("flightId"));
        purchaseTimeTableColumn.setCellValueFactory(new PropertyValueFactory<>("purchaseTime"));
        ticketsFrom24JanTable.setItems(model);
    }

    public void initModel(){
        model.clear();
        List<Ticket> tickets1 = service.findAllFromDate(LocalDate.of(2024,1,24));
        model.setAll(tickets1);
    }

    public void update(){
        initModel();
    }
}
