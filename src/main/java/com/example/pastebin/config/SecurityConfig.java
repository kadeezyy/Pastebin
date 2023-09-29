package com.example.pastebin.config;

import com.example.pastebin.security.AuthProviderImpl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    AuthProviderImpl authProvider;

    //Configures Authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/v1/paste/get/**").permitAll()
                .anyRequest().authenticated();
    }
}
