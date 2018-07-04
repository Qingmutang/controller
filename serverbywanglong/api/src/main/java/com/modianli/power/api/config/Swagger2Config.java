package com.modianli.power.api.config;

import com.fasterxml.classmate.TypeResolver;
import com.modianli.power.domain.jpa.UserAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import java.time.LocalDate;
import java.util.List;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

/**
 * Created by dell on 2017/2/10.
 */
@Configuration
@EnableSwagger2
@Profile(value = {"dev", "test"})
public class Swagger2Config {

  @Autowired
  private TypeResolver typeResolver;

  @Bean
  public Docket modianApi() {
	return new Docket(DocumentationType.SWAGGER_2)
		.select()
		.apis(RequestHandlerSelectors.any())
		.paths(PathSelectors.any())
		.build()
		.pathMapping("/")
		.ignoredParameterTypes(UserAccount.class)
		.directModelSubstitute(LocalDate.class,
							   String.class)
		.genericModelSubstitutes(ResponseEntity.class)
		.alternateTypeRules(
			newRule(typeResolver.resolve(DeferredResult.class,
										 typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
					typeResolver.resolve(WildcardType.class)))
		.useDefaultResponseMessages(false)

		.securitySchemes(newArrayList(apiKey()))
		.securityContexts(newArrayList(securityContext()))
		.enableUrlTemplating(true)
        .tags(new Tag("Modian Service", "Modian Service apis "))

		;
  }

  private ApiKey apiKey() {
	return new ApiKey("x-auth-token", "api_key", "header");
  }

  private SecurityContext securityContext() {
	return SecurityContext.builder()
						  .securityReferences(defaultAuth())
						  .forPaths(PathSelectors.regex("/anyPath.*"))
						  .build();
  }

  private List<SecurityReference> defaultAuth() {
	AuthorizationScope authorizationScope
		= new AuthorizationScope("global", "accessEverything");
	AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
	authorizationScopes[0] = authorizationScope;
	return newArrayList(
		new SecurityReference("x-auth-token", authorizationScopes));
  }

//  @Bean
//  SecurityConfiguration security() {
//	return new SecurityConfiguration(
//		"test-app-client-id",
//		"test-app-client-secret",
//		"test-app-realm",
//		"test-app",
//		"apiKey",
//		ApiKeyVehicle.HEADER,
//		"api_key",
//		"," /*scope separator*/);
//  }

  @Bean
  UiConfiguration uiConfig() {
	return new UiConfiguration(
		"validatorUrl",// url
		"none",       // docExpansion          => none | list
		"alpha",      // apiSorter             => alpha
		"schema",     // defaultModelRendering => schema
		UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
		false,        // enableJsonEditor      => true | false
		true, // showRequestHeaders    => true | false
		1000L
	);
  }

}

