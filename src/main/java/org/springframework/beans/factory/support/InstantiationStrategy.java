package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;

import java.beans.Beans;

public interface InstantiationStrategy {
    Object instantiate(BeanDefinition beanDefinition)throws BeansException;
}
