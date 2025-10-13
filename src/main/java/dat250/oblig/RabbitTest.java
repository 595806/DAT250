package dat250.oblig;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitTest {
    public static class Send {
        private final static String QUEUE_NAME = "hello";
        public static void main(String[] argv) throws Exception {
        }
    }


    public static void main(String[] argv)  {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare("test_queue", true, false, false, null);
            String msg = "Hello world!";
            channel.basicPublish("", "test_queue", null, msg.getBytes());
            System.out.println("Published: " + msg);
        }
        catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

}
