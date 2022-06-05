package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class StatefulServiceTest {

    @Test
    @DisplayName("싱글톤 방식의 문제점 - 상태 유지 시 문제")
    void statefulServiceSingletonTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //ThreadA: A 사용자가 10000원 주문
        statefulService1.order("userA", 10000);
        //ThreadB : B 사용자가 20000원 주문
        statefulService2.order("userB", 20000);

        //ThreadA: 사용자A 주문 금액 조회
        int price1 = statefulService1.getPrice();
        System.out.println("price1= " + price1);//사용자A 이후 사용자B가 값을 변경하도록 변경요청해서 20000원으로 조회됨
        Assertions.assertThat(price1).isEqualTo(20000);
    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}
