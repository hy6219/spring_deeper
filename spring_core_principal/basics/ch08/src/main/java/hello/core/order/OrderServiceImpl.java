package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{

  //  private final MemberRepository memberRepository = new MemoryMemberRepository();
    private MemberRepository memberRepository;
    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
   // private final DiscountPolicy discountPolicy = new RateDiscountPolicy();//정액할인제->정률할인제 (클래스만 갈아끼우면 됨!!)
    private DiscountPolicy discountPolicy;//인터페이스에만 의존하도록 변경
    //-> '누군가' 클라이언트인 OrderServiceImpl에 DiscountPolicy의 구현객체를 대신 생성하고 주입해주어야 함
    //어떤 구현체가 할당될 지는 어플리케이션이 할당해주어야 함(관심사 분리)

    //=>app config를 통해서 추상화되었고, 구현을 모르는 상태라서 DIP가 지켜짐
    //appconfig를 통해서 클라이언트는 확장에 열려있고(기능 추가 / 수정 ok), 변경(해당 기능을 활용하는 코드를 변경)에는 닫혀 있음 --> OCP 만족

   /* @Autowired
    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy){
        System.out.println("일반 메서드 주입");
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }*/

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy){
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member,itemPrice);//grade를 같이 넘길지 아니면 member 자체를 넘길지는 선택하기!

        //지금은 단일책임원칙이 잘 지켜진 설계
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //테스트 용도라서 인터페이스에 기재해두지는 않을 것
    public MemberRepository getMemberRepository(){
        return this.memberRepository;
    }
}
