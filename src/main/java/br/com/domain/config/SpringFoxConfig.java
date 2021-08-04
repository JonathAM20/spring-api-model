package br.com.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SpringFoxConfig {

    @Bean
    public UiConfiguration tryItOutConfig() {
        final String[] methodsWithTryItOutButton = {""};
        return UiConfigurationBuilder
                .builder()
                .supportedSubmitMethods(methodsWithTryItOutButton)
                .build();
    }

    @Bean
    public Docket swagger(){
      return new Docket(DocumentationType.SWAGGER_2)
              .apiInfo(apiInfo())
              .select()
              .apis(RequestHandlerSelectors.basePackage("br.com.domain.controller"))
              .paths(PathSelectors.any())
              .build()
              .useDefaultResponseMessages(false);
//              .securityContexts(Arrays.asList(securityContext()))
//              .securitySchemes(Arrays.asList(apiKey()));
    };

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Spring API Model")
                .description("Sample Spring API Model wich can be used for overall software solution.")
                .version("1.0")
                .build();
    }

//    private ApiKey apiKey() {
//        return new ApiKey("JWT", "Authorization", "header");
//    }
//
//    private SecurityContext securityContext() {
//        return SecurityContext
//                .builder()
//                .securityReferences(defaultAuth())
//                //.forPaths(PathSelectors.regex(""))
//                .build();
//    }
//
//    private List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
//    }
}