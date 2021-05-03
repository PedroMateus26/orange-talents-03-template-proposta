package com.zupacademy.MicroservicoPropota.seguranca;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public Environment env;

    public static String [] AUTORIZADOS = {"api/propostas/**", "api/cartoes/**","/actuator/**"};

    private static final String [] PUBLIC= {"/h2-console/**"};

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //H2
        if(Arrays.asList(env.getActiveProfiles()).contains("test"))
            http.headers().frameOptions().disable();

        http.csrf().disable().authorizeRequests(request ->
                request.antMatchers(PUBLIC).permitAll()
                        .antMatchers(AUTORIZADOS)
                        .hasAuthority("SCOPE_profile")
                        .anyRequest()
                        .authenticated()
        ).oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }

}

