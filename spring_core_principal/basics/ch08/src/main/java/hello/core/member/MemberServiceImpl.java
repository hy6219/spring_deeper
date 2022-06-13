package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
 *구현체가 1개만 있으면 관례상 ~Impl로 많이 네이밍 함
 */
@Component
public class MemberServiceImpl implements MemberService{

    //ctrl+shift+enter-> ;까지 자동완성됨!!
    //private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    //테스트 용도라서 인터페이스에 기재해두지는 않을 것
    public MemberRepository getMemberRepository(){
        return this.memberRepository;
    }
}
