package ubb.scs.map.zboruri_practic.Service;

import ubb.scs.map.zboruri_practic.Domain.Client;
import ubb.scs.map.zboruri_practic.Domain.Flight;
import ubb.scs.map.zboruri_practic.Domain.Ticket;
import ubb.scs.map.zboruri_practic.Repository.ClientRepository;
import ubb.scs.map.zboruri_practic.Repository.FlightRepository;
import ubb.scs.map.zboruri_practic.Repository.RepositoryException;
import ubb.scs.map.zboruri_practic.Repository.TicketRepository;
import ubb.scs.map.zboruri_practic.Utils.Observable;
import ubb.scs.map.zboruri_practic.Utils.Observer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Service implements Observable {
    private ClientRepository clientRepository;
    private FlightRepository flightRepository;
    private TicketRepository ticketRepository;

    List<Observer> observers = new ArrayList<>();

    public Service(ClientRepository clientRepository, FlightRepository flightRepository, TicketRepository ticketRepository) {
        this.clientRepository = clientRepository;
        this.flightRepository = flightRepository;
        this.ticketRepository = ticketRepository;
    }

    public Optional<Client> verifyLoginCredentials(String username){
        return clientRepository.findByUsername(username);
    }

    public Iterable<Ticket> allTicketsByUsername(String username){
        return ticketRepository.findTicketsByUsername(username);
    }

    public List<Ticket> findAllFromDate(LocalDate date){
        List<Ticket> allTickets = ticketRepository.findAll();
        return allTickets.stream().filter(ticket -> ticket.getPurchaseTime().toLocalDate().equals(date)).collect(Collectors.toList());
    }

    public List<Flight> findAllFlights(){
        return flightRepository.findAll();
    }

    public void buyTicket(String username,Long flight_id,LocalDateTime purchaseTime) throws RepositoryException {
        flightRepository.bookSeatAtFlight(flight_id);
        ticketRepository.add(username,flight_id,purchaseTime);
        notifyObservers();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        observers.forEach(observer -> observer.update());
    }

}
