package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class RateDiscountPolicyTest {

    RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP는 10% 할인이 적용되어야 한다")
    void vip_o() {
        //given
        Member memberA = new Member(1L, "memberA", Grade.VIP);
        //when
        int discount = discountPolicy.discount(memberA, 10000);
        //then
        assertThat(discount).isEqualTo(1000);
    }

    //테스트는 성공하는 것도 중요한데, 실패하는 테스트를 만들어서 확인하는 것도 중요!!
    //alt + enter 로 static import 가능
    @Test
    @DisplayName("VIP가 아니면 할인이 적용되지 않아야 한다")
    void vip_x(){
        //given
        Member memberA = new Member(1L, "memberA", Grade.BASIC);
        //when
        int discount = discountPolicy.discount(memberA, 10000);
        //then
        assertThat(discount).isEqualTo(0);//0이어야 하는데 1000으로 예측해서 테스트 실패 ->성공시키려면 0으로 변경시킬 것
        /*
        expected: 1000
        but was: 0

        -> 0으로 변경 후 pass!!
         */
    }
}