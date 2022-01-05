package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BoardUserDetailsService boardUserDetailsService;
   // @Autowired
   // private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity security) throws Exception{
        security.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/member/**").authenticated()
                .antMatchers("/manager/**").hasRole("MANAGER")
                .antMatchers("/admin/**").hasRole("ADMIN");

        //RESTfull을 사용하기 위해서는 csrf 기능을 비활성화 해야한다.
        security.csrf().disable();
        security.formLogin().loginPage("/login").defaultSuccessUrl("/loginSuccess",true);
        security.exceptionHandling().accessDeniedPage("/accessDenied");
        security.logout().invalidateHttpSession(true).logoutSuccessUrl("/login");

        security.userDetailsService(boardUserDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

/*
    @Autowired
    public void authenticate(AuthenticationManagerBuilder auth) throws Exception{
        String query1 = "select id username, concat('{noop}', password) password, true enabled from member where id=?";
        String query2 = "select id, role from member where id=?";
        //데이터베이스
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(query1)
                .authoritiesByUsernameQuery(query2);

        메모리에 저장시

        auth.inMemoryAuthentication().
                withUser("manager").
                password("{noop}manager123").
                roles("MANAGER");

        auth.inMemoryAuthentication().
                withUser("admin").
                password("{noop}admin123").
                roles("ADMIN");


    }

 */
}
