package cinema.service;

import cinema.dto.*;
import cinema.exception.WrongTokenException;
import cinema.models.Seat;
import cinema.repository.SeatsRepository;
import org.springframework.stereotype.Service;

@Service
public class CinemaService {
    private final SeatsRepository seatsRepository;
    private final TokenGenerationService tokenGenerationService;

    public CinemaService(SeatsRepository seatsRepository, TokenGenerationService tokenGenerationService) {
        this.seatsRepository = seatsRepository;
        this.tokenGenerationService = tokenGenerationService;
    }

    public AvailableSeatsDTO getAvailableSeats() {
        return new AvailableSeatsDTO(
                seatsRepository.getRows(),
                seatsRepository.getColumns(),
                seatsRepository.getSeats().stream()
                        .filter(s -> !s.isReserved())
                        .map(s -> new SeatDTO(s.getRow(), s.getColumn(), s.getPrice()))
                        .toArray(SeatDTO[]::new));
    }

    public boolean isPositionOutOfRange(SeatPositionDTO seatPosition) {
        return seatPosition.getRow() < 1 || seatPosition.getRow() > seatsRepository.getRows() ||
                seatPosition.getColumn() < 1 || seatPosition.getColumn() > seatsRepository.getColumns();
    }

    public boolean isSeatAtPositionReserved(SeatPositionDTO seatPosition) {
        return seatsRepository.getSeat(seatPosition.getRow(), seatPosition.getColumn()).isReserved();
    }

    public TicketDTO porchesSeat(SeatPositionDTO seatPositionDTO) {
        Seat seat = seatsRepository.getSeat(seatPositionDTO.getRow(), seatPositionDTO.getColumn());
        if (seat.isReserved()) {
            throw new IllegalArgumentException("Seat is already reserved");
        }
        String token = tokenGenerationService.generateToken();
        seat.setReservationToken(token);
        return new TicketDTO(
                seat.getReservationToken(),
                new SeatDTO(
                        seat.getRow(),
                        seat.getColumn(),
                        seat.getPrice()
                )
        );
    }

    public ReturnedTicketDTO returnTicket(String token) {
        Seat seat = seatsRepository.getSeatByTokenOrDefault(token);
        if (seat == null) {
            throw new WrongTokenException();
        }
        seat.setReservationToken(null);
        return new ReturnedTicketDTO(
                new SeatDTO(
                        seat.getRow(),
                        seat.getColumn(),
                        seat.getPrice()
                )
        );
    }

    public StatisticsDTO getStatistics() {
        int currentIncome, numberOfAvailableSeats, numberOfReservedSeats;
        currentIncome = numberOfAvailableSeats = numberOfReservedSeats = 0;
        for (Seat seat : seatsRepository.getSeats()) {
            if (seat.isReserved()) {
                currentIncome += seat.getPrice();
                numberOfReservedSeats++;
            } else {
                numberOfAvailableSeats++;
            }
        }

        return new StatisticsDTO(
                currentIncome,
                numberOfAvailableSeats,
                numberOfReservedSeats
        );
    }
}
