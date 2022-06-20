package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.order.OrderServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertThrows(Exception.class, () -> ac.getBean("discountPolicy", DiscountPolicy.class));
    }

    @Test
    @DisplayName("생성자 주입-생성자가 1개만 존재하는 경우")
    void justOneConstructor() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        MemberRepository memberRepository = ac.getBean(MemberRepository.class);
        DiscountPolicy discountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);

        assertThat(memberRepository).isNotNull();
        assertThat(discountPolicy).isNotNull();
    }

    @Test
    @DisplayName("수정자 주입")
    void setterDiTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        assertDoesNotThrow(() -> ac.getBean(OrderServiceImpl.class));
    }

    @Test
    @DisplayName("필드 주입")
    void fieldDi() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        OrderServiceImpl bean = ac.getBean(OrderServiceImpl.class);
        MemberRepository memberRepository = bean.getMemberRepository();
        System.out.println("memberRepository= " + memberRepository);
        assertThat(memberRepository).isInstanceOf(MemberRepository.class);
        assertThat(memberRepository).isNotNull();
    }

/*    @Test
    @DisplayName("필드 주입 단점 - 외부에서 변경이 어려움")
    void fieldDiWeaknessTest() {
        OrderServiceImpl orderService = new OrderServiceImpl();
        assertThrows(NullPointerException.class, ()->orderService.createOrder(1L, "itemA", 10000));
    }*/

    @Test
    @DisplayName("일반 메서드 주입")
    void generalMethodDiTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        ac.getBean(OrderServiceImpl.class);
    }
}