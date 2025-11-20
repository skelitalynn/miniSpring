package org.springframework.beans;

/**
 * 自定义异常类
 * 为什么不直接抛出RuntimeException？
 * 如果不用BeanException，用户根本不知道这是Spring容器的错误
 * */
public class BeansException extends RuntimeException{
    public BeansException(String msg){
        super(msg);
    }
    public BeansException(String msg,Throwable cause){
        super(msg,cause);
    }
}
