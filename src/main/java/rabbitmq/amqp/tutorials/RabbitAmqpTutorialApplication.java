package rabbitmq.amqp.tutorials;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RabbitAmqpTutorialApplication {

    private static Logger logger = LoggerFactory.getLogger(RabbitAmqpTutorialApplication.class);

    @Profile("usage_message")
    @Bean
    public CommandLineRunner usage() {
        return args -> {
            System.out.println("This app uses Spring Profiles to control its behavior.\n");
            System.out.println("Sample usage: java -jar SpringTutorials-1.0-SNAPSHOT.jar --spring.profiles.active=hello-world,sender");
            System.out.println("Sample usage: java -jar target/SpringTutorials-1.0-SNAPSHOT.jar --spring.profiles.active=work-queues,receiver");
            System.out.println("Sample usage: java -jar target/SpringTutorials-1.0-SNAPSHOT.jar --spring.profiles.active=work-queues,sender");
            System.out.println("Sample usage: java -jar target/SpringTutorials-1.0-SNAPSHOT.jar --spring.profiles.active=routing,receiver --tutorial.client.duration=60000");
            System.out.println("Sample usage: java -jar target/SpringTutorials-1.0-SNAPSHOT.jar --spring.profiles.active=routing,sender --tutorial.client.duration=60000");
            System.out.println("Sample usage: java -jar target/SpringTutorials-1.0-SNAPSHOT.jar --spring.profiles.active=topics,receiver --tutorial.client.duration=60000");
            System.out.println("Sample usage: java -jar target/SpringTutorials-1.0-SNAPSHOT.jar --spring.profiles.active=topics,sender --tutorial.client.duration=60000");
        };
    }

    @Profile("!usage_message")
    @Bean
    public CommandLineRunner tutorial() {
        return new RabbitAmqpTutorialsRunner();
    }

    public static void main(String[] args) {
        SpringApplication.run(RabbitAmqpTutorialApplication.class, args);
        logger.info("=============================================================================");
    }
}