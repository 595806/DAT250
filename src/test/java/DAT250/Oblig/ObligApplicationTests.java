package dat250.oblig;

import dat250.oblig.model.Poll;
import dat250.oblig.model.User;
import dat250.oblig.model.Vote;
import dat250.oblig.model.VoteOption;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ObligApplicationTests {

    @LocalServerPort
    int port;

    RestClient client;

    @BeforeEach
    void setUp() {
        client = RestClient.create("http://localhost:"+port+"/api");
    }

    @Order(1)
    @Test
    void emptyUsersList() {
        var resp = client.get()
                .uri("/users")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(User[].class);

        assertEquals(200, resp.getStatusCode().value());
        assertNotNull(resp.getBody());
        assertEquals(0, resp.getBody().length);

        System.out.println("Passed emptyUsersList");
    }

    @Test
    @Order(2)
    void testCreateUser() {
        var resp = client.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body("""
                    {"username":"Thomas","email":"thomas@dat255.hvl.no"}
                """)
                .retrieve()
                .toEntity(User.class);

        assertEquals(200, resp.getStatusCode().value());
        assertNotNull(resp.getBody());
        assertEquals("Thomas", resp.getBody().getUsername());

        System.out.println("Passed testCreateUser");

    }

    @Order(3)
    @Test
    void testCreateUser2() {
        var resp = client.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body("""
                    {"username":"Teacher","email":"teacher@dat255.hvl.no"}
                """)
                .retrieve()
                .toEntity(User.class);

        assertEquals(200, resp.getStatusCode().value());
        assertNotNull(resp.getBody());
        assertEquals("Teacher", resp.getBody().getUsername());

        System.out.println("Passed testCreateUser2");

    }

    @Order(4)
    @Test
    void usersList() {
        var resp = client.get()
                .uri("/users")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(User[].class);

        assertEquals(200, resp.getStatusCode().value());
        assertNotNull(resp.getBody());
        assertEquals(2, resp.getBody().length);
        assertEquals("Thomas", resp.getBody()[0].getUsername());
        assertEquals("Teacher", resp.getBody()[1].getUsername());

        System.out.println("Passed usersList");
    }

    @Order(5)
    @Test
    void createPoll() {
        var resp = client.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/polls")
                        .queryParam("userid", 0)
                        .queryParam("question", "What class is this")
                        .queryParam("validUntil", "2025-09-12T20:30:00Z")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Poll.class);

        assertEquals(200, resp.getStatusCode().value());
        assertEquals(resp.getBody().getQuestion(), "What class is this");

        System.out.println("Passed createPoll");
    }

    @Order(6)
    @Test
    void pollsList() {
        var resp = client.get()
                .uri("/polls")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Poll[].class);

        assertEquals(200, resp.getStatusCode().value());
        assertNotNull(resp.getBody());
        assertEquals(1, resp.getBody().length);
        assertEquals("What class is this", resp.getBody()[0].getQuestion());

        System.out.println("Passed pollsList");
    }

    @Order(7)
    @Test
    void addVoteOption() {
        var resp = client.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/polls/{id}/options")
                        .queryParam("caption", "DAT250")
                        .build(0))                        // {id} = 0
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(VoteOption.class);

        assertEquals(200, resp.getStatusCode().value());
        assertEquals(resp.getBody().getCaption(), "DAT250");

        System.out.println("Passed addVoteOption");
    }

    @Order(8)
    @Test
    void addVoteOption2() {
        var resp = client.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/polls/{id}/options")
                        .queryParam("caption", "INF234")
                        .build(0))                        // {id} = 0
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(VoteOption.class);

        assertEquals(200, resp.getStatusCode().value());
        assertEquals(resp.getBody().getCaption(), "INF234");

        System.out.println("Passed addVoteOption2");
    }

    @Order(9)
    @Test
    void voteOnPoll() {
        var resp = client.post()
                .uri(b -> b.path("/vote")
                        .queryParam("userId", 1)
                        .queryParam("pollId", 0)
                        .queryParam("voteOption", 1)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                // Change Vote.class to whatever your endpoint returns (e.g., Poll.class or String.class)
                .toEntity(Poll.class);

        assertEquals(200, resp.getStatusCode().value());
        assertEquals(resp.getBody().getPollId(), 0);
        assertEquals(resp.getBody().getVoteOptions().getLast().getCaption(), "INF234");

        System.out.println("Passed voteOnPoll");
    }

    @Order(10)
    @Test
    void changeVote() {
        var resp = client.post()
                .uri(b -> b.path("/vote")
                        .queryParam("userId", 1)
                        .queryParam("pollId", 0)
                        .queryParam("voteOption", 0)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                // Change Vote.class to whatever your endpoint returns (e.g., Poll.class or String.class)
                .toEntity(Poll.class);

        assertEquals(200, resp.getStatusCode().value());
        assertEquals(resp.getBody().getPollId(), 0);
        assertEquals(resp.getBody().getVoteOptions().getLast().getCaption(), "INF234");

        System.out.println("Passed changeVote");
    }

    @Order(11)
    @Test
    void listVotes() {
        var resp = client.get()
                .uri(b -> b.path("/vote")
                        .queryParam("userId", 1)
                        .queryParam("pollId", 0)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Vote[].class);

        assertEquals(200, resp.getStatusCode().value());
        assertEquals(resp.getBody()[0].getUserId(), 1);
        assertEquals(resp.getBody().length, 1);

        System.out.println("Passed listVotes");
    }

    @Order(12)
    @Test
    void deletePoll() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        var resp = client.post()
                .uri("/polls/{id}/delete", 0)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(form)
                .retrieve()
                .toBodilessEntity();

        assertTrue(resp.getStatusCode().is2xxSuccessful());

        System.out.println("Passed deletePoll");
    }

    @Order(13)
    @Test
    void listEmptyPolls() {
        var resp = client.get()
                .uri("/polls")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Poll[].class);

        assertEquals(200, resp.getStatusCode().value());
        assertNotNull(resp.getBody());
        assertEquals(0, resp.getBody().length);

        System.out.println("Passed listEmptyPolls");
    }

    @Order(14)
    @Test
    void listEmptyVotes() {
        try {
            var resp = client.get()
                    .uri(b -> b.path("/vote")
                            .queryParam("userId", 1)
                            .queryParam("pollId", 0)
                            .build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toBodilessEntity();
            assertEquals(404, resp.getStatusCode().value());
        } catch(HttpClientErrorException.NotFound e) {
            assertEquals(404, e.getStatusCode().value());
        }

        System.out.println("Passed listEmptyVotes");
    }
}
