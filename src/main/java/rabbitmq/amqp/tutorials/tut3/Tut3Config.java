package rabbitmq.amqp.tutorials.tut3;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * 팬아웃 패턴을 구현하여 여러 소비자에게 메시지를 전달합니다.
 * 이 패턴은 "게시/구독"이라고도 하며 Tut3Config파일에 여러 개의 Bean을 구성하여 구현됩니다.
 * 기본적으로 게시된 메시지는 모든 수신자에게 브로드캐스트됩니다.(수신하는 모든 메시지를 알고 있는 모든 대기열에 브로드캐스트)
 * 팬아웃 튜토리얼 실행을 위해 세 개의 프로필을 만듭니다.
 * 클래스 내에서 Tut3Receiver 2개의 빈 AnonymousQueue(AMQP 용어로 비지속성, 배타적, 자동 삭제 대기열)과
 * 해당 대기열을 교환에 바인딩하는 2개의 바인딩 등 4개의 빈을 정의합니다.
 */
@Profile({"tut3", "pub-sub", "publish-subscribe"})
@Configuration
public class Tut3Config {

    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("tut.fanout");
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
        public Binding binding1(FanoutExchange fanout,
                                Queue autoDeleteQueue1) {
            return BindingBuilder.bind(autoDeleteQueue1).to(fanout);
        }

        @Bean
        public Binding binding2(FanoutExchange fanout,
                                Queue autoDeleteQueue2) {
            return BindingBuilder.bind(autoDeleteQueue2).to(fanout);
        }

        @Bean
        public Tut3Receiver receiver() {
            return new Tut3Receiver();
        }
    }

    @Profile("sender")
    @Bean
    public Tut3Sender sender() {
        return new Tut3Sender();
    }
}
