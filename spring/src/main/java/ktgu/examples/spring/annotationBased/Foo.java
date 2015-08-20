package ktgu.examples.spring.annotationBased;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

/**
 * Created by ktgu on 15/8/18.
 */

public class Foo {

    @Autowired
    public BeanFactory beanFactory;

    @Autowired
    public ApplicationContext context;

    @Autowired
    public Environment environment;

    @Autowired
    public ResourceLoader resourceLoader;

    @Autowired
    public ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public MessageSource messageSource;

    @Autowired
    public ConfigurableApplicationContext configurableApplicationContext;

    @Autowired
    public ResourcePatternResolver resourcePatternResolver;

    // Autowired可以自动装配数组（甚至还可以装配method的任意个数的参数，参数也可以是数组或强类型的map）
    @Autowired
    private BeanPostProcessor[] processors;

    @Autowired(required = false)
    public  Foo(ApplicationContext context) {
        this.context = context;
    }

    @Autowired(required = true)
    public Foo(ApplicationContext context, Environment environment) {
        this.context = context;
        this.environment = environment;
    }

    public void bar() {
        System.out.print("bar");
    }

}
