package rabbitmq.amqp.tutorials.tut4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

public class Tut4Sender {

    private static Logger logger = LoggerFactory.getLogger(Tut4Sender.class);

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange direct;

    AtomicInteger index = new AtomicInteger(0);

    AtomicInteger count = new AtomicInteger(0);

    private final String[] keys = {"orange", "black", "green"};

    /**
     * send() 메소드는 스케줄링된 작업으로서, 매 초마다 "orange", "black", "green" 중
     * 하나의 키를 순서대로 선택하여 메시지를 전송. 메시지는 "Hello to [color] [count]" 형식.
     * 전송된 메시지는 DirectExchange를 통해 적절한 바인딩 키를 가진 큐로 라우팅.
     */
    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        StringBuilder builder = new StringBuilder("Hello to ");
        if (this.index.incrementAndGet() == 3) {
            this.index.set(0);
        }
        String key = keys[this.index.get()];
        builder.append(key).append(' ');
        builder.append(this.count.get());
        String message = builder.toString();
        template.convertAndSend(direct.getName(), key, message);

        logger.info("===== [X] Send '{}'", message);

    }

}
