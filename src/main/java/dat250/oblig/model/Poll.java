package dat250.oblig.model;

import java.time.Instant;
import java.util.*;

public class Poll {
    private String question;
    private Instant publishedAt;
    private Instant validUntil;
    private Long pollId;
    private Long creatorId;

    private final List<VoteOption> voteOptions = new ArrayList<>();

    private final HashMap<Long,Vote> votes = new HashMap<>();

    public Poll() {}

    public Poll(String question, Instant publishedAt, Instant validUntil) {
        this.question = question;
        this.publishedAt = publishedAt;
        this.validUntil = validUntil;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt() {
        this.publishedAt = publishedAt;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorID) {
        this.creatorId = creatorID;
    }

    public Long getPollId() {
        return pollId;
    }

    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }

    public List<VoteOption> getVoteOptions() {
        return voteOptions;
    }

    public VoteOption addVoteOption(String caption) {
        VoteOption voteOption = new VoteOption(caption);
        voteOption.setPresentationOrder(voteOptions.size());
        this.voteOptions.add(voteOption);
        return voteOption;
    }

    public HashMap<Long,Vote> getVotes() {
        return votes;
    }

    public Vote getVote(long userId) {
        return votes.get(userId);
    }

    public void addVote(Vote vote) {
        votes.put(vote.getUserId(), vote);
    }
}
