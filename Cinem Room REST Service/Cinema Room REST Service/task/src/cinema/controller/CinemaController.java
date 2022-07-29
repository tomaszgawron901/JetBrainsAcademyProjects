package cinema.controller;

import cinema.dto.*;
import cinema.exception.AlreadyPurchasedException;
import cinema.exception.OutOfBoundaryException;
import cinema.exception.WrongPasswordException;
import cinema.exception.WrongTokenException;
import cinema.service.CinemaService;
import cinema.service.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CinemaController {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({OutOfBoundaryException.class, AlreadyPurchasedException.class, WrongTokenException.class})
    public ApiError handleBadRequestException(Exception e) {
        return new ApiError(e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({WrongPasswordException.class})
    public ApiError handleUnauthorizedException(Exception e) {
        return new ApiError(e.getMessage());
    }

    private final CinemaService cinemaService;
    private final SecurityService securityService;

    public CinemaController(CinemaService seatsService, SecurityService securityService) {
        this.cinemaService = seatsService;
        this.securityService = securityService;
    }

    @GetMapping("/seats")
    public ResponseEntity<AvailableSeatsDTO> getSeats() {
        return ResponseEntity.ok(cinemaService.getAvailableSeats());
    }

    @PostMapping("/purchase")
    public ResponseEntity<TicketDTO> purchaseSeat(@RequestBody SeatPositionDTO seatPositionDTO) {
        if (cinemaService.isPositionOutOfRange(seatPositionDTO)) {
            throw new OutOfBoundaryException();
        }
        if (cinemaService.isSeatAtPositionReserved(seatPositionDTO)) {
            throw new AlreadyPurchasedException();
        }

        return ResponseEntity.ok(cinemaService.porchesSeat(seatPositionDTO));
    }

    @PostMapping("/return")
    public ResponseEntity<ReturnedTicketDTO> returnSeat(@RequestBody TokenDTO tokenDTO) {
        return ResponseEntity.ok(cinemaService.returnTicket(tokenDTO.getToken()));
    }

    @PostMapping("/stats")
    public ResponseEntity<StatisticsDTO> getStatistics(@RequestParam(required = false) String password) {
        if (!securityService.isValidPassword(password)) {
            throw new WrongPasswordException();
        }
        return ResponseEntity.ok(cinemaService.getStatistics());
    }
}
