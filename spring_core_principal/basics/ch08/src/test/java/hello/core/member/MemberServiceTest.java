package hello.core.member;

import hello.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

    private MemberService memberService;

    @BeforeEach
    void setUp(){
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

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
