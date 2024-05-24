package rabbitmq.amqp.tutorials.tut2;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * 구성(송신자 및 수신자)을 배치하기 위한 새 패키지를 만들고
 * tut2 두 명의 소비자가 있는 작업 대기열을 만들었습니다.
 * 작업 대기열의 기본 가정은 각 작업이 정확히 한 명의 작업자에게 전달된다는 것입니다.
 */
@Profile({"tut2", "work-queues"})
@Configuration
public class Tut2Config {

    @Bean
    public Queue hello() {
        return new Queue("hello");
    }

    @Profile("receiver")
    private static class ReceiverConfig {

        @Bean
        public Tut2Receiver receiver1() {
            return new Tut2Receiver(1);
        }

        @Bean
        public Tut2Receiver receiver2() {
            return new Tut2Receiver(2);
        }
    }

    @Profile("sender")
    @Bean
    public Tut2Sender sender() {
        return new Tut2Sender();
    }

}
