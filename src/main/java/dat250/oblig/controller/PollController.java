package dat250.oblig.controller;

import dat250.oblig.manager.PollManager;
import dat250.oblig.model.Poll;
import dat250.oblig.model.VoteOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Collection;

@RestController
@RequestMapping("/api/polls")
public class PollController {
    @Autowired
    private PollManager pollManager;

    @GetMapping
    public ResponseEntity<Collection<Poll>> getPolls()
    {
        return ResponseEntity.ok(pollManager.getPolls());
    }

    @PostMapping
    public ResponseEntity<Poll> createPoll(@RequestParam Long userid,
                                           @RequestParam String question,
                                           @RequestParam Instant validUntil)
    {
        try {
            Poll newPoll = pollManager.startPoll(userid, question, validUntil);
            return ResponseEntity.ok(newPoll);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{pollId}/options")
    public ResponseEntity<VoteOption> addVoteOption(@PathVariable("pollId") Long pollId, @RequestParam String caption) {
        try {
            VoteOption voteOption = pollManager.addVoteOption(Long.valueOf(pollId), caption);
            return ResponseEntity.ok(voteOption);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{pollId}/delete")
    public ResponseEntity<VoteOption> addVoteOption(@PathVariable("pollId") Long pollId) {
        try {
            pollManager.deletePoll(pollId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
