package rabbitmq.amqp.tutorials;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;

public class RabbitAmqpTutorialsRunner implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(RabbitAmqpTutorialsRunner.class);

    @Value("${tutorial.client.duration:0}")
    private int duration;

    @Autowired
    private ConfigurableApplicationContext ctx;

    @Override
    public void run(String... arg0) throws Exception {
        logger.info("=============================================================================");
        logger.info("===== Ready *** running for {} ms", duration);
        Thread.sleep(duration);
        ctx.close();
    }
}
