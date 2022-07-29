package cinema.dto;

public class TicketDTO {
    private String token;
    private SeatDTO ticket;

    public TicketDTO() {
    }

    public TicketDTO(String token, SeatDTO ticket) {
        this.token = token;
        this.ticket = ticket;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public SeatDTO getTicket() {
        return ticket;
    }

    public void setTicket(SeatDTO ticket) {
        this.ticket = ticket;
    }
}
