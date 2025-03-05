package ubb.scs.map.zboruri_practic.Domain;

import java.time.LocalDateTime;

public class Ticket extends Entity<Long>{
    private String username;
    private Long flightId;
    private LocalDateTime purchaseTime;
    public Ticket(Long id, String username, Long flightId, LocalDateTime purchaseTime) {
        super(id);
        this.username = username;
        this.flightId = flightId;
        this.purchaseTime = purchaseTime;
    }
    public String getUsername() {
        return username;
    }
    public Long getFlightId() {
        return flightId;
    }
    public LocalDateTime getPurchaseTime() {
        return purchaseTime;
    }
    @Override
    public String toString() {
        return "Ticket [username=" + username + ", flightId=" + flightId + ", purchaseTime="+purchaseTime+"]";
    }
}
