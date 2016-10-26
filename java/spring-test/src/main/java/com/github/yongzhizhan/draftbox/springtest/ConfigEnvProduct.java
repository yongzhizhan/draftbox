package com.github.yongzhizhan.draftbox.springtest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Product环境
 * @author zhanyognzhi
 */

@Configuration
@Profile("product")
public class ConfigEnvProduct {
    @Bean
    public ITest test(){
        return new TestBar();
    }
}
