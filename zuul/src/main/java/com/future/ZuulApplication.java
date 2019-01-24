package com.future;

import com.future.filter.ErrorFilter;
import com.future.filter.ResultFilter;
import com.future.filter.ValidateFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

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
	
}
