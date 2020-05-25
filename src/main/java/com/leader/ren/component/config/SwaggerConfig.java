package com.leader.ren.component.config;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableConfigurationProperties({
        SwaggerProperties.class
})
public class SwaggerConfig {
    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${spring.profiles.active}")
    private String profile;

    @Bean
    public Docket restApi() {
        boolean enableSwagger = true;
        if ("prod".equals(profile)) {
            enableSwagger = false;
        }
        List<Parameter> params = new ArrayList<>();
        ParameterBuilder param = new ParameterBuilder();
        param.name("token").modelRef(new ModelRef("string")).parameterType("header");
        params.add(param.build());
        ParameterBuilder platform = new ParameterBuilder();
        platform.name("platform").modelRef(new ModelRef("string")).parameterType("header");
        params.add(platform.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(params)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .enable(enableSwagger)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("REST API of Example")
                .description("REST API of Example")
                .termsOfServiceUrl("http://www.wenewrs.cn/terms")
                .license("License of wenewrs")
                .licenseUrl("http://www.wenewrs.com/LICENSE")
                .version("2.1.0")
                .build();
    }
}
