package hello.core.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //ThreadA: A사용자가 10000원 주문
        int userAPrice = statefulService1.order("userA", 10000);
        //ThreadB: B사용자가 20000원 주문
        int userBPrice = statefulService2.order("userB",20000);

        //ThreadA: 사용자A 주문 금액 조회
//        int price = statefulService1.getPrice();
        System.out.println("price = " + userAPrice);
        //원래 결과는 사용자B의 가격이 나옴
        //stateful로 설계하면 나의 아이디에 다른 사람 이름이 보이거나 나의 결제내역인데 다른 사람의 결제내역이 보임

//        assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}