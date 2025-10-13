package dat250.oblig.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Poll> created = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Vote> votes = new ArrayList<>();

    public List<Vote> getVotes() {
        return votes;
    }

    public User() {}

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<Poll> getCreated() {
        return created;
    }

    public void setCreated(Set<Poll> created) {
        this.created = created;
    }

    public Poll createPoll(String question) {
        Poll p = new Poll(question, Instant.now(), Instant.now());
        p.setCreator(this);
        this.created.add(p);
        return p;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public Vote voteFor(VoteOption option) {
        Vote v = new Vote(Instant.now(), this, option);
        v.setPoll(option.getPoll());
        votes.add(v);
        //option.getPoll().addVote(v);
        option.addVote(v);
        return v;
    }
}
