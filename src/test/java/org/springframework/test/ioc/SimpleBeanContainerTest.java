package org.springframework.test.ioc;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleBeanContainerTest {
    @Test
    public void testGetBean()throws Exception{
        BeanFactory beanFactory=new BeanFactory();
        beanFactory.registerBean("Lynn",new HelloService());
        beanFactory.getBean("Lynn");
        //取出的是Object ，需要强转为helloService
        HelloService helloService=(HelloService) beanFactory.getBean("Lynn");
        assertThat(helloService).isNotNull();
        assertThat(helloService.sayHello()).isEqualTo("hello");

    }

    class HelloService {
        public String sayHello() {
            System.out.println("hello");
            return "hello";
        }
    }
}
