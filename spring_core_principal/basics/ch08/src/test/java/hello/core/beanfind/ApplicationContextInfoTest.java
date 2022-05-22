package hello.core.beanfind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBean() {
        //junit5부터는 테스트 메서드의 접근제한자가 public이 아니어도 됨
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        //iter 엔터 - 리스트/배열에 대해서 for문이 자동으로 생성됨
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = ac.getBean(beanDefinitionName);
            System.out.println("name = " + beanDefinitionName + ", object = " + bean);
        }
    }


    @Test
    @DisplayName("애플리케이션 빈 출력하기")
    void findApplicationBean() {
        //junit5부터는 테스트 메서드의 접근제한자가 public이 아니어도 됨
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        //iter 엔터 - 리스트/배열에 대해서 for문이 자동으로 생성됨
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            //스프링 내부 기반 빈이 아닌, 개발을 위해 등록해둔 빈들을 가져오기

            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION){
                Object bean = ac.getBean(beanDefinitionName);
                System.out.println("name = " + beanDefinitionName + ", object = " + bean);
            }else if(beanDefinition.getRole() == BeanDefinition.ROLE_INFRASTRUCTURE){
                //스프링 내부 기반 빈
                Object bean = ac.getBean(beanDefinitionName);
                System.out.println("[INFRA]name = " + beanDefinitionName + ", object = " + bean);
            }
        }
    }
}
