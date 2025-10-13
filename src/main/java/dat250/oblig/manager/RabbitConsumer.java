package dat250.oblig.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import dat250.oblig.model.Poll;
import dat250.oblig.model.VoteOption;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class RabbitConsumer {
    private Connection connection;
    private Channel channel;
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private PollManager pollManager;


    @PostConstruct
    public void startConsumer() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            factory.setUsername("guest");
            factory.setPassword("guest");

            connection = factory.newConnection("consumer");
            channel = connection.createChannel();
            channel.exchangeDeclare("poll.all", BuiltinExchangeType.TOPIC, true);
            channel.queueDeclare("pollqueue", true, false, false, null);
            channel.queueBind("pollqueue", "poll.all", "#");

            DeliverCallback callback = (tag, delivery) -> {
                String json = new String(delivery.getBody(), StandardCharsets.UTF_8);
                try {
                    VoteEvent evt = mapper.readValue(json, VoteEvent.class);
                    if(!evt.type.equals("API")) {
                        Poll poll = pollManager.getPoll(evt.pollId);
                        VoteOption option = poll.getVoteOptions().get(evt.optionId);
                        pollManager.castVote(evt.userId, poll, option);
                        System.out.println("Consumer: Added vote from MessageBroker - " + evt);
                    } else System.out.println("Consumer: vote received from API - " + evt);
                } catch (Exception ex) {
                    System.err.println("Failed to parse vote event: " + json);
                    ex.printStackTrace();
                }
            };

            channel.basicConsume("pollqueue", true, callback, tag -> {});
            System.out.println("Consumer started");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void shutdown() {
        System.out.println("Consumer stopped");
    }

    public static record VoteEvent(String type, int pollId, int optionId, Long userId) {}
}
