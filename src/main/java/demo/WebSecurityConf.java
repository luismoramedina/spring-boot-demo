package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.util.ArrayList;

/**
 * @author luis mora
 */
@Configuration
@EnableWebMvcSecurity
public class WebSecurityConf extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyFilter myFilter;
    @Autowired
    private MySecondFilter mySecondFilter;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated();

        CompositeFilter compositeFilter = new CompositeFilter();
        ArrayList<Filter> filters = new ArrayList<>();
        filters.add(myFilter);
        filters.add(mySecondFilter);
        compositeFilter.setFilters(filters);
        http.addFilterAfter(compositeFilter, AnonymousAuthenticationFilter.class);
    }

}
