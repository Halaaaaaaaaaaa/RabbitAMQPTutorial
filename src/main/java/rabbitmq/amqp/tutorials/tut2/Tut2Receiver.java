package rabbitmq.amqp.tutorials.tut2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

/**
 * 특정 Queue("hello")로부터 메시지를 수신하는 클래스로
 * @RabbitListener 메시지 큐의 리스너로 지정하는 어노테이션
 * queues = "hello"는 이 리스너가 "hello" 큐로부터 메시지를 수신하도록 지정
 */
@RabbitListener(queues = "hello")
public class Tut2Receiver {

    private static Logger logger = LoggerFactory.getLogger(Tut2Receiver.class);

    //Tut2Receiver의 인스턴스를 식별하는 데 사용되는 정수 값으로 생성자를 통해 초기화됩니다.
    private final int instance;

    public Tut2Receiver(int i) {
        this.instance = i;
    }

    //메시지를 처리하는 메서드에 이 어노테이션.
    //메시지 수신, 처리, 처리 시간 측정 관리하는 클래스
    @RabbitHandler
    public void receiver(String in) throws InterruptedException {
        //메서드 실행 시간을 측정하기 위해 사용됩니다. watch.start() 호출해서 측정을 시작하고,
        // watch.getTotalTimeSeconds()를 통해 총 실행 시간을 초 단위로 얻습니다.
        StopWatch watch = new StopWatch();
        watch.start();
        logger.info("instance: {} | [X] Received '{}'", this.instance, in);

        //doWork(in) 메서드 호출해서 실제 메시지 처리 작업을 수행
        //메시지에 포함된 점(.) 개수에 따라 일정 시간동안 대기. 각 점당 500ms씩 대기
        //watch.stop() 호출해서 시간 측정 중지
        doWork(in);
        watch.stop();
        logger.info("instance: {} | [X] Done in {} s", this.instance, watch.getTotalTimeSeconds());
    }

    //실제 작업을 수행하는 메서드. 일반적으로 메시지의 내용에 따라 실제 작업을 수행하는 로직을 구현합니다.
    // 예를 들어, 메시지 내의 각 점(.) 문자에 대해 500ms 동안 대기(Thread.sleep(500))하는 것으로
    // 메시지 처리에 따른 시간 지연을 모방할 수 있습니다.
    private void doWork(String in) throws InterruptedException {
        for (char ch : in.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(500);
            }
        }

    }
}
