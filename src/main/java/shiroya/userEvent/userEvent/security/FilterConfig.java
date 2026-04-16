package shiroya.userEvent.userEvent.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Autowired
    private JwtFilter jwtAuthFilter;

    @Bean
    public FilterRegistrationBean<JwtFilter> filter() {
        FilterRegistrationBean<JwtFilter> bean =
                new FilterRegistrationBean<>();

        bean.setFilter(jwtAuthFilter);
        bean.addUrlPatterns("/*");
        bean.setOrder(1);

        return bean;
    }
}
