/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.ruoyi.web.core.config;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.HttpAuthenticationScheme;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a>
 * 2020/09/18 11:04
 * @since:knife4j-spring-boot2-demo 1.0
 */
@EnableOpenApi
@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class Knife4jConfig {

	@Bean(value = "defaultApi1")
	public Docket defaultApi1() {
		//List<SecurityScheme> securitySchemes=Arrays.asList(new ApiKey("Authorization", "Authorization", "header"));
		List<SecurityScheme> securitySchemes=new ArrayList<>();

		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;

		List<SecurityContext> securityContexts=Arrays.asList(SecurityContext.builder()
				.securityReferences(Arrays.asList(new SecurityReference("Authorization", authorizationScopes)))
				.forPaths(PathSelectors.regex("/.*"))
				.build());
		HttpAuthenticationScheme httpAuthenticationScheme = HttpAuthenticationScheme.JWT_BEARER_BUILDER
				.name(HttpHeaders.AUTHORIZATION)
				.description("Bearer Token")
				.build();
		securitySchemes.add(httpAuthenticationScheme);

		//默认全局参数
		List<RequestParameter> requestParameters=new ArrayList<>();
		requestParameters.add(new RequestParameterBuilder().name("test").description("测试").in(ParameterType.QUERY).required(true).build());

		Docket docket=new Docket(DocumentationType.OAS_30)
				.apiInfo(apiInfo())
				//分组名称
				.groupName("系统设置")
				.select()
				//这里指定Controller扫描包路径
				.apis(RequestHandlerSelectors.basePackage("com.ruoyi.web.controller.system"))
				.paths(PathSelectors.any())
				.build()
				.securityContexts(securityContexts).securitySchemes(securitySchemes);
		return docket;
	}

	@Bean(value = "defaultApi2")
	public Docket defaultApi2() {
		Docket docket=new Docket(DocumentationType.OAS_30)
				.apiInfo(apiInfo())
				//分组名称
				.groupName("通用请求")
				.select()
				//这里指定Controller扫描包路径
				.apis(RequestHandlerSelectors.basePackage("com.ruoyi.web.controller.common"))
				.paths(PathSelectors.any())
				.build();
		return docket;
	}

	@Bean(value = "defaultApi3")
	public Docket defaultApi3() {
		Docket docket=new Docket(DocumentationType.OAS_30)
				.apiInfo(apiInfo())
				//分组名称
				.groupName("系统监控")
				.select()
				//这里指定Controller扫描包路径
				.apis(RequestHandlerSelectors.basePackage("com.ruoyi.web.controller.monitor"))
				.paths(PathSelectors.any())
				.build();
		return docket;
	}

	@Bean(value = "defaultApi4")
	public Docket defaultApi4() {
		Docket docket=new Docket(DocumentationType.OAS_30)
				.apiInfo(apiInfo())
				//分组名称
				.groupName("接口示例")
				.select()
				//这里指定Controller扫描包路径
				.apis(RequestHandlerSelectors.basePackage("com.ruoyi.web.controller.tool"))
				.paths(PathSelectors.any())
				.build();
		return docket;
	}

	private ApiInfo apiInfo(){
		return new ApiInfoBuilder()
				.title("云天")
				.description("swagger-bootstrap RESTful APIs")
				.version("1.0")
				.build();
	}

	private ApiKey apiKey() {
		return new ApiKey("BearerToken", "Authorization", "header");
	}
	private ApiKey apiKey1() {
		return new ApiKey("BearerToken1", "Authorization-x", "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex("/.*"))
				.build();
	}
	private SecurityContext securityContext1() {
		return SecurityContext.builder()
				.securityReferences(defaultAuth1())
				.forPaths(PathSelectors.regex("/.*"))
				.build();
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("BearerToken", authorizationScopes));
	}
	List<SecurityReference> defaultAuth1() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("BearerToken1", authorizationScopes));
	}
}
