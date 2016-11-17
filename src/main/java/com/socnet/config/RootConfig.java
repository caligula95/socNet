package com.socnet.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
@ComponentScan(basePackages = "com.socnet", excludeFilters = 
			   @Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class))
public class RootConfig extends WebMvcConfigurerAdapter {

	private static final String PERSISTENCE_SOURCE_BASE_NAME = "properties/persistence";

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	
    	registry
    		.addResourceHandler("/resources/**")
    		.addResourceLocations("/resources/")
    		.setCachePeriod(3600)
    		.resourceChain(true)
    		.addResolver(new PathResourceResolver());
    }

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver=new CommonsMultipartResolver();
		resolver.setDefaultEncoding("utf-8");
		return resolver;
	}
	
	@Bean
	MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames(PERSISTENCE_SOURCE_BASE_NAME); 
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}
	

	
}