package synergyhubback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import synergyhubback.common.util.DateUtils;

import java.time.LocalDate;

@EnableJpaAuditing
@SpringBootApplication
@EnableScheduling
public class SynergyHubBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(SynergyHubBackApplication.class, args);


        /* 근태 관련 test */
        LocalDate[] weekRange = DateUtils.getCurrentWeek();
        System.out.println("Start of week: " + weekRange[0]);
        System.out.println("End of week: " + weekRange[1]);
    }

}
