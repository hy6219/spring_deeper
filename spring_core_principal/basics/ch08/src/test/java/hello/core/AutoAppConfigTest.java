package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

class AutoAppConfigTest {

    @Test
    void basicScan() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        MemberService memberService = context.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);
    }

    @Test
    @DisplayName("중복된 빈 충돌 : 자동등록빈 vs 자동등록빈")
    void autoScanConflict() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        //ConflictingBeanDefinitionException
        ac.getBean("discountPolicy", DiscountPolicy.class);
    }
}