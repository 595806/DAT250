package dat250.oblig.model;

import java.time.Instant;
import java.util.*;

public class User {
    private String username;
    private String email;
    private Long id;

    private Set<Poll> created;

    public User() {}

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.created = new LinkedHashSet<>();
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

    public Set<Poll> getCreated() {
        return created;
    }

    public void setCreated(Set<Poll> created) {
        this.created = created;
    }

    public Poll createPoll(String question) {
        return new Poll(question, Instant.now(), Instant.now());
    }

    public Vote voteFor(VoteOption option) {
        return new Vote(Instant.now(), id, option);
    }
}
