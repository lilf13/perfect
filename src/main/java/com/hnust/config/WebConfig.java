package com.hnust.config;

import com.hnust.interceptor.WorkshopInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 包含默认的静态资源规则
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/", "classpath:/public/", "classpath:/resources/", "classpath:/META-INF/resources/");
        // 加载当前工作目录中的img目录下的静态资源
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/img/");
        //加载文章目录
        registry.addResourceHandler("/article/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/article/");
        super.addResourceHandlers(registry);
    }

    /**
     * 配置拦截器
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new WorkshopInterceptor())
                .addPathPatterns("/forum/view/workshop")
                .order(1);
    }
}
