//package com.macmie.mfoodyex.Config;
//
//import org.springframework.boot.web.server.ErrorPage;
//import org.springframework.boot.web.server.WebServerFactoryCustomizer;
//import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import static com.macmie.mfoodyex.Constant.AttributeConstant.FORWARD_PREFIX;
//import static com.macmie.mfoodyex.Constant.ViewConstant.INDEX_VIEW;
//import static com.macmie.mfoodyex.Constant.ViewConstant.NOT_FOUND_VIEW;
//import static org.springframework.http.HttpStatus.*;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController(NOT_FOUND_VIEW).setViewName(FORWARD_PREFIX + INDEX_VIEW);
//    }
//
//    @Bean
//    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> containerCustomizer() {
//        return container -> {
//            container.addErrorPages(new ErrorPage(NOT_FOUND, NOT_FOUND_VIEW));
//        };
//    }
//}
