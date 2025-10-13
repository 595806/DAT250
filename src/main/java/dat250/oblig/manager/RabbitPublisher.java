package dat250.oblig.manager;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitPublisher {
    private final ConnectionFactory factory;


    public RabbitPublisher() {
        this.factory = new ConnectionFactory();
        this.factory.setHost("localhost");
        this.factory.setPort(5672);
        this.factory.setUsername("guest");
        this.factory.setPassword("guest");
    }

    public void createTopic(String pollName) {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(pollName, "topic", true);
            channel.exchangeDeclare("poll.all", "topic", true);
            channel.exchangeBind("poll.all", pollName, "vote");
            System.out.println("Created topic: " + pollName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void publishVote(String pollName, Long pollId, Long userId, Long optionId) {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            String payload = """
                {"type": "API", "pollId":%d,"optionId":%d,"userId":%s}
            """.formatted(
                    pollId,
                    optionId,
                    userId
            );
            channel.basicPublish(pollName, "vote", null, payload.getBytes());
            System.out.println("Published: " + payload + " to " + pollName);


        } catch (Exception ignored) {
        }

    }
}