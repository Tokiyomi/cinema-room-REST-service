package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Seat {

    private int row;
    private int column;

    private int price;
    @JsonIgnore
    private boolean booked;

    @JsonIgnore
    private UUID token;

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        setPrice();
        setBooked(false);
    }

    public Seat() {
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice() {
        if (this.row <= 4) {
            this.price = 10;
        } else {
            this.price = 8;
        }
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
