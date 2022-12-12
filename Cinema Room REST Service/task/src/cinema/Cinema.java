package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Cinema {

    private int total_rows;
    private int total_columns;
    private List<Seat> available_seats;

    @JsonIgnore
    private List<Seat> total_seats;

    // Generate array of seats of 9 x 9
    public void generateSeats() {
        for (int row = 0; row < this.total_rows; row++) {
            for (int col = 0; col < this.total_columns; col++ ) {
                this.total_seats.add(new Seat(row+1, col+1));
            }
        }
    }

    public void getAvailableSeats() {
        this.available_seats = this.total_seats.stream().filter(p-> !p.isBooked()).collect(Collectors.toList());
    }

    public int computeIncome() {
        List<Seat> purchased_seats = this.total_seats.stream().filter(p-> p.isBooked()).collect(Collectors.toList());
        int total_income = 0;
        for (int i=0; i < purchased_seats.size(); i++) {
            total_income += purchased_seats.get(i).getPrice();
        }
        return total_income;
    }

    public Seat getSeat(int row, int col) {
        Seat seat = this.total_seats.stream()
                .filter(p->p.getRow()==row && p.getColumn()==col)
                .findFirst().orElse(null);
        return seat;
    }

    public Seat findToken(UUID token) {
        Seat seat = this.total_seats.stream()
                .filter(p-> p.getToken() != null && p.getToken().equals(token))
                .findFirst().orElse(null);
        return seat;
    }


    public Cinema(int total_rows, int total_columns, List<Seat> total_seats) {
        this.total_rows = total_rows;
        this.total_columns = total_columns;
        this.total_seats = total_seats;
    }

    public Cinema() {
        this.total_rows = 9;
        this.total_columns = 9;
        this.total_seats = new ArrayList<>();
        this.generateSeats();
    }

    public int getTotal_rows() {
        return total_rows;
    }

    public void setTotal_rows(int total_rows) {
        this.total_rows = total_rows;
    }

    public int getTotal_columns() {
        return total_columns;
    }

    public void setTotal_columns(int total_columns) {
        this.total_columns = total_columns;
    }

    public List<Seat> getAvailable_seats() {
        return available_seats;
    }

    public void setAvailable_seats(List<Seat> available_seats) {
        this.available_seats = available_seats;
    }

    public List<Seat> getTotal_seats() {
        return total_seats;
    }

    public void setTotal_seats(List<Seat> total_seats) {
        this.total_seats = total_seats;
    }

    public Map<String, Integer> computeStats() {
        Map<String, Integer> stats_map = new LinkedHashMap<>();
        this.getAvailableSeats();
        int available_seats = this.available_seats.size();
        int purchased_tickets = this.total_columns*this.total_rows-available_seats;
        int total_income = this.computeIncome();
        stats_map.put("current_income",total_income);
        stats_map.put("number_of_available_seats", available_seats);
        stats_map.put("number_of_purchased_tickets", purchased_tickets);
        return stats_map;
    }


}
