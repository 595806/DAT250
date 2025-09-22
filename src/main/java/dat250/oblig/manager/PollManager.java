package dat250.oblig.manager;

import dat250.oblig.model.Poll;
import dat250.oblig.model.User;
import dat250.oblig.model.Vote;
import dat250.oblig.model.VoteOption;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class PollManager {
    private Map<Long, Poll> polls =  new LinkedHashMap<>();
    private Map<Long, User> users =  new LinkedHashMap<>();

    private AtomicLong userId = new AtomicLong(0);
    private AtomicLong pollId = new AtomicLong(0);

    public PollManager() {}

    public User createUser(String username, String email) {
        User user = new User(username, email);
        user.setId(userId.getAndIncrement());
        users.put(user.getId(), user);
        return user;
    }

    public User getUser(long id) {
        return users.get(id);
    }

    public Collection<User> getUsers() {
        return users.values();
    }

    public Collection<Poll> getPolls() {
        return polls.values();
    }

    public Poll getPoll(long pollId) {
        return polls.get(pollId);
    }

    public Poll startPoll(Long userId, String question, Instant validUntil) {
        User user = getUser(userId);
        Poll poll = user.createPoll(question);
        poll.setCreatorId(userId);
        poll.setPollId(pollId.getAndIncrement());
        polls.put(poll.getPollId(), poll);
        return poll;
    }

    public VoteOption addVoteOption(Long pollId, String caption) {
        return polls.get(pollId).addVoteOption(caption);
    }

    public Vote castVote(Long userId, Poll poll, VoteOption option) {
        User user = getUser(userId);
        Vote vote = user.voteFor(option);
        poll.addVote(vote);
        return vote;
    }

    public void deletePoll(Long pollId) {
        polls.remove(pollId);
    }
}
