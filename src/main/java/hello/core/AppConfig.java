package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    //중복제거 및 역할에 따라 분리(리팩터링)
    //역할과 구조가 한눈에 들어옴, 애플리케이션 전체구성 빠르게 파악 가능

    @Bean
    //MemberService 역할
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository()); //생성자 주입
    }

    @Bean
    //MemberRepository 역할
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    //OrderService 역할
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    //DiscountPolicy 역할
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

    //스프링의 장점
    //스프링 컨테이너가 관리를 해줌으로써 편리한 기능이 매우 많음
}
