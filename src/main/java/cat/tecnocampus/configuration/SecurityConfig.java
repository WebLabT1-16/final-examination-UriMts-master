package cat.tecnocampus.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String USERS_QUERY = "select username, password, enabled from user where username = ?";
    private static final String AUTHORITIES_QUERY = "select username, role from user_roles where username = ?";
	
	
    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passEncoder() {
        return new BCryptPasswordEncoder();
    }
   
     //Configure authentication manager
     @Override
     public void configure(AuthenticationManagerBuilder auth) throws Exception {
         auth.jdbcAuthentication()
                 .dataSource(dataSource)
                 .usersByUsernameQuery(USERS_QUERY)
                 .authoritiesByUsernameQuery(AUTHORITIES_QUERY)
                 .passwordEncoder(passEncoder());
     }
    //Configure Spring security's filter chain
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    //Configure how requests are secured by interceptors
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                /*
                and mvcMathcers so that the following urls:
                    + "/stations" can only be listed by users with role USER
                    + the "/static" resources can be accessed by everybody
                    + the favorite journeys ("users/{username}/favoriteJourneys", "users/{username}/add/favoriteJourney")
                      can only be read and created by their owners (the users). You can use the
                      WebSecurity component (see the configuration package) to check that the logged in user corresponds to
                      the one in the url
                 Note that:
                    + each point above can be done with a line of code
                 POSTCONDITION: tests in webControllerTest should all pass
                */

                // order matters. First the most specific, last anyRequest()
                // ******** YOUR CODE HERE
                .antMatchers("/static").permitAll()
                .antMatchers("/stations").hasRole("USER")
                .antMatchers("/users/{username}/favoriteJourneys").hasRole("USER")
                //validar si realmente se hace así
                .antMatchers("/users/{username}/add/favoriteJourney").hasRole("{username}")


                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/byebye").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin() //a login form is shown when no authenticated request
                .and()
                .logout()
                .logoutSuccessUrl("/byebye"); //where to go when logout is successful

        //Required to allow h2-console work
        http
                .csrf().disable()
                .headers()
                .frameOptions().disable();
    }
}

