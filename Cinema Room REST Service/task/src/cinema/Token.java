package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class Token {

    private UUID token;

    public Token(UUID token) {
        this.token = token;
    }

    public Token() {
        //this.token = getToken();
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public static UUID generateToken() {
        return UUID.randomUUID();
    }
}
