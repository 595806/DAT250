package dat250.oblig.controller;

import dat250.oblig.manager.PollManager;
import dat250.oblig.model.Poll;
import dat250.oblig.model.Vote;
import dat250.oblig.model.VoteOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@CrossOrigin
@RestController
@RequestMapping("/api/vote")
public class VoteController {

    @Autowired
    private PollManager pollManager;

    @GetMapping
    public ResponseEntity<Collection<Vote>> getVote(@RequestParam Long userId,
                                                    @RequestParam Long pollId) {

        try {
            Poll p =  pollManager.getPoll(pollId);
            return ResponseEntity.ok(Collections.singleton(p.getVote(userId)));
        } catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Poll> castVote(@RequestParam Long userId,
                                         @RequestParam Long pollId,
                                         @RequestParam Integer voteOption) {
        try {
            Poll poll = pollManager.getPoll(pollId);
            VoteOption option = poll.getVoteOptions().get(voteOption);
            Vote vote = pollManager.castVote(userId, poll, option);
            return ResponseEntity.ok(poll);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
