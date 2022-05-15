package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{

  //  private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;
    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
   // private final DiscountPolicy discountPolicy = new RateDiscountPolicy();//정액할인제->정률할인제 (클래스만 갈아끼우면 됨!!)
    private final DiscountPolicy discountPolicy;//인터페이스에만 의존하도록 변경
    //-> '누군가' 클라이언트인 OrderServiceImpl에 DiscountPolicy의 구현객체를 대신 생성하고 주입해주어야 함
    //어떤 구현체가 할당될 지는 어플리케이션이 할당해주어야 함(관심사 분리)


    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
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
}
