package ubb.scs.map.zboruri_practic.Repository;

import ubb.scs.map.zboruri_practic.Domain.Flight;
import ubb.scs.map.zboruri_practic.Domain.Ticket;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class TicketRepository implements Repository<Long,Ticket> {
    private String username;
    private String password;
    private String url;

    public TicketRepository(String username, String password, String url) {
        this.username = username;
        this.password = password;
        this.url = url;
    }

    public void add(String username_client, Long flight_id, LocalDateTime date) {
        int rez = -1;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("insert into tickets (username,flight_id,purchase_time) values(?,?,?)");) {
            statement.setString(1, username_client);
            statement.setLong(2, flight_id);
            statement.setTimestamp(3, Timestamp.valueOf(date));
            rez = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Optional<Ticket> find(Long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from tickets where id = ?");) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery();) {
                if (resultSet.next()) {
                    String username_client = resultSet.getString("username");
                    Long flightId = resultSet.getLong("flight_id");
                    LocalDateTime purchaseTime = resultSet.getTimestamp("purchase_time").toLocalDateTime();
                    return Optional.of(new Ticket(id, username_client, flightId, purchaseTime));
                }
            }
        } catch (SQLException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    public Iterable<Ticket> findTicketsByUsername(String username_client) {
        Map<Long,Ticket> tickets = new HashMap<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from tickets where username = ?");) {
            statement.setString(1, username_client);
            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    Long ticketId = resultSet.getLong("id");
                    Long flightId = resultSet.getLong("flight_id");
                    LocalDateTime purchaseTime = resultSet.getTimestamp("purchase_time").toLocalDateTime();
                    tickets.put(ticketId,new Ticket(ticketId,username_client, flightId, purchaseTime));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tickets.values();
    }

    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from tickets");) {
            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    Long ticketId = resultSet.getLong("id");
                    String username_client = resultSet.getString("username");
                    Long flightId = resultSet.getLong("flight_id");
                    LocalDateTime purchaseTime = resultSet.getTimestamp("purchase_time").toLocalDateTime();
                    tickets.add(new Ticket(ticketId,username_client, flightId, purchaseTime));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tickets;
    }

}
