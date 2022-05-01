package hello.core.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

    private final MemberService memberService = new MemberServiceImpl();

    @Test
    void joinTest(){
        //given
        Member memberA = new Member(1L, "MemberA", Grade.VIP);
        //when
        memberService.join(memberA);

        Member findMember = memberService.findMember(1L);
        //then
        Assertions.assertThat(memberA).isEqualTo(findMember);
    }
}
