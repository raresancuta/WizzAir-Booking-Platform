package ubb.scs.map.zboruri_practic.Domain;

import java.time.LocalDateTime;

public class Flight extends Entity<Long>{
    private String from;
    private String to;
    private LocalDateTime departureTime;
    private LocalDateTime landingTime;
    private int seats;
    public Flight(Long id, String from, String to, LocalDateTime departureTime, LocalDateTime landingTime, int seats) {
        super(id);
        this.from = from;
        this.to = to;
        this.departureTime = departureTime;
        this.landingTime = landingTime;
        this.seats = seats;
    }
    public String getFrom() {
        return from;
    }
    public String getTo(){
        return to;
    }
    public LocalDateTime getDepartureTime(){
        return departureTime;
    }
    public LocalDateTime getLandingTime(){
        return landingTime;
    }
    public int getSeats(){
        return seats;
    }

}
