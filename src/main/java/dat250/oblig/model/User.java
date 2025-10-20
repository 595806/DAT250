package dat250.oblig.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String email;
    private Long id;

    private List<Vote> votes = new ArrayList<>();

    public User() {}

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
