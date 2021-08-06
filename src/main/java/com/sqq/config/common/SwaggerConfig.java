package com.sqq.config.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  private static final String AUTHORIZATION = "Authorization";

  @Value("${swagger.show}")
  private boolean swaggerShow;
  @Value("${swagger.url}")
  private String url;

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
      .title("springboot-all")
      .version("1.0.0")
      .build();
  }

  @Bean
  public Docket customImplementation() {
    return new Docket(DocumentationType.SWAGGER_2)
      .host(url)
      .enable(swaggerShow)
      .select()
      .apis(RequestHandlerSelectors.basePackage("com.sqq.controller"))
      .build()
      .securitySchemes(securitySchemes())
      .securityContexts(securityContexts())
      .directModelSubstitute(org.joda.time.LocalDate.class, java.sql.Date.class)
      .directModelSubstitute(org.joda.time.DateTime.class, java.util.Date.class)
      .apiInfo(apiInfo());
  }

  private List<ApiKey> securitySchemes() {
    List<ApiKey> list = new ArrayList<>();
    list.add(new ApiKey(AUTHORIZATION, AUTHORIZATION, "header"));
    return list;
  }

  private List<SecurityContext> securityContexts() {
    List<SecurityContext> list = new ArrayList<>();
    list.add(SecurityContext.builder()
            .securityReferences(defaultAuth())
            .build());
    return list;
  }

  private List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    List<SecurityReference> list = new ArrayList<>();
    list.add(new SecurityReference(AUTHORIZATION, authorizationScopes));
    return list;
  }
}
