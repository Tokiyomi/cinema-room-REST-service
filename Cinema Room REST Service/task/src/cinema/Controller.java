package cinema;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController // Controller annotation to let Spring Boot know this is our controller component
//@RequestMapping(path="/seats") // General path that each request must have
public class Controller {

    @Autowired
    Cinema cinema = new Cinema();

    @GetMapping(path="/seats")
    public Cinema getCinema() {
        cinema.getAvailableSeats();
        return cinema;
    }

    @PostMapping(path="/purchase")
    public ResponseEntity<Object> getSeat(@RequestBody Seat seat) {
        if ((seat.getRow() > cinema.getTotal_rows() | seat.getRow()<1 ) | (seat.getColumn()> cinema.getTotal_columns() | seat.getColumn()<1)) {
            return ResponseHandler.generateResponse("The number of a row or a column is out of bounds!",
                    HttpStatus.BAD_REQUEST);
        }
        Seat temp =  cinema.getSeat(seat.getRow(), seat.getColumn());
        if (temp.isBooked()) {
        //if (cinema.getPurchased_seats().containsValue()) {
            return ResponseHandler.generateResponse("The ticket has been already purchased!",
                    HttpStatus.BAD_REQUEST);
        } else {
            temp.setBooked(true);
            //UUID token = Token.generateToken();
            //cinema.addPurchasedSeats(token, seat);
            //return new ResponseEntity<>(temp, HttpStatus.OK);
            return ResponseHandler.generateTicket(temp,
                    HttpStatus.OK);
        }
    }
    @PostMapping(path="/return")
    public ResponseEntity<Object> returnTicket(@RequestBody Token token) {
        //System.out.println(token.getToken().getClass().getName());
        try {
            Seat seat = cinema.findToken(token.getToken());
            if (seat==null) {
                return ResponseHandler.generateResponse("Wrong token!",
                        HttpStatus.BAD_REQUEST);
            } else {
                seat.setBooked(false);
                seat.setToken(null);
                return ResponseHandler.returnTicket(seat,
                        HttpStatus.OK);
           }
        } catch (Exception e) {System.out.println(e);
            return ResponseHandler.generateResponse("Wrong token!",
                HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping(path="/stats")
    public ResponseEntity<Object> getStats(@RequestParam(value="password", required = false) String password){
        if (password!=null && password.equals("super_secret")) {
            Map<String, Integer> stats = cinema.computeStats();
            return new ResponseEntity<Object>(stats,
                    HttpStatus.OK);
        } else {
            return ResponseHandler.generateResponse("The password is wrong!",
                    HttpStatus.UNAUTHORIZED);
        }
    }
}
