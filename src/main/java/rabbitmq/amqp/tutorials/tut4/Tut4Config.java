package rabbitmq.amqp.tutorials.tut4;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * 메시지의 하위 집합만 구독할 수 있게, 예를 들어, 관심 있는 특정 색상("주황색", "검은색", "녹색")으로만
 * 메시지를 보낼 수 있으며 동시에 모든 메시지를 콘솔에 인쇄할 수도 있습니다.
 * 바인딩은 추가 바인딩 키 매개변수를 사용할 수 있습니다.
 * 특정 바인딩 키("orange", "black", "green")에 따라 메시지를 필터링하고 라우팅하는 방법을 보여줍니다.
 * 이 구성은 Direct Exchange를 사용하여 메시지를 적절한 큐에 라우팅합니다.
 * 두 개의 자동 삭제 큐(autoDeleteQueue1, autoDeleteQueue2)가 있으며,
 * 각 큐는 특정 색상 키로 메시지를 수신하도록 구성합니다.
 */
@Profile({"tut4", "routing"})
@Configuration
public class Tut4Config {

    @Bean
    public DirectExchange direct() {
        return new DirectExchange("tut.direct");
    }

    @Profile("receiver")
    private static class ReceiverConfig {
        @Bean
        public Queue autoDeleteQueue1() {
            return new AnonymousQueue();
        }

        @Bean
        public Queue autoDeleteQueue2() {
            return new AnonymousQueue();
        }

        @Bean
        public Binding binding1a(DirectExchange direct, Queue autoDeleteQueue1) {
            return BindingBuilder.bind(autoDeleteQueue1)
                    .to(direct)
                    .with("orange");
        }

        @Bean
        public Binding binding1b(DirectExchange direct, Queue autoDeleteQueue1) {
            return BindingBuilder.bind(autoDeleteQueue1)
                    .to(direct)
                    .with("black");
        }

        @Bean
        public Binding binding2a(DirectExchange direct, Queue autoDeleteQueue2) {
            return BindingBuilder.bind(autoDeleteQueue2)
                    .to(direct)
                    .with("green");
        }

        @Bean
        public Binding binding2b(DirectExchange direct,
                Queue autoDeleteQueue2) {
            return BindingBuilder.bind(autoDeleteQueue2)
                    .to(direct)
                    .with("black");
        }

        @Bean
        public Tut4Receiver receiver() {
            return new Tut4Receiver();
        }
    }

    @Profile("sender")
    @Bean
    public Tut4Sender sender() {
        return new Tut4Sender();
    }


}
