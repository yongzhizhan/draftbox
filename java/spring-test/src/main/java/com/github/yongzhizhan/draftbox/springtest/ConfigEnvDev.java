package com.github.yongzhizhan.draftbox.springtest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Dev环境
 * @author zhanyognzhi
 */

@Configuration
@Profile("dev")
public class ConfigEnvDev {
    @Bean
    public ITest test(){
        return new TestFoo();
    }
}
