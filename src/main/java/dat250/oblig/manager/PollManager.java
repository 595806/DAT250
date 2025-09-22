package dat250.oblig.manager;

import dat250.oblig.model.Poll;
import dat250.oblig.model.User;
import dat250.oblig.model.Vote;
import dat250.oblig.model.VoteOption;
import org.springframework.stereotype.Component;

import java.sql.Array;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class PollManager {
    private List<Poll> polls =  new ArrayList<>();
    private Map<Long, User> users =  new LinkedHashMap<>();

    private AtomicLong userId = new AtomicLong(0);

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
        return polls;
    }

    public Poll getPoll(Integer index) {
        return polls.get(index);
    }

    public Poll startPoll(Long userId, String question, Instant validUntil) {
        User user = getUser(userId);
        Poll poll = user.createPoll(question);
        poll.setCreator(user);
        polls.add(poll);
        return poll;
    }

    public VoteOption addVoteOption(Integer index, String caption) {
        return polls.get(index).addVoteOption(caption);
    }

    public Vote castVote(Long userId, Poll poll, VoteOption option) {
        User user = getUser(userId);
        Vote vote = user.voteFor(option);
        return vote;
    }

    public void deletePoll(int index) {
        polls.remove(index);
    }
}
