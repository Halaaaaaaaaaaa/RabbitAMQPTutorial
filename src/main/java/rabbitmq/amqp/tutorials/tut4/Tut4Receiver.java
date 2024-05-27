package rabbitmq.amqp.tutorials.tut4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

public class Tut4Receiver {

    private static Logger logger = LoggerFactory.getLogger(Tut4Receiver.class);

    /**
     * 두 개의 @RabbitListener 어노테이션이 각각 autoDeleteQueue1과 autoDeleteQueue2로부터
     * 메시지를 수신하도록 설정.
     */
    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receive1(String in) throws InterruptedException {
        receive(in, 1);
    }

    @RabbitListener(queues = "#{autoDeleteQueue2.name}")
    public void receive2(String in) throws InterruptedException {
        receive(in, 2);
    }

    /**
     * 메시지를 수신하고 처리 시간을 로깅.
     * doWork()를 통해 메시지 내용에 따라 작업 시간을 조정 가능.
     * 특정 색상에 관심이 있는 소비자만 해당 색상의 메시지를 수신 가능
     * @param in
     * @param receiver
     * @throws InterruptedException
     */
    public void receive(String in, int receiver) throws InterruptedException {
        StopWatch watch = new StopWatch();
        watch.start();
        logger.info("===== instance {} [X] Received '{}'", receiver, in);

        doWork(in);

        watch.stop();
        logger.info("===== instance {} [X] Done in {}s", receiver, watch.getTotalTimeSeconds());
    }

    private void doWork(String in) throws InterruptedException {
        for (char ch : in.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }
}
