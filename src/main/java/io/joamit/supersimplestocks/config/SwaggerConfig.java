package io.joamit.supersimplestocks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String CONTROLLER_BASE_PACKAGE = "io.joamit.supersimplestocks.controller";

    /**
     * Enables swagger only for the controllers created in defined package
     *
     * @return Docket instance for swagger initialization
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(or(RequestHandlerSelectors.withClassAnnotation(RepositoryRestController.class),
                        basePackage(CONTROLLER_BASE_PACKAGE)))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    /**
     * Add custom api information to be displayed on Swagger page
     *
     * @return ApiInformation for the swagger page
     */
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Super Simple Stocks REST API",
                "CRUD operations for Super Simple stocks.",
                "0.0.1-SNAPSHOT",
                "ToBeDefined",
                new Contact("Amit Joshi", "https://github.com/joamit/super-simple-stocks", "joamit13@gs.com"),
                "MIT Open source", "API license URL", Collections.emptyList());
    }
}
