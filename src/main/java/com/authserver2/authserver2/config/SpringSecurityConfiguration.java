package com.authserver2.authserver2.config;

import com.authserver2.authserver2.service.UserDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.web.SecurityFilterChain;

import java.util.UUID;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    @Autowired
    private UserDetailServiceImp userDetailServiceImp;

    /**
     * istersek burada kendi authentication managerımızı yazabiliyoruz
     *
     * @param http
     * @return
     * @throws Exception
     * @Autowired private CustomAuthenticationProvider authProvider;
     * @Bean public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
     * AuthenticationManagerBuilder authenticationManagerBuilder =
     * httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
     * authenticationManagerBuilder.authenticationProvider(authProvider);
     * return authenticationManagerBuilder.build();
     * }
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .cors().and().csrf().disable()
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests.antMatchers("/api/auth/login","/api/auth/register").permitAll().anyRequest().authenticated()
                );
        //.formLogin(Customizer.withDefaults());
        // @formatter:on

        return http.build();
    }


    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        // @formatter:off
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("huongdanjava")
                .clientSecret("{noop}123456")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("https://oidcdebugger.com/debug")
                .scope(OidcScopes.OPENID)
                .build();
        // @formatter:on

        return new InMemoryRegisteredClientRepository(registeredClient);
    }


    @Bean
    public UserDetailsService users() {
        /*
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("password")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user);*/
        return this.userDetailServiceImp;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}