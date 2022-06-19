package hello.core.scan.dup;

import hello.core.AutoAppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanDupTest {
    @Test
    @DisplayName("빈 등록 중복 - 수동 빈 등록 vs 자동 빈 등록")
    void dupCaseManualVsAuto(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        ac.getBean("memoryMemberRepository");
    }
}
