package hello.core;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

//AppConfig: 객체 생성과 연결을 담당
//=>(1)객체 생성, 연결 역할 (2) 실행하는 역할 이 두가지가 명확하게 분리됨
public class AppConfig {

    //구현체의 입장에서는, '의존관계에 대한 고민을 외부에 맡기고', '실행에만 집중'
    //구현체 입장에서는, 의존관계를 외부에서 주입해주는 것 같다고 해서 "DI; 의존관계 주입" 이라고 부름
    public MemberService memberService(){
        return new MemberServiceImpl(new MemoryMemberRepository());//생성자 주입
    }

    public OrderService orderService(){
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }


}
