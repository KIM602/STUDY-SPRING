package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class AppConfig {

    //중복제거 및 역할에 따라 분리(리팩터링)
    //역할과 구조가 한눈에 들어옴, 애플리케이션 전체구성 빠르게 파악 가능

    //MemberService 역할
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository()); //생성자 주입
    }

    //MemberRepository 역할
    private static MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    //OrderService 역할
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    //DiscountPolicy 역할
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

}
