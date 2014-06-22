package com.brn.homebrew.infrastructure;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;

import java.util.Collection;

/**
 * Created by bruno on 12/12/13.
 * <p/>
 * This class serves for getting rid of lazy initialization exception when jackson was serializing. Basically because
 * jackson inner serialization methods where called after the end of transaction when the virtual proxy tried to
 * access the database there was no longer any open session to play with. With this aspect joint point at the end of
 * return of my controller method (application layer) I force the lazy load by using a library called dozer. This way
 * I can have the lazy loading working properly at service layer level but when the controller returns the bean it
 * loads all the lazy loaded elements.
 */
@Aspect
public class LazyLoadingAspect {

    /*
        Will only intercept methods that return non void.
     */
    @After("execution (public !void com.brn.homebrew.controller.PersonalTrainerController.*(..))")
    public void logBefore(JoinPoint joinPoint) throws Throwable {
        Object returnObject = ((MethodInvocationProceedingJoinPoint) joinPoint).proceed();
        Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
        if (returnObject instanceof Collection) {
            for (Object o : (Collection) returnObject) {
                mapper.map(o, o.getClass());
            }
        } else {
            mapper.map(returnObject, returnObject.getClass());
        }
    }
}
