package ubb.scs.map.zboruri_practic.Domain;

public class Client extends Entity<Long>{
    private String username;
    private String name;
    public Client(Long id, String username, String name) {
        super(id);
        this.username = username;
        this.name = name;
    }
    public String getUsername() {
        return username;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Client [username=" + username + ", name=" + name + "]";
    }
}
