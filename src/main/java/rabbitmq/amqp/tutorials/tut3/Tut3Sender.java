package rabbitmq.amqp.tutorials.tut3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import rabbitmq.amqp.tutorials.tut2.Tut2Receiver;

import java.util.concurrent.atomic.AtomicInteger;

public class Tut3Sender {

    private static Logger logger = LoggerFactory.getLogger(Tut3Sender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private FanoutExchange fanout;

    AtomicInteger dots = new AtomicInteger();

    AtomicInteger count = new AtomicInteger();

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        StringBuilder builder = new StringBuilder("Hello, Fanout");

        if (dots.incrementAndGet() == 3) {
            dots.set(1);
        }
        for (int i=0; i<dots.get(); i++) {
            builder.append('.');
        }

        builder.append(count.incrementAndGet());
        String message = builder.toString();
        rabbitTemplate.convertAndSend(fanout.getName(), "", message);
        logger.info("===== [X] Sent '{}'", message);

    }

}
