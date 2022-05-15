package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;

public class MemberApp {
    //psvm 엔터 해도 main 메서드 만들어짐
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        // 변수 없이 "new 생성자" 상태에서 ctrl + alt + v ==> 객체타입 객체명선택 옵션 창이 뜸
        memberService.join(new Member(1L,"김밥", Grade.VIP));

        Member findTarget = memberService.findMember(1L);
        //soutv + 엔터 --> 출력할 객체 선택 옵션창 뜸
        System.out.println("findTarget = " + findTarget);
    }
}
