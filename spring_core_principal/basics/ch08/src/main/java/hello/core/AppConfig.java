package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//AppConfig: 객체 생성과 연결을 담당
//=>(1)객체 생성, 연결 역할 (2) 실행하는 역할 이 두가지가 명확하게 분리됨

//순수자바에서 스프링 도입
@Configuration
public class AppConfig {

    //member repository도 역할과 구현분리가 필요함
    //블록설정 후 alt + shift + m => 중복되는 부분을 따로 메서드로 분리해둘 수 있도록 지원
    //이 경우 new MemberRepository()가 중복되었는데, 이를 따로 분리

    //구현체의 입장에서는, '의존관계에 대한 고민을 외부에 맡기고', '실행에만 집중'
    //구현체 입장에서는, 의존관계를 외부에서 주입해주는 것 같다고 해서 "DI; 의존관계 주입" 이라고 부름
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(getMemberRepository());//생성자 주입
    }

    @Bean
    public MemoryMemberRepository getMemberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(getMemberRepository(), getDiscountPolicy());
    }

    //참고로, @Bean을 붙이려면 접근제한자는 private이어서는 안됨
    @Bean
    public DiscountPolicy getDiscountPolicy() {
        return new RateDiscountPolicy();
    }

    /*
    리팩토링을 통해서
    =>메서드 명으로 역할이 드러남
    그리고 그 역할에 대한 구현도 한눈에 확인이 가능해짐
    =>중복제거됨
    -------------------------------------------------------------------------------
    AppConfig의 등장
    - 구현 객체 생성, 연결하는 책임을 지님
    => 클라이언트 객체의 책임이 더욱 명확해짐(SRP 원칙 적용!: 한 클래스는 하나의 책임만 가져야 한다!)
    - 애플리케이션이 사용영역과(ServiceImpl, ~Policy) 구성영역(AppConfig)으로 구분됨
    - 할인정책을 변경하고 싶다면, 사용영역의 변경에 영향을 주지 않고, 구성영역만 변경해주면 됨
    - 배우를 교체해줌
    --------------------------------------------------------------------------------
    DIP 원칙 적용 과정
    1. 정액/정률 할인정책 구현체가 DiscountPolicy(역할)에 상속되어 있는데, 이에 따른 클라이언트(OrderServiceImpl)
    코드 영향을 줄이기 위해서 DiscountPolicy에만 의존하도록 추상화
    2. AppConfig에서 구현을 정해줌(의존관계 주입)

    OrderServiceImpl에 적어둔 OCP 원칙 내용도 확인해보기!

    ==> SRP, OCP, DIP 원칙을 지킴
     */

}
