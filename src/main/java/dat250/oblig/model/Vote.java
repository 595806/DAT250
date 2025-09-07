package dat250.oblig.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;

import java.time.Instant;

public class Vote {
    private Instant publishedAt;

    @JsonIdentityReference(alwaysAsId = true)
    private VoteOption voteOption;

    private Long userId;

    public Vote() {}

    public Vote(Instant publishedAt, Long userId, VoteOption option) {
        this.publishedAt = publishedAt;
        this.userId = userId;
        this.voteOption = option;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setUser(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public VoteOption getVoteOption() {
        return voteOption;
    }

    public void setVoteOption(VoteOption voteOption) {
        this.voteOption = voteOption;
    }
}