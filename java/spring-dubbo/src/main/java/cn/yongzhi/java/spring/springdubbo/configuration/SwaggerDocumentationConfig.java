package cn.yongzhi.java.spring.springdubbo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerDocumentationConfig {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("springdubbo API")
            .description("yueyuso 接口描述文件")
            .license("暂无")
            .licenseUrl("http://license.zhidiandata.com")
            .termsOfServiceUrl("http://www.zhidiandata.com")
            .version("1.0.0")
            .contact(new Contact("","", ""))
            .build();
    }

    @Bean
    public Docket customImplementation(){
        StringBuilder packageName = new StringBuilder(this.getClass().getPackage().getName());
        String[] packageItems = packageName.toString().split("\\.");

        packageName = new StringBuilder();
        for(int i=0; i<packageItems.length-1; i++){
            if(i == 0){
                packageName.append(packageItems[i]);
            }else{
                packageName.append(".").append(packageItems[i]);
            }
        }

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    .apis(RequestHandlerSelectors.basePackage(packageName.toString()))
                    .build()
                .directModelSubstitute(org.joda.time.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(org.joda.time.DateTime.class, java.util.Date.class)
                .apiInfo(apiInfo());
    }

}
