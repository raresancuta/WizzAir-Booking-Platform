package ubb.scs.map.zboruri_practic.Repository;

import ubb.scs.map.zboruri_practic.Domain.Client;
import ubb.scs.map.zboruri_practic.Domain.Flight;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightRepository implements Repository<Long, Flight> {
    private String username;
    private String password;
    private String url;

    public FlightRepository(String username, String password, String url) {
        this.username = username;
        this.password = password;
        this.url = url;
    }

    public void bookSeatAtFlight(Long id) throws RepositoryException{
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from flights where id = ?");) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery();) {
                if (resultSet.next()) {
                    int seats = resultSet.getInt("seats");
                    if(seats > 0){
                        PreparedStatement statement1 = connection.prepareStatement("update flights set seats = seats - 1 where id = ?");
                        statement1.setLong(1, id);
                        statement1.executeUpdate();
                    }
                    else throw new RepositoryException("This flight is fully booked");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public Optional<Flight> find(Long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from flights where id = ?");) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery();) {
                if (resultSet.next()) {
                    String from = resultSet.getString("from");
                    String to = resultSet.getString("to");
                    LocalDateTime departureTime = resultSet.getTimestamp("departure_time").toLocalDateTime();
                    LocalDateTime landingTime = resultSet.getTimestamp("landing_time").toLocalDateTime();
                    int seats = resultSet.getInt("seats");
                    return Optional.of(new Flight(id, from, to, departureTime, landingTime, seats));
                }
            }
        } catch (SQLException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    public List<Flight> findAll() {
        List<Flight> flights = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("select * from flights");) {
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String from = resultSet.getString("from");
                    String to = resultSet.getString("to");
                    LocalDateTime departureTime = resultSet.getTimestamp("departure_time").toLocalDateTime();
                    LocalDateTime landingTime = resultSet.getTimestamp("landing_time").toLocalDateTime();
                    int seats = resultSet.getInt("seats");
                    flights.add(new Flight(id, from, to, departureTime, landingTime, seats));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }
}
