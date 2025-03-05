package ubb.scs.map.zboruri_practic.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import ubb.scs.map.zboruri_practic.Domain.Client;
import ubb.scs.map.zboruri_practic.Domain.Flight;
import ubb.scs.map.zboruri_practic.Service.Service;
import ubb.scs.map.zboruri_practic.Utils.Observable;
import ubb.scs.map.zboruri_practic.Utils.Observer;

import javax.swing.event.ChangeListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class FlightsController implements Observer {
    private Stage stage;
    private Service service;
    private Client client;
    ObservableList<Flight> model = FXCollections.observableArrayList();

    @FXML
    private TableView<Flight> flightsTable;
    @FXML
    private TableColumn<Flight,String> fromColumn;
    @FXML
    private TableColumn<Flight,String> toColumn;
    @FXML
    private TableColumn<Flight,LocalDateTime> departureTimeColumn;
    @FXML
    private TableColumn<Flight,LocalDateTime> landingTimeColumn;
    @FXML
    private TableColumn<Flight,Integer> seatsColumn;
    @FXML
    private ComboBox<String> fromCombo;
    @FXML
    private ComboBox<String> toCombo;
    @FXML
    private DatePicker dateCombo;

    public void setServ(Stage stage,Service service,Client client) {
        this.stage = stage;
        this.service = service;
        this.client = client;
        service.addObserver(this);
        initModel();
    }

    public void initialize(){
        fromColumn.setCellValueFactory(new PropertyValueFactory<Flight,String>("from"));
        toColumn.setCellValueFactory(new PropertyValueFactory<Flight,String>("to"));
        departureTimeColumn.setCellValueFactory(new PropertyValueFactory<Flight, LocalDateTime>("departureTime"));
        landingTimeColumn.setCellValueFactory(new PropertyValueFactory<Flight, LocalDateTime>("landingTime"));
        seatsColumn.setCellValueFactory(new PropertyValueFactory<Flight,Integer>("seats"));
        flightsTable.setItems(model);

        dateCombo.setConverter(new StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                return date != null ? date.toString() : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return (string != null && !string.isEmpty()) ? LocalDate.parse(string) : null;
            }
        });

        fromCombo.setOnAction(event -> filterFlights());
        toCombo.setOnAction(event -> filterFlights());
        dateCombo.setOnAction(event -> filterFlights());

    }

    public void initModel(){
        List<Flight> flights = service.findAllFlights();
        model.setAll(flights);

        List<String> fromLocations = flights.stream()
                .map(Flight::getFrom)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        List<String> toLocations = flights.stream()
                .map(Flight::getTo)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        fromCombo.setItems(FXCollections.observableArrayList(fromLocations));
        toCombo.setItems(FXCollections.observableArrayList(toLocations));

    }

    private void filterFlights() {
        String selectedFrom = fromCombo.getValue();
        String selectedTo = toCombo.getValue();
        LocalDate selectedDate = dateCombo.getValue();

        List<Flight> filteredFlights = service.findAllFlights().stream()
                .filter(flight -> (selectedFrom == null || flight.getFrom().equals(selectedFrom)))
                .filter(flight -> (selectedTo == null || flight.getTo().equals(selectedTo)))
                .filter(flight -> (selectedDate == null || flight.getDepartureTime().toLocalDate().equals(selectedDate)))
                .collect(Collectors.toList());

        model.setAll(filteredFlights);
    }

    public void handleBuyTicket(){
        Flight selectedFlight = flightsTable.getSelectionModel().getSelectedItem();
        if(selectedFlight == null){
            MessageAlert.showMessage(null, Alert.AlertType.ERROR,"Error","You didn't select a flight");
        }
        else{
            try{
                service.buyTicket(client.getUsername(),selectedFlight.getId(),LocalDateTime.now());
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION,"Succes","You just bought a new ticket");
            }
            catch(Exception e){
                MessageAlert.showMessage(null, Alert.AlertType.ERROR,"Error",e.getMessage());
            }

        }
    }
    public void update(){
        initModel();
    }

}
