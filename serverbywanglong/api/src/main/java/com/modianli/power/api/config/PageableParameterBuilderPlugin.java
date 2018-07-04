package com.modianli.power.api.config;

/**
 * Created by gao on 17-2-27.
 */

import com.google.common.base.Function;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

import springfox.documentation.schema.ModelReference;
import springfox.documentation.schema.TypeNameExtractor;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.contexts.ModelContext;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.schema.ResolvedTypes.modelRefFactory;
import static springfox.documentation.spi.schema.contexts.ModelContext.inputParam;

@Profile(value = {"dev", "test"})
@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER)
public class PageableParameterBuilderPlugin implements ParameterBuilderPlugin {
  private final TypeNameExtractor nameExtractor;
  private final TypeResolver resolver;

  @Autowired
  public PageableParameterBuilderPlugin(TypeNameExtractor nameExtractor, TypeResolver resolver) {
	this.nameExtractor = nameExtractor;
	this.resolver = resolver;
  }

  @Override
  public boolean supports(DocumentationType delimiter) {
	return true;
  }

  private Function<ResolvedType, ? extends ModelReference>
  createModelRefFactory(ParameterContext context) {
	ModelContext modelContext = inputParam(context.resolvedMethodParameter().getParameterType(),
										   context.getDocumentationType(),
										   context.getAlternateTypeProvider(),
										   context.getGenericNamingStrategy(),
										   context.getIgnorableParameterTypes());
	return modelRefFactory(modelContext, nameExtractor);
  }

  @Override
  public void apply(ParameterContext context) {
	ResolvedMethodParameter parameter = context.resolvedMethodParameter();
	Class<?> type = parameter.getParameterType().getErasedType();
	if (type != null && Pageable.class.isAssignableFrom(parameter.getParameterType().getErasedType())) {
	  Function<ResolvedType, ? extends ModelReference> factory =
		  createModelRefFactory(context);

	  ModelReference intModel = factory.apply(resolver.resolve(Integer.TYPE));
	  ModelReference stringModel = factory.apply(resolver.resolve(List.class, String.class));

	  List<Parameter> parameters = newArrayList(
		  context.parameterBuilder()
				 .parameterType("query").name("page").modelRef(intModel)
				 .description("Results page you want to retrieve (0..N)")
				 .build(),
		  context.parameterBuilder()
				 .parameterType("query").name("size").modelRef(intModel)
				 .description("Number of recods per page")
				 .build(),
		  context.parameterBuilder()
				 .parameterType("query").name("sort").modelRef(stringModel).allowMultiple(true)
				 .description("Sorting criteria in the format: property(,asc|desc). "
							  + "Default sort order is ascending. "
							  + "Multiple sort criteria are supported.")
				 .build());

	  context.getOperationContext().operationBuilder().parameters(parameters);
	}
  }

}
