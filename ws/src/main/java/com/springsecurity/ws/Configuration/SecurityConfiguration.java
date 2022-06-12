package com.springsecurity.ws.Configuration;

import com.springsecurity.ws.JWTfilter.JwtAccessDenied;
import com.springsecurity.ws.JWTfilter.JwtAuthenticationFilter;
import com.springsecurity.ws.JWTfilter.JwtAuthenticationHttp403;
import com.sun.deploy.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.endpoint.NimbusAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private JwtAuthenticationFilter jwtAuthorizationFilter;
    private JwtAccessDenied jwtAccessDenied;
    private JwtAuthenticationHttp403 jwtAuthenticationHttp403;
    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private String googleConfigID="899007720285-cu474ct3bmr919ffqdnajj710a9s0urc.apps.googleusercontent.com";
    private String googleSecret="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";

    @Autowired
    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthorizationFilter,
                                 JwtAccessDenied jwtAccessDeniedHandler,
                                 JwtAuthenticationHttp403 jwtAuthenticationEntryPoint,
                                 @Qualifier("UserService")UserDetailsService userDetailsService,
                                 BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.jwtAccessDenied = jwtAccessDeniedHandler;
        this.jwtAuthenticationHttp403 = jwtAuthenticationEntryPoint;
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
    private static final String[] PUBLIC_MATCHERS = {
            "/user/login",
            "/user/home",
            "/user/register",
            "/user/changepassword",
            "/user/changePassword/**",
            "/user/resetpassword/**",
            "/v2/api-docs",
            "/swagger-resources/**",
            "/swagger-ui.html**",
            "/webjars/**",
            "/auth/**",
            "/oauth2/**",
            "/oauth_login",
            "/oauth2/authorization/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().cors().and()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and().authorizeRequests()
                .antMatchers(PUBLIC_MATCHERS).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(jwtAccessDenied)
                .authenticationEntryPoint(jwtAuthenticationHttp403)
                .and()
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        http.oauth2Login()
                .loginPage("/oauth_login")
                .defaultSuccessUrl("/auth/loginSuccess")
                .failureUrl("/auth/loginFailure").authorizationEndpoint()
                .baseUri("/oauth2/authorize-client")
                .authorizationRequestRepository(authorizationRequestRepository()).and().tokenEndpoint()
                .accessTokenResponseClient(accessTokenResponseClient()).and().redirectionEndpoint()
                .baseUri("/oauth2/redirect");
    }
    @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest>
    authorizationRequestRepository() {

        return new HttpSessionOAuth2AuthorizationRequestRepository();
    }
    @Bean
    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest>
    accessTokenResponseClient() {

        return new NimbusAuthorizationCodeTokenResponseClient();
    }

    @Bean
    ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration googleClient =
                CommonOAuth2Provider.GOOGLE.getBuilder("google")
                        .clientId(googleConfigID)
                        .clientSecret(googleSecret)
                        .build();
        return new InMemoryClientRegistrationRepository(googleClient);
    }


    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {
        return new InMemoryOAuth2AuthorizedClientService(
                clientRegistrationRepository());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
