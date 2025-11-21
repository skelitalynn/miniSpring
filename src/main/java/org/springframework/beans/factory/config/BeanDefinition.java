package org.springframework.beans.factory.config;

/**
 * BeanDefiniton 实例保存Bean的信息，包括clas
 * */
public class BeanDefinition {
    private Class beanClass;
    public BeanDefinition(Class beanClass){
        this.beanClass=beanClass;
    }
    public Class getBeanClass() {
        return this.beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
}
