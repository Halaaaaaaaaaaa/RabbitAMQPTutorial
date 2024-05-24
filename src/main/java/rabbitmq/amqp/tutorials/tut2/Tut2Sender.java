package rabbitmq.amqp.tutorials.tut2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 정해진 시간 간격으로 메시지를 큐에 전송
 */
public class Tut2Sender {

    private static Logger logger = LoggerFactory.getLogger(Tut2Sender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queue;

    //AtomicInteger 멀티스레드 환경에서도 안전하게 정수 값을 증가시키거나 가져올 수 있는 클래스로 메시지 구성에 필요한 숫자 관리.
    AtomicInteger dots = new AtomicInteger(0);

    AtomicInteger count = new AtomicInteger(0);

    //일정 시간 간격(fixedDelay = 1000ms, 초당 1회)으로 자동 실행. 초기 지연 시간으로 500ms 설정
    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        StringBuilder builder = new StringBuilder("Hello");

        //message는 "Hello"로 시작해서 dots 값에 따라 점(.) 개수가 달라지고 dots 값은 1부터 시작해서 4가 되면 다시 1로 초기화.
        //ex) "Hello.", "Hello..", "Hello...", "Hello...."
        if (dots.incrementAndGet() == 4) {
            dots.set(1);
        }

        for (int i=0; i< dots.get(); i++) {
            builder.append('.');
        }

        //count는 1씩 증가하고, 메시지 끝에 붙어서 메시지 구별하는데 사용
        builder.append(count.incrementAndGet());
        String message = builder.toString();

        //최종 메시지는 rabbitTemplate.convertAndSend 메소드를 통해 지정된 큐(queue.getName())
        rabbitTemplate.convertAndSend(queue.getName(), message);

        logger.info("===== [X] Sent '{}'", message);

    }

}
