package ubb.scs.map.zboruri_practic.Repository;

import ubb.scs.map.zboruri_practic.Domain.Client;

import java.sql.*;
import java.util.Map;
import java.util.Optional;

public class ClientRepository implements Repository<Long, Client> {
    private String username;
    private String password;
    private String url;

    public ClientRepository(String username, String password, String url) {
        this.username = username;
        this.password = password;
        this.url = url;
    }

    public Optional<Client> find(Long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from clients where id = ?");) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery();) {
                if (resultSet.next()) {
                    String username_client = resultSet.getString("username");
                    String name = resultSet.getString("name");
                    Client client = new Client(id, username_client, name);
                    return Optional.of(client);
                }
            }
        } catch (SQLException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    public Optional<Client> findByUsername(String username_client) {
        try(Connection connection = DriverManager.getConnection(url,username,password);
        PreparedStatement preparedStatement = connection.prepareStatement("select * from clients where username=?");){
            preparedStatement.setString(1,username_client);
            try(ResultSet resultSet = preparedStatement.executeQuery();){
                if(resultSet.next()){
                    Long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    Client client = new Client(id, username_client, name);
                    return Optional.of(client);
                }
            }
        }
        catch (SQLException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

}
