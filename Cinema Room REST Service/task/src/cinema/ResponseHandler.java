package cinema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class ResponseHandler {

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("error", message);
        return new ResponseEntity<Object>(map,status);
    }

    public static ResponseEntity<Object> generateTicket(Seat seat, HttpStatus status) {
        UUID token = Token.generateToken();
        seat.setToken(token);
        //System.out.println("Token actualizado:" + seat.getToken());
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("token", token);
        map.put("ticket", seat);
        return new ResponseEntity<Object>(map,status);
    }

    public static ResponseEntity<Object> returnTicket(Seat seat, HttpStatus status) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("returned_ticket", seat);
        return new ResponseEntity<Object>(map,status);
    }
}