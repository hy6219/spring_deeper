package hello.core.member;

/*
 *구현체가 1개만 있으면 관례상 ~Impl로 많이 네이밍 함
 */
public class MemberServiceImpl implements MemberService{

    //ctrl+shift+enter-> ;까지 자동완성됨!!
    //private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

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
}
