package solution.demo.global.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                .defaultModelExpandDepth(3)
                .defaultModelExpandDepth(3)
                .docExpansion(DocExpansion.LIST)
                .displayRequestDuration(false)
                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
                .validatorUrl(null)
                .build();
    }

    @Bean
    public Docket api() {
        // Swagger 연결하기 위한 Bean 생성
        return new Docket(DocumentationType.SWAGGER_2)
                //Docket Bean이 하나일 경우 기본값은 default로 생략가능하지만,
                //Docket Bean이 여러개일 경우 고유 값을 명시해줘야 한다.
                .groupName("Template Basic API")
                .apiInfo(apiInfo())
                .select()
                //ApiOperation 어노테이션이 붙은 부분들을 찾아서 Swagger 설정
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //모든 경로에 대한 문서화
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Template Basic API")
                .description("Spring Template Basic API Document")
                .version("1.0.0")
                .build();
    }
}