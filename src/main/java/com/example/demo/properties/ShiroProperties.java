package com.example.demo.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @auther lf
 * @date 2017/10/12
 * @description 描述
 */
@Component
@PropertySource("classpath:shiro.properties")
@ConfigurationProperties("shiro.not")
public class ShiroProperties {

    private String chain;

    public String getChain() {
        return chain;
    }

    public void setChain(final String chain) {
        this.chain = chain;
    }

    @Override
    public String toString() {
        return "ShiroProperties{" +
                "chain='" + chain + '\'' +
                '}';
    }
}
