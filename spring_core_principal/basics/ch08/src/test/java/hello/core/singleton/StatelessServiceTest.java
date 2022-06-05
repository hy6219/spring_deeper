package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatelessServiceTest {

    @Test
    @DisplayName("무상태로 설계")
    void statelessServiceTest(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatelessService statelessService1 = ac.getBean(StatelessService.class);
        StatelessService statelessService2 = ac.getBean(StatelessService.class);

        int userAPrice = statelessService1.order("userA",10000);
        int userBPrice = statelessService2.order("userB", 20000);

        Assertions.assertThat(userAPrice).isEqualTo(10000);
        Assertions.assertThat(userBPrice).isEqualTo(20000);
    }

    static class TestConfig{
        @Bean
        public StatelessService statelessService(){
            return new StatelessService();
        }
    }

}