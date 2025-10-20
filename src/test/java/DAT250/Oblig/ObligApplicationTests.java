package dat250.oblig;

import dat250.oblig.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ObligApplicationTests {
    User user1;
    User user2;

	@Test
	void contextLoads() {

	}

    @Test
    void testCreateUser() {
        user1 = new User("Thomas", "thomas@dat250.hvl.no");
    }

    @Test
    void testCreateUser2() {
        user2 = new User("Teacher", "teacher@250.hvl.no");
    }

    @Test
    void listUsers() {
        assertTrue(true);
    }
}
