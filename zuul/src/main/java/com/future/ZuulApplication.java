package com.future;

import com.future.filter.ErrorFilter;
import com.future.filter.ResultFilter;
import com.future.filter.ValidateFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@EnableSwagger2
@EnableZuulProxy
@SpringCloudApplication
public class ZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class,args);
	}

	@Bean
	public ErrorFilter ErrorFilter() {
		return new ErrorFilter();
	}

	@Bean
	public ResultFilter resultFilter() {
		return new ResultFilter();
	}

	@Bean
	public ValidateFilter validateFilter() {
		return new ValidateFilter();
	}


	@Component
	@Primary
	class DocumentationConfig implements SwaggerResourcesProvider {
		@Override
		public List<SwaggerResource> get() {
			List resources = new ArrayList<>();
			resources.add(swaggerResource("serviceLogin", "/serviceLogin/v2/api-docs", "2.0"));
			resources.add(swaggerResource("serviceFun", "/serviceFun/v2/api-docs", "2.0"));
			return resources;
		}

		private SwaggerResource swaggerResource(String name, String location, String version) {
			SwaggerResource swaggerResource = new SwaggerResource();
			swaggerResource.setName(name);
			swaggerResource.setLocation(location);
			swaggerResource.setSwaggerVersion(version);
			return swaggerResource;
		}
	}
	
}
