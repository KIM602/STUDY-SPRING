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

    //@Bean memberService --> new MemoryMemberRepository()
    //@Bean orderService --> new MemoryMemberRepository()
    
    //예상(memberRepository 세번 호출) --> @Bean만 사용했을 때 결과
    //call AppConfig.memberService
    //call AppConfig.memberRepository
    //call AppConfig.memberRepository
    //call AppConfig.orderService
    //call AppConfig.memberRepository
    
    //실제 결과
    //call AppConfig.memberService
    //call AppConfig.memberRepository
    //call AppConfig.orderService

    /* AppConfig@CGLIB 예상 코드
       @Bean
       public MemberRepository memberRepository() {
           if(memoryMemberRepository가 이미 스프링 컨테이너에 등록되어 있으면?) {
               return 스프링 컨테이너에서 찾아서 반환
           } else { //스프링 컨테이너에 없으면
               기존 로직을 호출해서 MemoryMemberRepository를 생성하고 스프링 컨테이너에 등록
               return 반환
           }
       }
    */

    //@Bean만 사용해도 스프링 빈으로 등록되지만, 싱글톤을 보장하지는 않는다.
    //(memberRepository()처럼 의존관계 주입이 필요해서 메서드를 직접 호출할 때 싱글톤을 보장하지 않는다.)
    //크게 고민할 것 없이 스프링 설정 정보는 항상 @Configuration을 사용하자.

    @Bean
    //MemberService 역할
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository()); //생성자 주입
    }

    @Bean
    //MemberRepository 역할
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    //OrderService 역할
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
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
