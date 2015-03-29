package demo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author luis mora
 */
@Configuration
@EnableWebMvcSecurity
public class WebSecurityConf extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated();

        CompositeFilter compositeFilter = new CompositeFilter();
        ArrayList<Filter> filters = new ArrayList<Filter>();
        filters.add(new MyFilter());
        filters.add(new MySecondFilter());
        compositeFilter.setFilters(filters);
        http.addFilterAfter(compositeFilter,
                AnonymousAuthenticationFilter.class);
//        http.addFilterAfter((new MyFilter()),
//                AnonymousAuthenticationFilter.class);
    }

    private class MySecondFilter extends MyFilter{
        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            System.out.println("MySecondFilter.doFilter");
            super.doFilter(servletRequest, servletResponse, filterChain);
        }
    }
    private class MyFilter implements Filter {
        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
            System.out.println("MyFilter.init");
        }

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            System.out.println("MyFilter.doFilter");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("authentication = " + authentication);
            if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                if (servletRequest.getParameter("token") != null) {
                    System.out.println("we get token!");
                    ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("juan", new Object(), authorities);
                    SecurityContextHolder.getContext().setAuthentication(auth);

                }
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }

        @Override
        public void destroy() {
            System.out.println("MyFilter.destroy");
        }
    }
}
